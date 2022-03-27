package com.edmachina.quiron.service;

import com.edmachina.quiron.model.Titulo;

import java.util.List;
import java.util.Optional;

public interface TituloServiceInterfase {

    Optional<Titulo> findById(Long id);
    List<Titulo> findAll();
    List<Titulo> saveAll(List<Titulo> titulos) throws Exception;
    Titulo insert(Titulo obj) throws Exception;
    Titulo update(Titulo obj) throws Exception;
    Boolean delete(Long id);
}
