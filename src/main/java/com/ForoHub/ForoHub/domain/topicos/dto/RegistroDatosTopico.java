package com.ForoHub.ForoHub.domain.topicos.dto;

import com.ForoHub.ForoHub.domain.topicos.Estado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroDatosTopico(

        @NotBlank
        String titulo,

        @NotBlank
        String mensaje,

        @NotNull
        Estado status,

        @NotNull
        Long autorId,

        @NotBlank
        String nombreCurso ) {
}