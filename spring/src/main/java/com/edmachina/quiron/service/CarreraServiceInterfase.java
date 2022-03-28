package com.edmachina.quiron.service;

import com.edmachina.quiron.model.Carrera;

import java.util.List;
import java.util.Optional;

public interface CarreraServiceInterfase {

    Optional<Carrera> findById(Long id);
    List<Carrera> findAll();
    Carrera insert(Carrera obj) throws Exception;
    Carrera update(Carrera obj) throws Exception;
    Boolean delete(Long id);
    Long cantidad();
}
