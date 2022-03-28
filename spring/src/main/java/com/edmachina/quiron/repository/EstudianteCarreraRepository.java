package com.edmachina.quiron.repository;

import com.edmachina.quiron.model.EstudianteCarrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteCarreraRepository extends JpaRepository<EstudianteCarrera, Long> {

    List<EstudianteCarrera> findAllByEstudianteId(Long id);
    List<EstudianteCarrera> findAllByCarreraId(Long id);
    Optional<EstudianteCarrera> findByEstudianteIdAndCarreraId(Long estudianteId, Long carreraId);
}
