package com.saudemental.api.repository;

import com.saudemental.api.model.entity.Question;
import com.saudemental.api.model.enums.AssessmentCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findByCategoryAndActiveOrderByOrder(AssessmentCategory category, Boolean active);
    List<Question> findByActiveOrderByOrder(Boolean active);
    long countByCategory(AssessmentCategory category);
}
