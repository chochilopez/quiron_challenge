package com.edmachina.quiron.service;

import com.edmachina.quiron.model.Materia;

import java.util.List;
import java.util.Optional;

public interface MateriaServiceInterfase {

    Optional<Materia> findById(Long id);
    List<Materia> findAll();
    List<Materia> saveAll(List<Materia> materias) throws Exception;
    Materia insert(Materia obj) throws Exception;
    Materia update(Materia obj) throws Exception;
    Boolean delete(Long id);
}
