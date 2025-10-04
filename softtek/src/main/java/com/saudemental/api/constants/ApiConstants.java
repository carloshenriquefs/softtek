package com.saudemental.api.constants;

/**
 * Constantes para versionamento da API
 */
public final class ApiConstants {

    private ApiConstants() {
        throw new UnsupportedOperationException("Esta é uma classe de constantes");
    }

    // ==========================================
    // VERSÕES DA API
    // ==========================================
    public static final String API_V1 = "v1";
    public static final String API_BASE_PATH = "/api";
    public static final String API_V1_PATH = API_BASE_PATH + "/" + API_V1;

    // ==========================================
    // HEADERS DE VERSÃO
    // ==========================================
    public static final String HEADER_API_VERSION = "X-API-Version";
    public static final String HEADER_SERVICE_VERSION = "X-Service-Version";
    public static final String HEADER_DEPRECATED = "X-API-Deprecated";
    public static final String HEADER_SUNSET = "X-API-Sunset";
    public static final String HEADER_REQUEST_ID = "X-Request-Id";

    // ==========================================
    // STATUS DE VERSÃO
    // ==========================================
    public static final String VERSION_STATUS_STABLE = "STABLE";
    public static final String VERSION_STATUS_BETA = "BETA";
    public static final String VERSION_STATUS_DEPRECATED = "DEPRECATED";
    public static final String VERSION_STATUS_SUNSET = "SUNSET";

    // ==========================================
    // ENDPOINTS PÚBLICOS
    // ==========================================
    public static final String[] PUBLIC_ENDPOINTS = {
            API_V1_PATH + "/auth/login",
            API_V1_PATH + "/health",
            API_V1_PATH + "/version/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/actuator/health"
    };

    // ==========================================
    // ENDPOINTS PROTEGIDOS
    // ==========================================
    public static final String ASSESSMENTS_PATH = "/assessments";
    public static final String MOODS_PATH = "/moods";
    public static final String RESOURCES_PATH = "/resources";
    public static final String AUTH_PATH = "/auth";
    public static final String USERS_PATH = "/users";
    public static final String REPORTS_PATH = "/reports";

    // ==========================================
    // MENSAGENS DE DEPRECIAÇÃO
    // ==========================================
    public static final String DEPRECATION_MESSAGE =
            "Esta versão da API está depreciada e será descontinuada em breve. " +
                    "Por favor, migre para a versão mais recente.";

    public static final String SUNSET_MESSAGE =
            "Esta versão da API foi descontinuada. " +
                    "Por favor, atualize para a versão mais recente imediatamente.";
}