package br.com.softtek.softtek.adapters.outbound.jpa.repositories;

import br.com.softtek.softtek.adapters.outbound.jpa.entities.JpaQuestionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JpaQuestionRepository extends MongoRepository<JpaQuestionEntity, String> {
}
