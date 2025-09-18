package com.saudemental.api.repository;

import com.saudemental.api.model.entity.User;
import com.saudemental.api.model.enums.UserStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndStatus(String email, UserStatus status);
    boolean existsByEmail(String email);
    long countByStatus(UserStatus status);
    long countByLastLoginAtAfter(LocalDateTime dateTime);
}
