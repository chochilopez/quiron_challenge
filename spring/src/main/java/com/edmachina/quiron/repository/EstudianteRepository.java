package com.edmachina.quiron.repository;

import com.edmachina.quiron.model.Estudiante;
import com.edmachina.quiron.model.enumerator.EnumEstadoEstudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    Long countByStatus(EnumEstadoEstudiante status);
}
