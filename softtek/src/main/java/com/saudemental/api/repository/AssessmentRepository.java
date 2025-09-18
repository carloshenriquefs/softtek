package com.saudemental.api.repository;

import com.saudemental.api.model.entity.Assessment;
import com.saudemental.api.model.enums.AssessmentCategory;
import com.saudemental.api.model.enums.RiskLevel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AssessmentRepository extends MongoRepository<Assessment, String> {
    List<Assessment> findByUserIdOrderByCompletedAtDesc(String userId);
    List<Assessment> findByUserIdAndTypeOrderByCompletedAtDesc(String userId, AssessmentCategory type);

    @Query("{'userId': ?0, 'completedAt': {'$gte': ?1, '$lte': ?2}}")
    List<Assessment> findByUserIdAndDateRange(String userId, LocalDateTime startDate, LocalDateTime endDate);

    long countByRiskLevel(RiskLevel riskLevel);
    long countByCompletedAtAfter(LocalDateTime dateTime);

    @Query("{'riskLevel': 'CRITICAL', 'completedAt': {'$gte': ?0}}")
    List<Assessment> findCriticalAssessmentsAfter(LocalDateTime dateTime);
}
