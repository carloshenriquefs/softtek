package com.saudemental.api.controller;

import com.saudemental.api.model.dto.MoodEntryRequest;
import com.saudemental.api.model.dto.MoodEntryResponse;
import com.saudemental.api.service.MoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/moods")
@RequiredArgsConstructor
@Tag(name = "Humor - v1", description = "API de registro de humor (Versão 1)")
public class MoodController {

    private final MoodService moodService;

    @PostMapping
    @Operation(
            summary = "Registrar humor",
            description = "Registra o humor do usuário (API v1)"
    )
    public ResponseEntity<MoodEntryResponse> saveMoodEntry(
            @Valid @RequestBody MoodEntryRequest request,
            Authentication authentication,
            HttpServletRequest httpRequest) {

        String userId = getUserIdFromAuthentication(authentication);
        String ipAddress = getClientIpAddress(httpRequest);

        MoodEntryResponse response = moodService.saveMoodEntry(userId, request, ipAddress);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(
            summary = "Listar registros de humor",
            description = "Retorna histórico de humor do usuário (API v1)"
    )
    public ResponseEntity<List<MoodEntryResponse>> getMoodEntries(
            Authentication authentication,
            @Parameter(description = "Número de registros (opcional)")
            @RequestParam(required = false) Integer limit) {

        String userId = getUserIdFromAuthentication(authentication);

        List<MoodEntryResponse> entries = limit != null
                ? moodService.getRecentMoodEntries(userId, limit)
                : moodService.getUserMoodEntries(userId);

        return ResponseEntity.ok(entries);
    }

    @GetMapping("/range")
    @Operation(
            summary = "Listar humor por período",
            description = "Retorna humor do usuário em um período específico (API v1)"
    )
    public ResponseEntity<List<MoodEntryResponse>> getMoodEntriesByDateRange(
            Authentication authentication,
            @Parameter(description = "Data inicial")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "Data final")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        String userId = getUserIdFromAuthentication(authentication);
        List<MoodEntryResponse> entries = moodService.getMoodEntriesByDateRange(userId, startDate, endDate);

        return ResponseEntity.ok(entries);
    }

    @GetMapping("/count")
    @Operation(
            summary = "Contar registros",
            description = "Retorna quantidade de registros desde uma data (API v1)"
    )
    public ResponseEntity<Long> getMoodCount(
            Authentication authentication,
            @Parameter(description = "Data inicial")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime since) {

        String userId = getUserIdFromAuthentication(authentication);
        long count = moodService.getUserMoodCount(userId, since);

        return ResponseEntity.ok(count);
    }

    private String getUserIdFromAuthentication(Authentication authentication) {
        return authentication.getName();
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        return request.getRemoteAddr();
    }
}