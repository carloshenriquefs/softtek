package com.saudemental.api.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor para adicionar informações de versão nos headers de resposta
 */
@Slf4j
@Component
public class ApiVersionInterceptor implements HandlerInterceptor {

    @Value("${api.version:1.0.0}")
    private String apiVersion;

    private static final String API_VERSION_HEADER = "X-API-Version";
    private static final String API_DEPRECATED_HEADER = "X-API-Deprecated";

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) {

        // Adicionar versão da API no header de resposta
        response.setHeader(API_VERSION_HEADER, "v1");
        response.setHeader("X-Service-Version", apiVersion);

        // Log da requisição com versão
        String path = request.getRequestURI();
        String method = request.getMethod();
        log.debug("API v1 - {} {}", method, path);

        return true;
    }

    @Override
    public void afterCompletion(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler,
            Exception ex) {

        if (ex != null) {
            log.error("Error in API v1 request: {}", ex.getMessage());
        }
    }
}