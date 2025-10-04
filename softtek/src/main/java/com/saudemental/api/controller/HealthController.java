package com.saudemental.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
@Tag(name = "Health Check - v1", description = "Endpoint de verificação de saúde da aplicação (Versão 1)")
public class HealthController {

    @Value("${spring.application.name:Saúde Mental API}")
    private String applicationName;

    @Value("${api.version:1.0.0}")
    private String apiVersion;

    @GetMapping
    @Operation(
            summary = "Verificar saúde da aplicação",
            description = "Retorna status da aplicação e informações de versão (API v1)"
    )
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("service", applicationName);
        health.put("version", apiVersion);
        health.put("apiVersion", "v1");

        return ResponseEntity.ok(health);
    }
}