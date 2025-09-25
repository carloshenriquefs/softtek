package br.com.softtek.softtek.adapters.inbound.controller;

import br.com.softtek.softtek.adapters.inbound.dto.LoginDTO;
import br.com.softtek.softtek.adapters.inbound.dto.TokenDTO;
import br.com.softtek.softtek.adapters.inbound.dto.UserRequestDTO;
import br.com.softtek.softtek.adapters.inbound.dto.UserResponseDTO;
import br.com.softtek.softtek.adapters.outbound.jpa.entities.MongoUserEntity;
import br.com.softtek.softtek.application.service.UserServiceImpl;
import br.com.softtek.softtek.infrastructure.config.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userServiceImpl;
    private final TokenService tokenService;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserServiceImpl userServiceImpl,
            TokenService tokenService
    ) {
        this.userServiceImpl = userServiceImpl;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.email(),
                        loginDTO.password());

        Authentication authentication = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((MongoUserEntity) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenDTO(token));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO register(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        UserResponseDTO saveUser = null;
        saveUser = userServiceImpl.saveUser(userRequestDTO);

        return saveUser;
    }

}
