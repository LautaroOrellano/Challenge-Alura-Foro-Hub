package com.ForoHub.ForoHub.domain.autores;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AutenticacionAutor(

        @NotBlank(message = "El campo ´login´ no debe estar vacio")
        @Email
        String login,

        @NotBlank(message = "El campo ´clave´ no debe estar vacio")
        String clave
) {
}
