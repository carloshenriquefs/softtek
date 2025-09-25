package br.com.softtek.softtek.infrastructure.config;

import br.com.softtek.softtek.adapters.outbound.jpa.entities.MongoUserEntity;
import br.com.softtek.softtek.adapters.outbound.jpa.repositories.MongoUserRepository;
import br.com.softtek.softtek.domain.user.enums.UserRole;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig {

    private final MongoUserRepository mongoUserRepository;

    public TestConfig(MongoUserRepository mongoUserRepository) {
        this.mongoUserRepository = mongoUserRepository;
    }

    @PostConstruct
    public void init() {

        mongoUserRepository.deleteAll();

        MongoUserEntity felipe = new MongoUserEntity("", "", "", "", UserRole.USER);
        MongoUserEntity daniel = new MongoUserEntity("", "", "", "", UserRole.ADMIN);
        MongoUserEntity douglas = new MongoUserEntity("", "", "", "", UserRole.USER);

        mongoUserRepository.saveAll(Arrays.asList(felipe, daniel, douglas));
    }
}
