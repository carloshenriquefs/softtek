package com.saudemental.api.model.entity;

import com.saudemental.api.model.enums.AuditAction;
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
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "audit_logs")
public class AuditLog {
    @Id
    private String id;

    @NotBlank(message = "ID do usuário é obrigatório")
    private String userId;

    @NotNull(message = "Ação é obrigatória")
    private AuditAction action;

    @NotBlank(message = "Recurso é obrigatório")
    private String resource;

    private String resourceId;

    @NotNull(message = "Timestamp é obrigatório")
    @CreatedDate
    private LocalDateTime timestamp;

    private String ipAddress;
    private String userAgent;
    private String sessionId;

    // Dados antes da modificação (para UPDATE/DELETE)
    private Object oldValue;

    // Dados após a modificação (para CREATE/UPDATE)
    private Object newValue;

    private String description;
    private Map<String, Object> metadata;

    // Hash para integridade
    private String dataHash;

    // Conformidade NR-1 - Retenção obrigatória
    private LocalDateTime retentionUntil;

    @Builder.Default
    private Boolean processed = false;
}
