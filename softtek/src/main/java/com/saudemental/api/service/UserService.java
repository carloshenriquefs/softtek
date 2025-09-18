package com.saudemental.api.service;

import com.saudemental.api.model.dto.LoginRequest;
import com.saudemental.api.model.dto.LoginResponse;
import com.saudemental.api.model.dto.RefreshTokenRequest;
import com.saudemental.api.model.dto.UserProfileDto;
import com.saudemental.api.model.entity.User;
import com.saudemental.api.model.enums.AuditAction;
import com.saudemental.api.model.enums.UserStatus;
import com.saudemental.api.repository.UserRepository;
import com.saudemental.api.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuditService auditService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole().name())
                .accountExpired(!user.getAccountNonExpired())
                .accountLocked(!user.getAccountNonLocked())
                .credentialsExpired(!user.getCredentialsNonExpired())
                .disabled(!user.getEnabled())
                .build();
    }

    public LoginResponse authenticate(LoginRequest request, String ipAddress) {
        User user = userRepository.findByEmailAndStatus(request.getEmail(), UserStatus.ACTIVE)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        // Verificar senha diretamente usando PasswordEncoder
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        updateLastLogin(user, ipAddress);

        UserDetails userDetails = loadUserByUsername(request.getEmail());
        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        // Auditoria
        auditService.logAction(user.getId(), AuditAction.LOGIN, "auth", null, ipAddress, request.getDeviceInfo());

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(86400L) // 24 horas
                .user(convertToDto(user))
                .build();
    }

    public LoginResponse refreshToken(RefreshTokenRequest request, String ipAddress) {
        String email = jwtService.extractUsername(request.getRefreshToken());
        User user = userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        UserDetails userDetails = loadUserByUsername(email);

        if (jwtService.isTokenValid(request.getRefreshToken(), userDetails)) {
            String accessToken = jwtService.generateToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);

            return LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .tokenType("Bearer")
                    .expiresIn(86400L)
                    .user(convertToDto(user))
                    .build();
        }

        throw new RuntimeException("Token inválido");
    }

    public UserProfileDto getUserProfile(String email) {
        User user = userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return convertToDto(user);
    }

    private void updateLastLogin(User user, String ipAddress) {
        user.setLastLoginAt(LocalDateTime.now());
        user.setLastLoginIp(ipAddress);
        userRepository.save(user);
    }

    private UserProfileDto convertToDto(User user) {
        return UserProfileDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .status(user.getStatus())
                .department(user.getDepartment())
                .build();
    }
}
