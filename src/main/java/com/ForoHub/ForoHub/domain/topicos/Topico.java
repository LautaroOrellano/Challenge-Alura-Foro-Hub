package com.ForoHub.ForoHub.domain.topicos;

import com.ForoHub.ForoHub.domain.autores.Autor;
import com.ForoHub.ForoHub.domain.topicos.dto.ActualizarDatosTopico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensaje;

    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    private Estado status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    @Column(name = "nombre_curso")
    private String nombreCurso;

    public Topico(String titulo, String mensaje, LocalDateTime fecha, Estado status, Autor autor, String nombreCurso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.status = status;
        this.autor = autor;
        this.nombreCurso = nombreCurso;
    }

    public void actualizar(ActualizarDatosTopico actualizarDatosTopico) {
        if (actualizarDatosTopico.titulo() != null) {
            this.titulo = actualizarDatosTopico.titulo();
        }
        if (actualizarDatosTopico.mensaje() != null) {
            this.mensaje = actualizarDatosTopico.mensaje();
        }
        if (actualizarDatosTopico.nombreCurso() != null) {
            this.nombreCurso = actualizarDatosTopico.nombreCurso();
        }
    }
}