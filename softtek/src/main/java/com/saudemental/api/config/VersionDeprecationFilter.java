package com.saudemental.api.config;

import com.saudemental.api.constants.ApiConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Filtro para gerenciar depreciação de versões da API
 */
@Slf4j
@Component
public class VersionDeprecationFilter extends OncePerRequestFilter {

    @Value("${api.versioning.deprecation-warning:true}")
    private boolean deprecationWarningEnabled;

    @Value("${api.v1.deprecation-date:}")
    private String v1DeprecationDate;

    @Value("${api.v1.sunset-date:}")
    private String v1SunsetDate;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String requestPath = request.getRequestURI();

        // Verificar se é uma requisição para v1
        if (requestPath.startsWith("/api/v1/")) {
            handleV1Request(response);
        }

        filterChain.doFilter(request, response);
    }

    private void handleV1Request(HttpServletResponse response) {
        // Verificar se v1 está depreciada
        if (isDeprecated(v1DeprecationDate)) {
            response.setHeader(ApiConstants.HEADER_DEPRECATED, "true");
            response.setHeader("X-Deprecation-Date", v1DeprecationDate);
            response.setHeader("X-Deprecation-Info", ApiConstants.DEPRECATION_MESSAGE);

            if (v1SunsetDate != null && !v1SunsetDate.isEmpty()) {
                response.setHeader(ApiConstants.HEADER_SUNSET, v1SunsetDate);
            }

            log.warn("Requisição para versão depreciada da API: v1");
        }

        // Verificar se v1 está em sunset
        if (isSunset(v1SunsetDate)) {
            response.setHeader("X-Sunset", "true");
            response.setHeader("X-Sunset-Info", ApiConstants.SUNSET_MESSAGE);
            log.error("Requisição para versão em sunset da API: v1");
        }
    }

    private boolean isDeprecated(String deprecationDate) {
        if (deprecationDate == null || deprecationDate.isEmpty()) {
            return false;
        }

        try {
            LocalDate deprecation = LocalDate.parse(deprecationDate, DateTimeFormatter.ISO_DATE);
            return LocalDate.now().isAfter(deprecation) || LocalDate.now().isEqual(deprecation);
        } catch (Exception e) {
            log.error("Erro ao parsear data de depreciação: {}", deprecationDate, e);
            return false;
        }
    }

    private boolean isSunset(String sunsetDate) {
        if (sunsetDate == null || sunsetDate.isEmpty()) {
            return false;
        }

        try {
            LocalDate sunset = LocalDate.parse(sunsetDate, DateTimeFormatter.ISO_DATE);
            return LocalDate.now().isAfter(sunset) || LocalDate.now().isEqual(sunset);
        } catch (Exception e) {
            log.error("Erro ao parsear data de sunset: {}", sunsetDate, e);
            return false;
        }
    }
}