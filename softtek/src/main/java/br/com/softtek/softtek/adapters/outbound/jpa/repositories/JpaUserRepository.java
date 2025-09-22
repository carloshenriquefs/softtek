package br.com.softtek.softtek.adapters.outbound.jpa.repositories;

import br.com.softtek.softtek.adapters.outbound.jpa.entities.JpaUserEntity;
import br.com.softtek.softtek.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JpaUserRepository extends MongoRepository<JpaUserEntity, String> {

    User findByEmail(String email);
}
