package br.com.softtek.softtek.application.service;

import br.com.softtek.softtek.adapters.inbound.dto.UserRequestDTO;
import br.com.softtek.softtek.adapters.inbound.dto.UserResponseDTO;
import br.com.softtek.softtek.adapters.outbound.jpa.entities.JpaUserEntity;
import br.com.softtek.softtek.adapters.outbound.jpa.repositories.JpaUserRepository;
import br.com.softtek.softtek.application.usecases.UserUseCases;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserUseCases {

    private final JpaUserRepository jpaUserRepository;

    public UserServiceImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    public UserResponseDTO saveUser(UserRequestDTO userRequestDTO) {

        String encryptedPassword = new BCryptPasswordEncoder().encode(userRequestDTO.password());

        JpaUserEntity user = new JpaUserEntity();
        BeanUtils.copyProperties(userRequestDTO, user);
        user.setPassword(encryptedPassword);

        JpaUserEntity usuarioSalvo = jpaUserRepository.save(user);

        //return new UserResponseDTO(usuarioSalvo);
        return null;
    }
}
