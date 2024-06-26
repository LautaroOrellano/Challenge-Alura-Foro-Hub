package com.ForoHub.ForoHub.domain.topicos.dto;

import com.ForoHub.ForoHub.domain.topicos.Estado;
import com.ForoHub.ForoHub.domain.topicos.Topico;
import java.time.LocalDateTime;

public record ListadoDatosTopico(

        Long id,

        String titulo,

        String mensaje,

        Estado status,

        String autor,

        String nombreCurso,

        LocalDateTime fecha) {

    public ListadoDatosTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getStatus(),
                topico.getAutor().getLogin(),
                topico.getNombreCurso(),
                topico.getFecha()
        );
    }
}
