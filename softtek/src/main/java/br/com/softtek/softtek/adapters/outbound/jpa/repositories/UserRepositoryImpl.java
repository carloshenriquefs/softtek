package br.com.softtek.softtek.adapters.outbound.jpa.repositories;

import br.com.softtek.softtek.domain.user.User;
import br.com.softtek.softtek.domain.user.UserRepository;

public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User findByEmail(String email) {
        User userEntity = this.jpaUserRepository.findByEmail(email);

        return new User(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRole()
        );
    }
}
