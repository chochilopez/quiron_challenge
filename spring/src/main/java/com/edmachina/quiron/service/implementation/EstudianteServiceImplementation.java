package com.edmachina.quiron.service.implementation;

import com.edmachina.quiron.helper.Helper;
import com.edmachina.quiron.model.*;
import com.edmachina.quiron.model.enumerator.EnumEstadoMateria;
import com.edmachina.quiron.repository.CarreraRepository;
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
    private final CarreraRepository carreraRepository;

//    @Override
//    public Set<Carrera> addCareer(Long idEstudiante, Long idTitulo) {
//        Optional<Estudiante> estudiante = this.findById(idEstudiante);
//        if (estudiante.isEmpty())
//            return null;
//        Optional<Titulo> titulo = tituloRepository.findById(idTitulo);
//        if (titulo.isEmpty())
//            return null;
//        Carrera carrera = new Carrera(titulo.get(), null, Helper.getHoy(), null);
//
//        carrera = carreraRepository.save(carrera);
//        Set<Carrera> carreras = estudiante.get().getCarreras();
//        if (carreras == null) {
//            carreras = new HashSet<>();
//        }
//        carreras.add(carrera);
//        estudiante.get().setCarreras(carreras);
//        Set<Cursada> cursadas = carrera.getCursadas();
//        if (cursadas == null) {
//            cursadas = new HashSet<>();
//        }
//        for (Materia materia : titulo.get().getMaterias()) {
//            Cursada cursada = new Cursada(estudiante.get(), materia, EnumEstadoMateria.ESTADO_CURSADA_SIN_CURSAR, 0, null, null);
//            cursadas.add(cursada);
//        }
//        estudiante.get().setCursadas(cursadas);
//        repositorio.save(estudiante.get());
//        return estudiante.get().getCarreras();
//    }

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
            log.info("Se encontraron {} entidades Estudiante, incluidas las borradas.", estudiantes.size());
        }
        return estudiantes;
    }

    @Override
    public List<Estudiante> saveAll(List<Estudiante> estudiantes) throws Exception {
        try {
            log.info("Insertando listado de entidades Estudiante.");
            return repositorio.saveAll(estudiantes);
        } catch (Exception exepcion) {
            log.error("Se produjo un error al intentar persistir el listado de entidades Estudiante.");
            return null;
        }
    }

    @Override
    public Estudiante insert(Estudiante estudiante) throws Exception {
        try {
            log.info("Insertando la entidad Estudiante: {}.",  estudiante);
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
