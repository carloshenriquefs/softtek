package br.com.softtek.softtek.adapters.outbound.jpa.repositories;

import br.com.softtek.softtek.adapters.outbound.jpa.entities.JpaFormEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JpaFormRepository extends MongoRepository<JpaFormEntity, String> {
}
