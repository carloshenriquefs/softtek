package com.saudemental.api.repository;

import com.saudemental.api.model.entity.Resource;
import com.saudemental.api.model.enums.AssessmentCategory;
import com.saudemental.api.model.enums.ResourceType;
import com.saudemental.api.model.enums.RiskLevel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends MongoRepository<Resource, String> {
    List<Resource> findByActiveAndTypeOrderByDisplayOrder(Boolean active, ResourceType type);
    List<Resource> findByActiveAndFeaturedOrderByDisplayOrder(Boolean active, Boolean featured);
    List<Resource> findByActiveAndTargetCategoriesContainingOrderByDisplayOrder(Boolean active, AssessmentCategory category);
    List<Resource> findByActiveAndTargetRiskLevelsContainingOrderByDisplayOrder(Boolean active, RiskLevel riskLevel);
    List<Resource> findByActiveOrderByDisplayOrder(Boolean active);
}
