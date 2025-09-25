package br.com.softtek.softtek.adapters.outbound.jpa.repositories;

import br.com.softtek.softtek.adapters.outbound.jpa.entities.MongoQuestionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoQuestionRepository extends MongoRepository<MongoQuestionEntity, String> {
}
