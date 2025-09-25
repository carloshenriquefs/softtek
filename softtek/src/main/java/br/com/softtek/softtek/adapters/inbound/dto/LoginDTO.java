package br.com.softtek.softtek.adapters.inbound.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO(

        @NotBlank(message = "O e-mail do usuário é obrigatório!")
        @Email(message = "O e-mail do usuário não é válido!")
        String email,

        @NotBlank(message = "A senha do usuário é obrigatória!")
        @Size(min = 6, max = 12, message = "A senha deve conter entre 6 e 20 caracteres!")
        String password
) {
}
