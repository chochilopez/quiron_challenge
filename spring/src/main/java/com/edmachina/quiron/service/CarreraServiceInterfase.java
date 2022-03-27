package com.edmachina.quiron.service;

import com.edmachina.quiron.model.Carrera;

import java.util.List;
import java.util.Optional;

public interface CarreraServiceInterfase {

    Optional<Carrera> findByEstudianteIdAndTituloId(Long idEstudiante, Long idTitulo);
    List<Carrera> findAll();
    List<Carrera> saveAll(List<Carrera> carreras) throws Exception;
    Carrera insert(Carrera obj) throws Exception;
    Carrera update(Carrera obj) throws Exception;
    Boolean delete(Long idEstudiante, Long idTitulo);
}
