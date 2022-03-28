package com.edmachina.quiron.service.implementation;

import com.edmachina.quiron.model.Carrera;
import com.edmachina.quiron.repository.CarreraRepository;
import com.edmachina.quiron.service.CarreraServiceInterfase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarreraServiceImplementation implements CarreraServiceInterfase {

    private final CarreraRepository repositorio;

    @Override
    public Long cantidad() {
        Long cuenta = repositorio.count();
        log.info("Existen {} entidades en Carrera.", cuenta);
        return cuenta;
    }

    @Override
    public Optional<Carrera> findById(Long id) {
        log.info("Buscando la entidad Carrera con id: {}.", id);
        Optional<Carrera> carrera = repositorio.findById(id);
        if (carrera.isEmpty()) {
            log.info("No existe la entidad Carrera con id: {}.", id);
        } else {
            log.info("Se encontró la entidad Carrera: {}.", carrera);
        }
        return carrera;
    }

    @Override
    public List<Carrera> findAll() {
        log.info("Buscando todas las entidades Carrera.");
        List<Carrera> carreras = repositorio.findAll();
        if (carreras.isEmpty()) {
            log.info("La tabla no posee entidades Carrera.");
        } else {
            log.info("Se encontraron {} entidades Carrera, incluidas las borradas.", carreras.size());
        }
        return carreras;
    }

    @Override
    public Carrera insert(Carrera carrera) throws Exception {
        try {
            log.info("Insertando la entidad Carrera: {}.",  carrera);
            repositorio.save(carrera);
            log.info("Se insertó correctamente la entidad Carrera: {}.", carrera);
            return carrera;
        } catch (Exception exepcion) {
            log.error("Se produjo un error al intentar persistir la entidad Carrera: {}. Se produjo la excepción {}.", carrera, exepcion);
            return null;
        }
    }

    @Override
    public Carrera update(Carrera carrera) {
        try {
            log.info("Actualizando la entidad Carrera: {}.", carrera);
            Optional<Carrera> entidad = repositorio.findById(carrera.getId());
            if (entidad.isEmpty()) {
                log.error("No existe la entidad Carrera que desea actualizar.");
                return null;
            }
            repositorio.save(carrera);
            log.info("Se actualizo correctamente la entidad Carrera: {}.", carrera);
            return carrera;
        } catch (Exception exepcion) {
            log.error("Se produjo un error al intentar modificar la entidad Carrera: {}. Se produjo la excepción {}.", carrera, exepcion);
            return null;
        }
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Borrando la entidad Carrera con id: {}.", id);
        Optional<Carrera> carrera = repositorio.findById(id);
        if (carrera.isEmpty()) {
            log.error("No se encontro ninguna entidad Carrera con id: {}.", id);
        }
        log.info("Borrando la entidad Carrera: {}.", carrera.get());
        repositorio.delete(carrera.get());
        return true;
    }
}
