package br.com.softtek.softtek.adapters.inbound.dto;

import br.com.softtek.softtek.adapters.outbound.jpa.entities.MongoUserEntity;
import br.com.softtek.softtek.domain.user.enums.UserRole;

public record UserResponseDTO(
        String id,
        String username,
        String email,
        UserRole role
) {

    public UserResponseDTO(MongoUserEntity mongoUserEntity) {
        this(
                mongoUserEntity.getId(),
                mongoUserEntity.getUsername(),
                mongoUserEntity.getEmail(),
                mongoUserEntity.getRole()
        );
    }
}
