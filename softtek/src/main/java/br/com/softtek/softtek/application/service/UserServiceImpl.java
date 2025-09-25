package br.com.softtek.softtek.application.service;

import br.com.softtek.softtek.adapters.inbound.dto.UserRequestDTO;
import br.com.softtek.softtek.adapters.inbound.dto.UserResponseDTO;
import br.com.softtek.softtek.adapters.outbound.jpa.entities.MongoUserEntity;
import br.com.softtek.softtek.adapters.outbound.jpa.repositories.MongoUserRepository;
import br.com.softtek.softtek.application.usecases.UserUseCases;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserUseCases {

    private final MongoUserRepository mongoUserRepository;

    public UserServiceImpl(MongoUserRepository mongoUserRepository) {
        this.mongoUserRepository = mongoUserRepository;
    }

    public UserResponseDTO saveUser(UserRequestDTO userRequestDTO) {

        String encryptedPassword = new BCryptPasswordEncoder().encode(userRequestDTO.password());

        MongoUserEntity user = new MongoUserEntity();
        BeanUtils.copyProperties(userRequestDTO, user);
        user.setPassword(encryptedPassword);

        MongoUserEntity saveUsed = mongoUserRepository.save(user);

        return new UserResponseDTO(saveUsed);
    }
}
