package com.edmachina.quiron.service;

import com.edmachina.quiron.model.Cursada;

import java.util.List;
import java.util.Optional;

public interface CursadaServiceInterfase {

    Optional<Cursada> findByEstudianteIdAndMateriaId(Long idEstudiante, Long idMateria);
    List<Cursada> findAll();
    List<Cursada> saveAll(List<Cursada> cursadas) throws Exception;
    Cursada insert(Cursada obj) throws Exception;
    Cursada update(Cursada obj) throws Exception;
    Boolean delete(Long idEstudiante, Long idMateria);
}
