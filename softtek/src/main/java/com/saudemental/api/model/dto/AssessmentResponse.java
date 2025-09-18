package com.saudemental.api.model.dto;

import com.saudemental.api.model.enums.AssessmentCategory;
import com.saudemental.api.model.enums.RiskLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AssessmentResponse {
    private String id;
    private AssessmentCategory type;
    private Double score;
    private RiskLevel riskLevel;
    private List<String> recommendations;
    private LocalDateTime completedAt;
    private Boolean requiresFollowUp;
}
