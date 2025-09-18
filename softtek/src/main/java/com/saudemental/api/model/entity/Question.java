package com.saudemental.api.model.entity;

import com.saudemental.api.model.enums.AssessmentCategory;
import com.saudemental.api.model.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "questions")
public class Question {
    @Id
    private String id;

    @NotBlank(message = "Texto da pergunta é obrigatório")
    private String text;

    @NotNull(message = "Tipo da pergunta é obrigatório")
    private QuestionType type;

    private List<String> options;

    @NotNull(message = "Categoria é obrigatória")
    private AssessmentCategory category;

    @Min(value = 1, message = "Ordem deve ser maior que 0")
    private Integer order;

    @Builder.Default
    private Boolean required = true;

    @Builder.Default
    private Boolean active = true;

    private String description;
    private String helpText;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private String createdBy;
    private String updatedBy;

    // Versioning para auditoria
    @Version
    private Long version;
}
