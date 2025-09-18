package com.saudemental.api.model.entity;

import com.saudemental.api.model.enums.UserRole;
import com.saudemental.api.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter formato válido")
    private String email;

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotBlank(message = "Senha é obrigatória")
    private String password;

    private UserRole role;

    private UserStatus status;

    private String companyId;
    private String department;
    private String employeeId;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private LocalDateTime lastLoginAt;
    private String lastLoginIp;

    // Dados para conformidade LGPD
    private LocalDateTime consentGivenAt;
    private LocalDateTime dataRetentionUntil;
    private Boolean marketingConsent;

    @Builder.Default
    private Boolean accountNonExpired = true;

    @Builder.Default
    private Boolean accountNonLocked = true;

    @Builder.Default
    private Boolean credentialsNonExpired = true;

    @Builder.Default
    private Boolean enabled = true;
}
