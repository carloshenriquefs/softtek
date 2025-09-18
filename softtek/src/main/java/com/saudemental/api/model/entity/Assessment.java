package com.saudemental.api.model.entity;

import com.saudemental.api.model.enums.AssessmentCategory;
import com.saudemental.api.model.enums.RiskLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "assessments")
public class Assessment {
    @Id
    private String id;

    @NotBlank(message = "ID do usuário é obrigatório")
    private String userId;

    @NotNull(message = "Tipo de avaliação é obrigatório")
    private AssessmentCategory type;

    @NotEmpty(message = "Respostas são obrigatórias")
    private Map<String, Object> responses;

    @DecimalMin(value = "0.0", message = "Score deve ser maior ou igual a 0")
    @DecimalMax(value = "100.0", message = "Score deve ser menor ou igual a 100")
    private Double score;

    private RiskLevel riskLevel;

    private List<String> recommendations;

    @CreatedDate
    private LocalDateTime completedAt;

    private Duration completionTime;
    private String deviceInfo;
    private String ipAddress;

    // Metadados para análise
    private Map<String, Object> metadata;

    // Hash para integridade (NR-1)
    private String responseHash;

    @Builder.Default
    private Boolean isValid = true;

    // Conformidade NR-1
    private LocalDateTime dataRetentionUntil;
    private String auditTrail;
}
