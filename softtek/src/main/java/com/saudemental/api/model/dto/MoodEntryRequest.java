package com.saudemental.api.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoodEntryRequest {
    @NotBlank(message = "Emoji é obrigatório")
    private String emoji;
    @NotBlank(message = "Sentimento é obrigatório")
    @Size(min = 1, max = 100, message = "Sentimento deve ter entre 1 e 100 caracteres")
    private String feeling;

    @Size(max = 500, message = "Nota deve ter no máximo 500 caracteres")
    private String note;

    private String deviceInfo;
}
