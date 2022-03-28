package com.edmachina.quiron.service;

import com.edmachina.quiron.model.EstudianteCarrera;

import java.util.List;
import java.util.Optional;

public interface EstudianteCarreraServiceInterfase {

    EstudianteCarrera inscribirEstudiante(Long estudianteId, Long carreraId);

    List<EstudianteCarrera> findAllByEstudianteId(Long id);
    List<EstudianteCarrera> findAllByCarreraId(Long id);
    Optional<EstudianteCarrera> findByEstudianteIdAndCarreraId(Long estudianteId, Long carreraId);

    Optional<EstudianteCarrera> findById(Long id);
    List<EstudianteCarrera> findAll();
    EstudianteCarrera insert(EstudianteCarrera obj) throws Exception;
    EstudianteCarrera update(EstudianteCarrera obj) throws Exception;
    Boolean delete(Long id);
    Long cantidad();
}
