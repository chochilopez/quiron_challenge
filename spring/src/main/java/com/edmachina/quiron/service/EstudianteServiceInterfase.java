package com.edmachina.quiron.service;

import com.edmachina.quiron.model.Carrera;
import com.edmachina.quiron.model.Estudiante;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EstudianteServiceInterfase {

//    Set<Carrera> addCareer(Long idEstudiante, Long idTitulo);

    Optional<Estudiante> findById(Long id);
    List<Estudiante> findAll();
    List<Estudiante> saveAll(List<Estudiante> estudiantes) throws Exception;
    Estudiante insert(Estudiante obj) throws Exception;
    Estudiante update(Estudiante obj) throws Exception;
    Boolean delete(Long id);
}
