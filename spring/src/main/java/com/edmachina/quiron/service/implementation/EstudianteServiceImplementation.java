package com.edmachina.quiron.service.implementation;

import com.edmachina.quiron.helper.Helper;
import com.edmachina.quiron.model.*;
import com.edmachina.quiron.model.enumerator.EnumEstadoEstudiante;
import com.edmachina.quiron.repository.CarreraRepository;
import com.edmachina.quiron.repository.EstudianteCarreraRepository;
import com.edmachina.quiron.repository.EstudianteRepository;
import com.edmachina.quiron.service.EstudianteServiceInterfase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstudianteServiceImplementation implements EstudianteServiceInterfase {

    private final EstudianteRepository repositorio;

    @Override
    public Optional<Estudiante> findByIdAndStatus(Long id, String status) {
        log.info("Buscando la entidad Estudiante con id: {} y estado {}.", id, status);
        Optional<Estudiante> estudiante = repositorio.findByIdAndStatus(id, EnumEstadoEstudiante.valueOf(status));
        if (estudiante.isEmpty()) {
            log.info("No existe la entidad Estudiante con id: {} y estado {}.", id, status);
        } else {
            log.info("Se encontró la entidad Estudiante: {}.", estudiante);
        }
        return estudiante;
    }

    @Override
    public List<Estudiante> findAllByStatus(String status) {
        log.info("Buscando todas las entidades con status {}.", status);
        List<Estudiante> estudiantes = repositorio.findAllByStatus(EnumEstadoEstudiante.valueOf(status));
        if (estudiantes.isEmpty()) {
            log.info("La tabla no posee entidades Estudiante con status {}.", status);
        } else {
            log.info("Se encontraron {} entidades Estudiante con status {}.", estudiantes.size(), status);
        }
        return estudiantes;
    }

    @Override
    public Long cantidad() {
        Long cuenta = repositorio.count();
        log.info("Existen {} entidades en Estudiante.", cuenta);
        return cuenta;
    }

    @Override
    public Long countByStatus(String status) {
        Long cuenta = repositorio.countByStatus(EnumEstadoEstudiante.valueOf(status));
        log.info("Existen {} entidades con status {}.", cuenta, status);
        return cuenta;
    }

    @Override
    public Optional<Estudiante> findById(Long id) {
        log.info("Buscando la entidad Estudiante con id: {}.", id);
        Optional<Estudiante> estudiante = repositorio.findById(id);
        if (estudiante.isEmpty()) {
            log.info("No existe la entidad Estudiante con id: {}.", id);
        } else {
            log.info("Se encontró la entidad Estudiante: {}.", estudiante);
        }
        return estudiante;
    }

    @Override
    public List<Estudiante> findAll() {
        log.info("Buscando todas las entidades Estudiante.");
        List<Estudiante> estudiantes = repositorio.findAll();
        if (estudiantes.isEmpty()) {
            log.info("La tabla no posee entidades Estudiante.");
        } else {
            log.info("Se encontraron {} entidades Estudiante.", estudiantes.size());
        }
        return estudiantes;
    }

    @Override
    public Estudiante insert(Estudiante estudiante) throws Exception {
        try {
            log.info("Insertando la entidad Estudiante: {}.",  estudiante);
            estudiante.setIngresoLead(Helper.getHoy());
            repositorio.save(estudiante);
            log.info("Se insertó correctamente la entidad Estudiante: {}.", estudiante);
            return estudiante;
        } catch (Exception exepcion) {
            log.error("Se produjo un error al intentar persistir la entidad Estudiante: {}. Se produjo la excepción {}.", estudiante, exepcion);
            return null;
        }
    }

    @Override
    public Estudiante update(Estudiante estudiante) {
        try {
            log.info("Actualizando la entidad Estudiante: {}.", estudiante);
            Optional<Estudiante> entidad = repositorio.findById(estudiante.getId());
            if (entidad.isEmpty()) {
                log.error("No existe la entidad Estudiante que desea actualizar.");
                return null;
            }
            repositorio.save(estudiante);
            log.info("Se actualizo correctamente la entidad Estudiante: {}.", estudiante);
            return estudiante;
        } catch (Exception exepcion) {
            log.error("Se produjo un error al intentar modificar la entidad Estudiante: {}. Se produjo la excepción {}.", estudiante, exepcion);
            return null;
        }
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Borrando la entidad Estudiante con id: {}.", id);
        Optional<Estudiante> estudiante = repositorio.findById(id);
        if (estudiante.isEmpty()) {
            log.error("No se encontro ninguna entidad Estudiante con id: {}.", id);
            return false;
        }
        log.info("Borrando la entidad Estudiante: {}.", estudiante.get());
        repositorio.delete(estudiante.get());
        return true;
    }
}
