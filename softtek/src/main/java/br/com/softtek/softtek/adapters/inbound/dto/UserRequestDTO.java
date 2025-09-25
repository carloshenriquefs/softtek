package br.com.softtek.softtek.adapters.inbound.dto;

import br.com.softtek.softtek.domain.user.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

        @NotBlank(message = "O nome do usuário é obrigatório!")
        String username,

        @NotBlank(message = "O e-mail do usuário é obrigatório!")
        @Email(message = "O e-mail do usuário não é válido!")
        String email,

        @NotBlank(message = "A senha do usuário é obrigatório!")
        @Size(min = 6, max = 12, message = "A senha deve conter entre 6 e 12 caracteres!")
        String password,

        UserRole role
) {
}
