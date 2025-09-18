package com.saudemental.api.model.entity;

import com.saudemental.api.model.enums.AssessmentCategory;
import com.saudemental.api.model.enums.ResourceType;
import com.saudemental.api.model.enums.RiskLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "resources")
public class Resource {
    @Id
    private String id;

    @NotBlank(message = "Título é obrigatório")
    private String title;

    @NotBlank(message = "Descrição é obrigatória")
    private String description;

    @NotNull(message = "Tipo de recurso é obrigatório")
    private ResourceType type;

    @URL(message = "Link deve ser uma URL válida")
    private String link;

    private String content; // Para recursos com conteúdo interno

    @Builder.Default
    private Boolean active = true;

    @Builder.Default
    private Boolean featured = false;

    private List<String> tags;
    private List<AssessmentCategory> targetCategories;
    private List<RiskLevel> targetRiskLevels;

    @Min(value = 1, message = "Ordem deve ser maior que 0")
    private Integer displayOrder;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private String createdBy;
    private String updatedBy;

    // Métricas de uso
    @Builder.Default
    private Long viewCount = 0L;

    @Builder.Default
    private Long clickCount = 0L;

    private LocalDateTime lastAccessedAt;
}
