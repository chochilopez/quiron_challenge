package com.edmachina.quiron.repository;

import com.edmachina.quiron.model.EstudianteMateria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteMateriaRepository extends JpaRepository<EstudianteMateria, Long> {

    List<EstudianteMateria> findAllByEstudianteId(Long id);
    List<EstudianteMateria> findAllByMateriaId(Long id);
    List<EstudianteMateria> findAllByCarreraId(Long id);
    List<EstudianteMateria> findAllByEstudianteIdAndCarreraId(Long estudianteId, Long carreraId);
    Optional<EstudianteMateria> findByEstudianteIdAndMateriaId(Long estudianteId, Long materiaId);

}
