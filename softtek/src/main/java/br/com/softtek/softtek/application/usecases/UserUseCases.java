package br.com.softtek.softtek.application.usecases;

import br.com.softtek.softtek.adapters.inbound.dto.UserRequestDTO;
import br.com.softtek.softtek.adapters.inbound.dto.UserResponseDTO;

public interface UserUseCases {

    UserResponseDTO saveUser(UserRequestDTO userRequestDTO);
}
