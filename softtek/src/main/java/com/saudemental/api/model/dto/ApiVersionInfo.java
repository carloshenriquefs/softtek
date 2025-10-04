package com.saudemental.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para informações de versão da API
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiVersionInfo {
    private String version;
    private String status;
    private LocalDateTime releaseDate;
    private LocalDateTime deprecationDate;
    private LocalDateTime sunsetDate;
    private List<String> features;
    private List<String> deprecatedEndpoints;
    private String documentation;
    private String migrationGuide;
}