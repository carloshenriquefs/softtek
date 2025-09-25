package br.com.softtek.softtek.adapters.outbound.jpa.repositories;

import br.com.softtek.softtek.domain.user.User;
import br.com.softtek.softtek.domain.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;

public class UserRepositoryImpl implements UserRepository {

    private final MongoUserRepository mongoUserRepository;

    public UserRepositoryImpl(MongoUserRepository mongoUserRepository) {
        this.mongoUserRepository = mongoUserRepository;
    }

    @Override
    public User findByEmail(String email) {
        UserDetails userEntity = this.mongoUserRepository.findByEmail(email);

        return new User(
                userEntity.getUsername(),
                userEntity.getPassword()
        );
    }
}
