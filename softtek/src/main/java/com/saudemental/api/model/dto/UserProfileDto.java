package com.saudemental.api.model.dto;

import com.saudemental.api.model.enums.UserRole;
import com.saudemental.api.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDto {
    private String id;
    private String email;
    private String name;
    private UserRole role;
    private UserStatus status;
    private String department;
}
