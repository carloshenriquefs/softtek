package br.com.softtek.softtek.adapters.outbound.jpa.repositories;

import br.com.softtek.softtek.adapters.outbound.jpa.entities.MongoFormEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoFormRepository extends MongoRepository<MongoFormEntity, String> {
}
