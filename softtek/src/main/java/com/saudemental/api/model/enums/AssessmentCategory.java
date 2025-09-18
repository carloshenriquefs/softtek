package com.saudemental.api.model.enums;

public enum AssessmentCategory {
    RISK_MAPPING("Mapeamento de Riscos"),
    WORKLOAD("Carga de Trabalho"),
    WARNING_SIGNS("Sinais de Alerta"),
    CLIMATE_RELATIONSHIP("Clima e Relacionamento"),
    COMMUNICATION("Comunicação"),
    LEADERSHIP_RELATION("Relação com Liderança"),
    STRESS_LEVEL("Nível de Estresse"),
    WORKPLACE_SATISFACTION("Satisfação no Trabalho");

    private final String description;

    AssessmentCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
