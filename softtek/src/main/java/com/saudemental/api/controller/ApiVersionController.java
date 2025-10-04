package com.saudemental.api.controller;

import com.saudemental.api.model.dto.ApiVersionInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/version")
@Tag(name = "API Version Info", description = "Informações sobre versões da API")
public class ApiVersionController {

    @Value("${api.version:1.0.0}")
    private String apiVersion;

    @GetMapping
    @Operation(
            summary = "Listar versões da API",
            description = "Retorna informações sobre todas as versões disponíveis da API"
    )
    public ResponseEntity<Map<String, Object>> getVersions() {
        Map<String, Object> versions = new HashMap<>();

        versions.put("current", "v1");
        versions.put("available", Arrays.asList("v1"));
        versions.put("deprecated", Arrays.asList());
        versions.put("sunset", Arrays.asList());

        return ResponseEntity.ok(versions);
    }

    @GetMapping("/v1")
    @Operation(
            summary = "Informações da v1",
            description = "Retorna detalhes sobre a versão 1 da API"
    )
    public ResponseEntity<ApiVersionInfo> getV1Info() {
        ApiVersionInfo v1Info = ApiVersionInfo.builder()
                .version("v1")
                .status("STABLE")
                .releaseDate(LocalDateTime.of(2024, 1, 1, 0, 0))
                .deprecationDate(null)
                .sunsetDate(null)
                .features(Arrays.asList(
                        "Autenticação JWT",
                        "Avaliações de saúde mental",
                        "Registro de humor",
                        "Recursos de apoio",
                        "Auditoria completa",
                        "Conformidade NR-1 e LGPD"
                ))
                .deprecatedEndpoints(Arrays.asList())
                .documentation("/swagger-ui/index.html")
                .migrationGuide(null)
                .build();

        return ResponseEntity.ok(v1Info);
    }
}