package com.edmachina.quiron.service;

import com.edmachina.quiron.model.Estudiante;

import java.util.List;
import java.util.Optional;

public interface EstudianteServiceInterfase {

    Long countByStatus(String status);
    List<Estudiante> findAllByStatus(String status);
    Optional<Estudiante> findByIdAndStatus(Long id, String status);

    Optional<Estudiante> findById(Long id);
    List<Estudiante> findAll();
    Estudiante insert(Estudiante obj) throws Exception;
    Estudiante update(Estudiante obj) throws Exception;
    Boolean delete(Long id);
    Long cantidad();
}
