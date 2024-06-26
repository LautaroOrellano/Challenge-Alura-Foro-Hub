package com.ForoHub.ForoHub.controller;

import com.ForoHub.ForoHub.domain.topicos.*;
import com.ForoHub.ForoHub.domain.topicos.dto.*;
import com.ForoHub.ForoHub.domain.topicos.validaciones.ServicioValidador;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private ServicioValidador servicioValidador;

    @Tag(name = "post", description = "Metodo POST de API de topicos")
    @Operation(
            summary = "Registrar topico",
            description = "Registra topico no existente en base de datos a su titulo y/o mensaje. " +
                    "Lo que devuelve es el topico registrado con su respectivo id, titulo, mensaje, status, " +
                    "autor, nombreCurso y fecha"
    )
    @PostMapping
    @Transactional
    public ResponseEntity<DetallesDatosTopico> registrarTopico(@RequestBody @Valid RegistroDatosTopico registroDatosTopico,
                                                               UriComponentsBuilder uriComponentsBuilder) {
        var topico = servicioValidador.registrarTopico(registroDatosTopico);
        URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetallesDatosTopico(topico));
    }

    @Tag(name = "get", description = "Metodo GET de API de topicos")
    @Operation(
            summary = "Listar topicos",
            description = "Lista todos los topicos. Lo que devuelve es una lista de topicos con su respectivo " +
                    "id, titulo, mensaje, status, autor, nombreCurso y fecha"
    )
    @GetMapping
    public ResponseEntity<Page<ListadoDatosTopico>> listarTopicos(Pageable pageable) {
        var topicos = topicoRepository.findAll(pageable)
                .map(ListadoDatosTopico::new);
        return ResponseEntity.ok(topicos);
    }

    @Tag(name = "get", description = "Metodo GET de API de topicos")
    @Operation(
            summary = "Listar topicos por fecha",
            description = "Lista topicos por su fecha de manera ascendiente. Lo que devuelve es una lista de topicos " +
                    "con su id, titulo, mensaje, status, autor, nombreCurso y fecha"
    )
    @GetMapping("/filtrar1")
    public ResponseEntity<Page<ListadoDatosTopico>> listarTopicosForFecha(@PageableDefault(size = 10, sort = "fecha",
            direction = Sort.Direction.ASC) Pageable pageable) {
        var topicos = topicoRepository.findAll(pageable)
                .map(ListadoDatosTopico::new);
        return ResponseEntity.ok(topicos);
    }

    @Tag(name = "get", description = "Metodo GET de API de topicos")
    @Operation(
            summary = "Listar topicos por nombre de curso y año",
            description = "Lista topicos por nombre de curso y año especifico con criterios de busqueda. " +
                    "Lo que devuelve es una lista de topicos con su id, titulo, mensaje, " +
                    "status, autor, nombreCurso y fecha"
    )
    @GetMapping("/filtrar2")
    public ResponseEntity listarTopicosPorNombreCursoYAño(@RequestParam String curso, @RequestParam Integer año) {
        var topico = topicoRepository.findByNombreCursoAndFechaAño(curso, año);
        return ResponseEntity.ok(new RepuestaDatosTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getStatus(),
                topico.getAutor().getLogin(),
                topico.getNombreCurso(),
                topico.getFecha()
        ));
    }

    @Tag(name = "get", description = "Metodo GET de API de topicos")
    @Operation(
            summary = "Detallar un topico",
            description = "Detalla un topico especifico por su id. Lo que devuelve es un objeto con su id, " +
                    "titulo, mensaje, status, autor, nombreCurso y fecha"
    )
    @GetMapping("/{id}")
    public ResponseEntity<RepuestaDatosTopico> detallarTopico(@PathVariable Long id) {
        var topico = servicioValidador.validarExistencia(id);
        return ResponseEntity.ok(new RepuestaDatosTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getStatus(),
                topico.getAutor().getLogin(),
                topico.getNombreCurso(),
                topico.getFecha()
        ));
    }

    @Tag(name = "put", description = "Metodo PUT de API de topicos")
    @Operation(
            summary = "Actualizar un topico",
            description = "Actualiza un topico existente. Lo que devuelve es un objeto del topico actualizado con el id, " +
                    "titulo, mensaje, status, autor, nombreCurso y fecha"
    )
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<RepuestaDatosTopico> actualizarTopico(@PathVariable Long id,
                                                                @RequestBody @Valid ActualizarDatosTopico actualizarDatosTopico) {
        var topico = servicioValidador.validarExistencia(id);
        topico.actualizar(actualizarDatosTopico);
        return ResponseEntity.ok(new RepuestaDatosTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getStatus(),
                topico.getAutor().getLogin(),
                topico.getNombreCurso(),
                topico.getFecha()
        ));
    }

    @Tag(name = "delete", description = "Metodo DELETE de API de topicos")
    @Operation(summary = "Eliminar un topico")
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        var topicoOptional = servicioValidador.validarExistencia(id);
        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}