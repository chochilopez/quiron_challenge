package com.edmachina.quiron.service.implementation;

import com.edmachina.quiron.model.EstudianteCarrera;
import com.edmachina.quiron.model.EstudianteMateria;
import com.edmachina.quiron.model.Materia;
import com.edmachina.quiron.model.enumerator.EnumEstadoMateria;
import com.edmachina.quiron.repository.EstudianteMateriaRepository;
import com.edmachina.quiron.service.EstudianteMateriaServiceInterfase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstudianteMateriaServiceImplementation implements EstudianteMateriaServiceInterfase {

    private final EstudianteMateriaRepository repositorio;

    @Override
    public Long cantidad() {
        Long cuenta = repositorio.count();
        log.info("Existen {} entidades en EstudianteMateria.", cuenta);
        return cuenta;
    }

    @Override
    public List<EstudianteMateria> inscribirMaterias(EstudianteCarrera estudianteCarrera) {
        List<EstudianteMateria> estudianteMaterias = new ArrayList<>();
        for ( Materia materia : estudianteCarrera.getCarrera().getPlanEstudio()) {
            EstudianteMateria estudianteMateria=new EstudianteMateria(
                    estudianteCarrera.getEstudiante(),
                    materia,
                    estudianteCarrera.getCarrera(),
                    EnumEstadoMateria.ESTADO_MATERIA_SIN_CURSAR,
                    0,
                    null,
                    null
            );
            estudianteMaterias.add(estudianteMateria);
        }
        return repositorio.saveAll(estudianteMaterias);
    }

    @Override
    public List<EstudianteMateria> findAllByEstudianteIdAndCarreraId(Long estudianteId, Long carreraId) {
        log.info("Buscando todas las entidades EstudianteMateria con estudianteId: " + estudianteId + " y carreraId: " + carreraId + ".");
        List<EstudianteMateria> estudianteMaterias = repositorio.findAllByEstudianteIdAndCarreraId(estudianteId, carreraId);
        if (estudianteMaterias.isEmpty()) {
            log.info("La tabla no posee entidades EstudianteMateria con estudianteId: " + estudianteId + " y carreraId: " + carreraId + ".");
        } else {
            log.info("Se encontraron {} entidades EstudianteMateria.", estudianteMaterias.size());
        }
        return estudianteMaterias;
    }

    @Override
    public Optional<EstudianteMateria> findByEstudianteIdAndMateriaId(Long estudianteId, Long materiaId) {
        log.info("Buscando todas las entidades EstudianteMateria con estudianteId: " + estudianteId + " y materiaId: " + materiaId + ".");
        Optional<EstudianteMateria> estudianteMaterias = repositorio.findByEstudianteIdAndMateriaId(estudianteId, materiaId);
        if (estudianteMaterias.isEmpty()) {
            log.info("La tabla no posee entidades EstudianteMateria con estudianteId: " + estudianteId + " y carreraId: " + materiaId + ".");
        } else {
            log.info("Se encontraró entidades EstudianteMateria.");
        }
        return estudianteMaterias;
    }

    @Override
    public List<EstudianteMateria> findAllByCarreraId(Long id) {
        log.info("Buscando todas las entidades EstudianteMateria con idCarrera: " + id + ".");
        List<EstudianteMateria> estudianteMaterias = repositorio.findAllByCarreraId(id);
        if (estudianteMaterias.isEmpty()) {
            log.info("La tabla no posee entidades EstudianteMateria con idCarrera: " + id + ".");
        } else {
            log.info("Se encontraron {} entidades EstudianteMateria.", estudianteMaterias.size());
        }
        return estudianteMaterias;
    }

    @Override
    public List<EstudianteMateria> findAllByEstudianteId(Long id) {
        log.info("Buscando todas las entidades EstudianteMateria con idEstudiante: " + id + ".");
        List<EstudianteMateria> estudianteMaterias = repositorio.findAllByEstudianteId(id);
        if (estudianteMaterias.isEmpty()) {
            log.info("La tabla no posee entidades EstudianteMateria con idEstudiante: " + id + ".");
        } else {
            log.info("Se encontraron {} entidades EstudianteMateria.", estudianteMaterias.size());
        }
        return estudianteMaterias;
    }

    @Override
    public List<EstudianteMateria> findAllByMateriaId(Long id) {
        log.info("Buscando todas las entidades EstudianteMateria con idMateria: " + id + ".");
        List<EstudianteMateria> estudianteMaterias = repositorio.findAllByMateriaId(id);
        if (estudianteMaterias.isEmpty()) {
            log.info("La tabla no posee entidades EstudianteMateria con idMateria: " + id + ".");
        } else {
            log.info("Se encontraron {} entidades EstudianteMateria.", estudianteMaterias.size());
        }
        return estudianteMaterias;
    }

    @Override
    public Optional<EstudianteMateria> findById(Long id) {
        log.info("Buscando la entidad EstudianteMateria con id: {}.", id);
        Optional<EstudianteMateria> estudianteMateria = repositorio.findById(id);
        if (estudianteMateria.isEmpty()) {
            log.info("No existe la entidad EstudianteMateria con id: {}.", id);
        } else {
            log.info("Se encontró la entidad EstudianteMateria: {}.", estudianteMateria);
        }
        return estudianteMateria;
    }

    @Override
    public List<EstudianteMateria> findAll() {
        log.info("Buscando todas las entidades EstudianteMateria.");
        List<EstudianteMateria> estudianteMaterias = repositorio.findAll();
        if (estudianteMaterias.isEmpty()) {
            log.info("La tabla no posee entidades EstudianteMateria.");
        } else {
            log.info("Se encontraron {} entidades EstudianteMateria, incluidas las borradas.", estudianteMaterias.size());
        }
        return estudianteMaterias;
    }

    @Override
    public EstudianteMateria insert(EstudianteMateria estudianteMateria) throws Exception {
        try {
            log.info("Insertando la entidad EstudianteMateria: {}.",  estudianteMateria);
            repositorio.save(estudianteMateria);
            log.info("Se insertó correctamente la entidad EstudianteMateria: {}.", estudianteMateria);
            return estudianteMateria;
        } catch (Exception exepcion) {
            log.error("Se produjo un error al intentar persistir la entidad EstudianteMateria: {}. Se produjo la excepción {}.", estudianteMateria, exepcion);
            return null;
        }
    }

    @Override
    public EstudianteMateria update(EstudianteMateria estudianteMateria) {
        try {
            log.info("Actualizando la entidad EstudianteMateria: {}.", estudianteMateria);
            Optional<EstudianteMateria> entidad = repositorio.findById(estudianteMateria.getId());
            if (entidad.isEmpty()) {
                log.error("No existe la entidad EstudianteMateria que desea actualizar.");
                return null;
            }
            repositorio.save(estudianteMateria);
            log.info("Se actualizo correctamente la entidad EstudianteMateria: {}.", estudianteMateria);
            return estudianteMateria;
        } catch (Exception exepcion) {
            log.error("Se produjo un error al intentar modificar la entidad EstudianteMateria: {}. Se produjo la excepción {}.", estudianteMateria, exepcion);
            return null;
        }
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Borrando la entidad EstudianteMateria con id: {}.", id);
        Optional<EstudianteMateria> estudianteMateria = repositorio.findById(id);
        if (estudianteMateria.isEmpty()) {
            log.error("No se encontro ninguna entidad EstudianteMateria con id: {}.", id);
        }
        log.info("Borrando la entidad EstudianteMateria: {}.", estudianteMateria.get());
        repositorio.delete(estudianteMateria.get());
        return true;
    }
}
