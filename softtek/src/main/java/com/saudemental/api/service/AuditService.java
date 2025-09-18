package com.saudemental.api.service;

import com.saudemental.api.model.entity.AuditLog;
import com.saudemental.api.model.enums.AuditAction;
import com.saudemental.api.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    @Value("${audit.retention-days}")
    private Integer retentionDays;

    @Async
    public void logAction(String userId, AuditAction action, String resource, String resourceId,
                         String ipAddress, String userAgent) {
        logAction(userId, action, resource, resourceId, ipAddress, userAgent, null, null, null);
    }

    @Async
    public void logAction(String userId, AuditAction action, String resource, String resourceId,
                         String ipAddress, String userAgent, Object oldValue, Object newValue,
                         Map<String, Object> metadata) {
        try {
            AuditLog auditLog = AuditLog.builder()
                    .userId(userId)
                    .action(action)
                    .resource(resource)
                    .resourceId(resourceId)
                    .timestamp(LocalDateTime.now())
                    .ipAddress(ipAddress)
                    .userAgent(userAgent)
                    .oldValue(oldValue)
                    .newValue(newValue)
                    .metadata(metadata)
                    .retentionUntil(LocalDateTime.now().plusDays(retentionDays))
                    .dataHash(generateDataHash(userId, action, resource, resourceId))
                    .build();

            auditLogRepository.save(auditLog);

            log.info("Audit log created: userId={}, action={}, resource={}, resourceId={}",
                    userId, action, resource, resourceId);

        } catch (Exception e) {
            log.error("Error creating audit log", e);
        }
    }

    public List<AuditLog> getAuditTrail(String userId, LocalDateTime startDate, LocalDateTime endDate) {
        return auditLogRepository.findByUserIdAndTimestampBetween(userId, startDate, endDate);
    }

    public List<AuditLog> getSystemAuditTrail(LocalDateTime startDate, LocalDateTime endDate) {
        return auditLogRepository.findByTimestampBetween(startDate, endDate);
    }

    private String generateDataHash(String userId, AuditAction action, String resource, String resourceId) {
        try {
            String data = userId + action + resource + (resourceId != null ? resourceId : "") + System.currentTimeMillis();
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
            log.error("Error generating hash", e);
            return null;
        }
    }
}
