package com.ForoHub.ForoHub.domain.topicos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Boolean existsByTituloAndMensaje(String titulo, String mensaje);

    @Query("SELECT t FROM Topico t WHERE t.nombreCurso = :curso AND FUNCTION('YEAR', t.fecha) = :año")
    Topico findByNombreCursoAndFechaAño(@Param("curso") String curso, @Param("año") Integer año);
}