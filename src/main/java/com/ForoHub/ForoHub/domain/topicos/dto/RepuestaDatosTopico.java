package com.ForoHub.ForoHub.domain.topicos.dto;

import com.ForoHub.ForoHub.domain.topicos.Estado;
import java.time.LocalDateTime;

public record RepuestaDatosTopico(

        Long id,

        String titulo,

        String mensaje,

        Estado status,

        String autor,

        String nombreCurso,

        LocalDateTime fecha ) {
}
