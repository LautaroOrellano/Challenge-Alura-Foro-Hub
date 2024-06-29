package com.ForoHub.ForoHub.domain.topicos.validaciones;

import com.ForoHub.ForoHub.domain.topicos.dto.RegistroDatosTopico;
import com.ForoHub.ForoHub.domain.topicos.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidacionDuplicado implements Validadores {

    @Autowired
    private TopicoRepository topicoRepository;

    public void validarDatos(RegistroDatosTopico registroDatosTopico) {
        var titulo = registroDatosTopico.titulo();
        var mensaje = registroDatosTopico.mensaje();
        var DuplicacionTopico = topicoRepository.existsByTituloAndMensaje(titulo, mensaje);

        if (DuplicacionTopico) {
            throw new ValidationException("Ya existe un topico con este titulo/mensaje");
        }
    }
}
