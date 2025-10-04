package com.saudemental.api.model.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoodEntryResponse {
    private String id;
    private String emoji;
    private String feeling;
    private String note;
    private LocalDateTime date;
}