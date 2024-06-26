package com.ForoHub.ForoHub.domain.topicos.dto;

import com.ForoHub.ForoHub.domain.topicos.Estado;
import com.ForoHub.ForoHub.domain.topicos.Topico;
import java.time.LocalDateTime;

public record DetallesDatosTopico(

        Long id,

        String titulo,

        String mensaje,

        LocalDateTime fecha,

        Estado status,

        Long autorId,

        String nombreCurso) {

    public DetallesDatosTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFecha(),
                topico.getStatus(),
                topico.getAutor().getId(),
                topico.getNombreCurso()
        );
    }
}
