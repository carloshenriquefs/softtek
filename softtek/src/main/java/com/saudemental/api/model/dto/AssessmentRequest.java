package com.saudemental.api.model.dto;

import com.saudemental.api.model.enums.AssessmentCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentRequest {
    @NotNull(message = "Tipo de avaliação é obrigatório")
    private AssessmentCategory type;

    @NotEmpty(message = "Respostas são obrigatórias")
    private Map<String, Object> responses;

    private String deviceInfo;
    private Duration completionTime;
}
