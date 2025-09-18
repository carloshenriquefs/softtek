package com.saudemental.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
@Tag(name = "Health Check", description = "Endpoint para verificação de saúde da aplicação")
public class HealthController {

    @GetMapping
    @Operation(summary = "Verificar saúde da aplicação", description = "Retorna status da aplicação")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("service", "Saúde Mental API");
        health.put("version", "1.0.0");

        return ResponseEntity.ok(health);
    }
}
