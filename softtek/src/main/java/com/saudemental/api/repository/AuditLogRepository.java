package com.saudemental.api.repository;

import com.saudemental.api.model.entity.AuditLog;
import com.saudemental.api.model.enums.AuditAction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends MongoRepository<AuditLog, String> {
    List<AuditLog> findByUserIdOrderByTimestampDesc(String userId);
    List<AuditLog> findByActionOrderByTimestampDesc(AuditAction action);
    List<AuditLog> findByResourceOrderByTimestampDesc(String resource);

    @Query("{'timestamp': {'$gte': ?0, '$lte': ?1}}")
    List<AuditLog> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("{'userId': ?0, 'timestamp': {'$gte': ?1, '$lte': ?2}}")
    List<AuditLog> findByUserIdAndTimestampBetween(String userId, LocalDateTime startDate, LocalDateTime endDate);

    long countByActionAndTimestampAfter(AuditAction action, LocalDateTime dateTime);
}
