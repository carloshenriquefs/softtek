package br.com.softtek.softtek.adapters.outbound.jpa.repositories;

import br.com.softtek.softtek.adapters.outbound.jpa.entities.MongoUserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface MongoUserRepository extends MongoRepository<MongoUserEntity, String> {

    UserDetails findByEmail(String email);
}
