package com.saudemental.api.repository;
import com.saudemental.api.model.entity.MoodEntry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface MoodEntryRepository extends MongoRepository<MoodEntry, String> {
    List<MoodEntry> findByUserIdOrderByDateDesc(String userId);

    List<MoodEntry> findByUserIdOrderByDateDesc(String userId, Pageable pageable);

    @Query("{'userId': ?0, 'date': {'$gte': ?1, '$lte': ?2}}")
    List<MoodEntry> findByUserIdAndDateBetween(String userId, LocalDateTime startDate, LocalDateTime endDate);

    long countByUserIdAndDateAfter(String userId, LocalDateTime date);
}