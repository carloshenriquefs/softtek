package com.saudemental.api.controller;

import com.saudemental.api.model.dto.LoginRequest;
import com.saudemental.api.model.dto.LoginResponse;
import com.saudemental.api.model.dto.RefreshTokenRequest;
import com.saudemental.api.model.dto.UserProfileDto;
import com.saudemental.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints para autenticação e autorização")
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "Realizar login", description = "Autentica usuário e retorna tokens de acesso")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request,
                                              HttpServletRequest httpRequest) {
        String ipAddress = getClientIpAddress(httpRequest);
        LoginResponse response = userService.authenticate(request, ipAddress);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Renovar token", description = "Renova token de acesso usando refresh token")
    public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request,
                                                     HttpServletRequest httpRequest) {
        String ipAddress = getClientIpAddress(httpRequest);
        LoginResponse response = userService.refreshToken(request, ipAddress);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    @Operation(summary = "Obter perfil do usuário", description = "Retorna informações do perfil do usuário autenticado")
    public ResponseEntity<UserProfileDto> getProfile(Authentication authentication) {
        UserProfileDto profile = userService.getUserProfile(authentication.getName());
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/logout")
    @Operation(summary = "Realizar logout", description = "Invalida sessão do usuário")
    public ResponseEntity<Void> logout(Authentication authentication, HttpServletRequest httpRequest) {
        // Implementação de logout (invalidar token, auditoria, etc.)
        return ResponseEntity.ok().build();
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        return request.getRemoteAddr();
    }
}
