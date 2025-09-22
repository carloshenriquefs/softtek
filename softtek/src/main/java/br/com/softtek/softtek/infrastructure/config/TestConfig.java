package br.com.softtek.softtek.infrastructure.config;

import br.com.softtek.softtek.adapters.outbound.jpa.entities.JpaUserEntity;
import br.com.softtek.softtek.adapters.outbound.jpa.repositories.JpaUserRepository;
import br.com.softtek.softtek.domain.user.UserRole;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig {

    private final JpaUserRepository jpaUserRepository;

    public TestConfig(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @PostConstruct
    public void init() {

        jpaUserRepository.deleteAll();

        JpaUserEntity felipe = new JpaUserEntity("", "", "", "", UserRole.USER);
        JpaUserEntity daniel = new JpaUserEntity("", "", "", "", UserRole.ADMIN);
        JpaUserEntity douglas = new JpaUserEntity("", "", "", "", UserRole.USER);

        jpaUserRepository.saveAll(Arrays.asList(felipe, daniel, douglas));
    }
}
