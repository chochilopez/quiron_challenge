package com.edmachina.quiron.service;

import com.edmachina.quiron.model.EstudianteCarrera;
import com.edmachina.quiron.model.EstudianteMateria;

import java.util.List;
import java.util.Optional;

public interface EstudianteMateriaServiceInterfase {

    List<EstudianteMateria> inscribirMaterias(EstudianteCarrera estudianteCarrera);

    List<EstudianteMateria> findAllByEstudianteId(Long id);
    List<EstudianteMateria> findAllByMateriaId(Long id);
    List<EstudianteMateria> findAllByCarreraId(Long id);
    List<EstudianteMateria> findAllByEstudianteIdAndCarreraId(Long estudianteId, Long carreraId);
    Optional<EstudianteMateria> findByEstudianteIdAndMateriaId(Long estudianteId, Long materiaId);

    Optional<EstudianteMateria> findById(Long id);
    List<EstudianteMateria> findAll();
    EstudianteMateria insert(EstudianteMateria obj) throws Exception;
    EstudianteMateria update(EstudianteMateria obj) throws Exception;
    Boolean delete(Long id);
    Long cantidad();
}
