package com.ForoHub.ForoHub.domain.topicos.validaciones;

import com.ForoHub.ForoHub.domain.autores.AutorRepository;
import com.ForoHub.ForoHub.domain.topicos.Topico;
import com.ForoHub.ForoHub.domain.topicos.TopicoRepository;
import com.ForoHub.ForoHub.domain.topicos.dto.RegistroDatosTopico;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServicioValidador {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private List<Validadores> validadores;

    public Topico registrarTopico(@Valid RegistroDatosTopico registroDatosTopico) {
        var autorId = registroDatosTopico.autorId();

        if (autorId != null && !autorRepository.existsById(autorId)) {
            throw new ValidationException("No existe ningun autor con ese id");
        }
        validadores.forEach(v -> v.validarDatos(registroDatosTopico));
        var autor = autorRepository.getReferenceById(autorId);
        var topico = new Topico(
                registroDatosTopico.titulo(),
                registroDatosTopico.mensaje(),
                LocalDateTime.now(),
                registroDatosTopico.status(),
                autor,
                registroDatosTopico.nombreCurso()
        );
        return topicoRepository.save(topico);
    }

    public Topico validarExistencia(Long id) {
        var topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isPresent()) {
            var topico = topicoOptional.get();
            return topico;
        }
        throw new EntityNotFoundException();
    }
}
