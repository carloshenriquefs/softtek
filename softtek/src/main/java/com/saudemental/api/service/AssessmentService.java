package com.saudemental.api.service;

import com.saudemental.api.model.dto.AssessmentRequest;
import com.saudemental.api.model.dto.AssessmentResponse;
import com.saudemental.api.model.entity.Assessment;
import com.saudemental.api.model.entity.Question;
import com.saudemental.api.model.enums.AuditAction;
import com.saudemental.api.model.enums.RiskLevel;
import com.saudemental.api.repository.AssessmentRepository;
import com.saudemental.api.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final QuestionRepository questionRepository;
    private final AuditService auditService;

    public List<Question> getQuestionsForAssessment(String category) {
        if (category != null) {
            return questionRepository.findByCategoryAndActiveOrderByOrder(
                    com.saudemental.api.model.enums.AssessmentCategory.valueOf(category), true);
        }
        return questionRepository.findByActiveOrderByOrder(true);
    }

    public AssessmentResponse submitAssessment(String userId, AssessmentRequest request, String ipAddress) {
        // Validar respostas
        validateResponses(request);

        // Calcular score e nível de risco
        double score = calculateScore(request.getResponses(), request.getType());
        RiskLevel riskLevel = determineRiskLevel(score);
        List<String> recommendations = generateRecommendations(riskLevel, request.getType());

        // Criar assessment
        Assessment assessment = Assessment.builder()
                .userId(userId)
                .type(request.getType())
                .responses(request.getResponses())
                .score(score)
                .riskLevel(riskLevel)
                .recommendations(recommendations)
                .completedAt(LocalDateTime.now())
                .completionTime(request.getCompletionTime())
                .deviceInfo(request.getDeviceInfo())
                .ipAddress(ipAddress)
                .responseHash(generateResponseHash(request.getResponses()))
                .dataRetentionUntil(LocalDateTime.now().plusYears(7)) // NR-1 compliance
                .build();

        assessment = assessmentRepository.save(assessment);

        // Auditoria
        auditService.logAction(userId, AuditAction.ASSESSMENT_COMPLETE, "assessment",
                assessment.getId(), ipAddress, request.getDeviceInfo());

        // Log para casos críticos
        if (riskLevel == RiskLevel.CRITICAL) {
            log.warn("CRITICAL risk level detected for user: {}, assessment: {}", userId, assessment.getId());
        }

        return convertToResponse(assessment);
    }

    public List<AssessmentResponse> getUserAssessments(String userId) {
        return assessmentRepository.findByUserIdOrderByCompletedAtDesc(userId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public AssessmentResponse getAssessmentById(String assessmentId, String userId) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));

        if (!assessment.getUserId().equals(userId)) {
            throw new RuntimeException("Acesso negado");
        }

        return convertToResponse(assessment);
    }

    private void validateResponses(AssessmentRequest request) {
        if (request.getResponses() == null || request.getResponses().isEmpty()) {
            throw new IllegalArgumentException("Respostas são obrigatórias");
        }
    }

    private double calculateScore(Map<String, Object> responses,
            com.saudemental.api.model.enums.AssessmentCategory type) {
        // Algoritmo simplificado de cálculo de score
        double totalScore = 0.0;
        int totalQuestions = responses.size();

        for (Object response : responses.values()) {
            if (response instanceof Number) {
                totalScore += ((Number) response).doubleValue();
            } else if (response instanceof String) {
                // Converter respostas textuais em scores
                totalScore += convertTextResponseToScore((String) response);
            }
        }

        return totalQuestions > 0 ? (totalScore / totalQuestions) * 20 : 0; // Normalizar para 0-100
    }

    private double convertTextResponseToScore(String response) {
        switch (response.toLowerCase()) {
            case "sim", "sempre", "muito alto":
                return 5.0;
            case "frequentemente", "alto":
                return 4.0;
            case "às vezes", "médio":
                return 3.0;
            case "raramente", "baixo":
                return 2.0;
            case "não", "nunca", "muito baixo":
                return 1.0;
            default:
                return 3.0; // Valor padrão
        }
    }

    private RiskLevel determineRiskLevel(double score) {
        if (score >= 80) return RiskLevel.CRITICAL;
        if (score >= 60) return RiskLevel.HIGH;
        if (score >= 40) return RiskLevel.MODERATE;
        return RiskLevel.LOW;
    }

    private List<String> generateRecommendations(RiskLevel riskLevel,
            com.saudemental.api.model.enums.AssessmentCategory type) {
        switch (riskLevel) {
            case CRITICAL:
                return Arrays.asList(
                    "Procure ajuda profissional imediatamente",
                    "Entre em contato com o RH ou Segurança do Trabalho",
                    "Considere afastamento temporário se necessário"
                );
            case HIGH:
                return Arrays.asList(
                    "Recomendamos acompanhamento profissional",
                    "Converse com seu gestor sobre ajustes na rotina",
                    "Pratique técnicas de relaxamento"
                );
            case MODERATE:
                return Arrays.asList(
                    "Monitore seu bem-estar regularmente",
                    "Considere atividades de autocuidado",
                    "Mantenha diálogo aberto com a equipe"
                );
            default:
                return Arrays.asList(
                    "Continue mantendo bons hábitos de saúde mental",
                    "Participe das atividades de bem-estar da empresa"
                );
        }
    }

    private String generateResponseHash(Map<String, Object> responses) {
        try {
            String data = responses.toString() + System.currentTimeMillis();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(data.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("Error generating response hash", e);
            return null;
        }
    }

    private AssessmentResponse convertToResponse(Assessment assessment) {
        return AssessmentResponse.builder()
                .id(assessment.getId())
                .type(assessment.getType())
                .score(assessment.getScore())
                .riskLevel(assessment.getRiskLevel())
                .recommendations(assessment.getRecommendations())
                .completedAt(assessment.getCompletedAt())
                .requiresFollowUp(assessment.getRiskLevel() == RiskLevel.CRITICAL ||
                                assessment.getRiskLevel() == RiskLevel.HIGH)
                .build();
    }
}
