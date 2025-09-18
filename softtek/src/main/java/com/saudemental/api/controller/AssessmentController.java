package com.saudemental.api.controller;

import com.saudemental.api.model.dto.AssessmentRequest;
import com.saudemental.api.model.dto.AssessmentResponse;
import com.saudemental.api.model.entity.Question;
import com.saudemental.api.service.AssessmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assessments")
@RequiredArgsConstructor
@Tag(name = "Avaliações", description = "Endpoints para avaliações de saúde mental")
public class AssessmentController {

    private final AssessmentService assessmentService;

    @GetMapping("/questions")
    @Operation(summary = "Obter perguntas", description = "Retorna perguntas para avaliação por categoria")
    public ResponseEntity<List<Question>> getQuestions(
            @Parameter(description = "Categoria da avaliação (opcional)")
            @RequestParam(required = false) String category) {
        List<Question> questions = assessmentService.getQuestionsForAssessment(category);
        return ResponseEntity.ok(questions);
    }

    @PostMapping
    @Operation(summary = "Submeter avaliação", description = "Submete uma nova avaliação de saúde mental")
    public ResponseEntity<AssessmentResponse> submitAssessment(
            @Valid @RequestBody AssessmentRequest request,
            Authentication authentication,
            HttpServletRequest httpRequest) {

        String userId = getUserIdFromAuthentication(authentication);
        String ipAddress = getClientIpAddress(httpRequest);

        AssessmentResponse response = assessmentService.submitAssessment(userId, request, ipAddress);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar avaliações do usuário", description = "Retorna histórico de avaliações do usuário autenticado")
    public ResponseEntity<List<AssessmentResponse>> getUserAssessments(Authentication authentication) {
        String userId = getUserIdFromAuthentication(authentication);
        List<AssessmentResponse> assessments = assessmentService.getUserAssessments(userId);
        return ResponseEntity.ok(assessments);
    }

    @GetMapping("/{assessmentId}")
    @Operation(summary = "Obter avaliação específica", description = "Retorna detalhes de uma avaliação específica")
    public ResponseEntity<AssessmentResponse> getAssessment(
            @PathVariable String assessmentId,
            Authentication authentication) {

        String userId = getUserIdFromAuthentication(authentication);
        AssessmentResponse assessment = assessmentService.getAssessmentById(assessmentId, userId);
        return ResponseEntity.ok(assessment);
    }

    private String getUserIdFromAuthentication(Authentication authentication) {
        // Em implementação real, extrair ID do usuário do token JWT
        return authentication.getName(); // Simplificado para este exemplo
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
