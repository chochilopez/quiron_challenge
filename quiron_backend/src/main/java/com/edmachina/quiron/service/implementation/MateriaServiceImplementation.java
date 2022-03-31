package com.edmachina.quiron.service.implementation;

import com.edmachina.quiron.model.Materia;
import com.edmachina.quiron.repository.MateriaRepository;
import com.edmachina.quiron.service.MateriaServiceInterfase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MateriaServiceImplementation implements MateriaServiceInterfase {

    private final MateriaRepository repositorio;

    @Override
    public Long cantidad() {
        Long cuenta = repositorio.count();
        log.info("Existen {} entidades en Materia.", cuenta);
        return cuenta;
    }

    @Override
    public Optional<Materia> findById(Long id) {
        log.info("Buscando la entidad Materia con id: {}.", id);
        Optional<Materia> materia = repositorio.findById(id);
        if (materia.isEmpty()) {
            log.info("No existe la entidad Materia con id: {}.", id);
        } else {
            log.info("Se encontró la entidad Materia: {}.", materia);
        }
        return materia;
    }

    @Override
    public List<Materia> findAll() {
        log.info("Buscando todas las entidades Materia.");
        List<Materia> materias = repositorio.findAll();
        if (materias.isEmpty()) {
            log.info("La tabla no posee entidades Materia.");
        } else {
            log.info("Se encontraron {} entidades Materia.", materias.size());
        }
        return materias;
    }

    @Override
    public Materia insert(Materia materia) throws Exception {
        try {
            log.info("Insertando la entidad Materia: {}.",  materia);
            repositorio.save(materia);
            log.info("Se insertó correctamente la entidad Materia: {}.", materia);
            return materia;
        } catch (Exception exepcion) {
            log.error("Se produjo un error al intentar persistir la entidad Materia: {}. Se produjo la excepción {}.", materia, exepcion);
            return null;
        }
    }

    @Override
    public Materia update(Materia materia) {
        try {
            log.info("Actualizando la entidad Materia: {}.", materia);
            Optional<Materia> entidad = repositorio.findById(materia.getId());
            if (entidad.isEmpty()) {
                log.error("No existe la entidad Materia que desea actualizar.");
                return null;
            }
            repositorio.save(materia);
            log.info("Se actualizo correctamente la entidad Materia: {}.", materia);
            return materia;
        } catch (Exception exepcion) {
            log.error("Se produjo un error al intentar modificar la entidad Materia: {}. Se produjo la excepción {}.", materia, exepcion);
            return null;
        }
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Borrando la entidad Materia con id: {}.", id);
        Optional<Materia> materia = repositorio.findById(id);
        if (materia.isEmpty()) {
            log.error("No se encontro ninguna entidad Materia con id: {}.", id);
            return false;
        }
        log.info("Borrando la entidad Materia: {}.", materia.get());
        repositorio.delete(materia.get());
        return true;
    }
}
