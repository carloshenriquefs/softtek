package com.saudemental.api.repository;

import com.saudemental.api.model.entity.Assessment;
import com.saudemental.api.model.enums.AssessmentCategory;
import com.saudemental.api.model.enums.RiskLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório de avaliações com suporte a paginação e filtros avançados
 */
@Repository
public interface AssessmentRepository extends MongoRepository<Assessment, String> {

    // ===== QUERIES COM PAGINAÇÃO =====
    Page<Assessment> findByUserId(String userId, Pageable pageable);

    Page<Assessment> findByUserIdAndType(String userId, AssessmentCategory type, Pageable pageable);

    Page<Assessment> findByUserIdAndRiskLevel(String userId, RiskLevel riskLevel, Pageable pageable);

    // ===== QUERIES SIMPLES =====
    List<Assessment> findByUserIdOrderByCompletedAtDesc(String userId);

    List<Assessment> findByUserIdAndTypeOrderByCompletedAtDesc(String userId, AssessmentCategory type);

    // ===== QUERIES POR PERÍODO =====
    @Query("{'userId': ?0, 'completedAt': {'$gte': ?1, '$lte': ?2}}")
    List<Assessment> findByUserIdAndDateRange(String userId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("{'userId': ?0, 'type': ?1, 'completedAt': {'$gte': ?2, '$lte': ?3}}")
    Page<Assessment> findByUserIdAndTypeAndDateRange(
            String userId,
            AssessmentCategory type,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );

    // ===== ESTATÍSTICAS E AGREGAÇÕES =====
    long countByRiskLevel(RiskLevel riskLevel);

    long countByCompletedAtAfter(LocalDateTime dateTime);

    long countByUserIdAndCompletedAtAfter(String userId, LocalDateTime dateTime);

    @Query(value = "{'riskLevel': {'$in': ['CRITICAL', 'HIGH']}, 'completedAt': {'$gte': ?0}}")
    List<Assessment> findHighRiskAssessmentsAfter(LocalDateTime dateTime);

    @Query("{'riskLevel': 'CRITICAL', 'completedAt': {'$gte': ?0}}")
    List<Assessment> findCriticalAssessmentsAfter(LocalDateTime dateTime);

    // ===== QUERIES PARA ANÁLISE E RELATÓRIOS =====
    @Query(value = "{'type': ?0, 'completedAt': {'$gte': ?1, '$lte': ?2}}",
            count = true)
    long countByTypeAndDateRange(AssessmentCategory type, LocalDateTime start, LocalDateTime end);

    @Query("{'userId': ?0, 'type': ?1}")
    List<Assessment> findLatestByUserIdAndType(String userId, AssessmentCategory type);
}