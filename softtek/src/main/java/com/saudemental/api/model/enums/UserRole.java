package com.saudemental.api.model.enums;

public enum UserRole {
    EMPLOYEE("Funcionário"),
    HR("RH"),
    OSH("Segurança do Trabalho"),
    ADMIN("Administrador"),
    AUDITOR("Auditor");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
