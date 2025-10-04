
package com.saudemental.api.model.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "mood_entries")
public class MoodEntry {
    @Id
    private String id;
    @NotBlank(message = "ID do usuário é obrigatório")
    private String userId;

    @NotBlank(message = "Emoji é obrigatório")
    private String emoji;

    @NotBlank(message = "Sentimento é obrigatório")
    private String feeling;

    private String note;

    @CreatedDate
    @NotNull
    private LocalDateTime date;

    private String deviceInfo;
    private String ipAddress;

    private LocalDateTime dataRetentionUntil;
}
