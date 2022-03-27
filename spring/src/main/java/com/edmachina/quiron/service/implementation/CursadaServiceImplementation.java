package com.edmachina.quiron.service.implementation;

import com.edmachina.quiron.model.Cursada;
import com.edmachina.quiron.repository.CursadaRepository;
import com.edmachina.quiron.service.CursadaServiceInterfase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CursadaServiceImplementation implements CursadaServiceInterfase {

    private final CursadaRepository repositorio;

    @Override
    public Optional<Cursada> findByEstudianteIdAndMateriaId(Long idEstudiante, Long idMateria) {
        log.info("Buscando la entidad Cursada con id_estudiante: {} e id_materia: {}.", idEstudiante, idMateria);
        Optional<Cursada> cursada = repositorio.findByEstudianteIdAndMateriaId(idEstudiante, idMateria);
        if (cursada.isEmpty()) {
            log.info("No existe la entidad Cursada con id_estudiante: {} e id_materia: {}.", idEstudiante, idMateria);
        } else {
            log.info("Se encontró la entidad Cursada: {}.", cursada);
        }
        return cursada;
    }

    @Override
    public List<Cursada> findAll() {
        log.info("Buscando todas las entidades Cursada.");
        List<Cursada> cursadas = repositorio.findAll();
        if (cursadas.isEmpty()) {
            log.info("La tabla no posee entidades Cursada.");
        } else {
            log.info("Se encontraron {} entidades Cursada, incluidas las borradas.", cursadas.size());
        }
        return cursadas;
    }

    @Override
    public List<Cursada> saveAll(List<Cursada> cursadas) throws Exception {
        try {
            log.info("Insertando listado de entidades Cursada.");
            return repositorio.saveAll(cursadas);
        } catch (Exception exepcion) {
            log.error("Se produjo un error al intentar persistir el listado de entidades Cursada.");
            return null;
        }
    }

    @Override
    public Cursada insert(Cursada cursada) throws Exception {
        try {
            log.info("Insertando la entidad Cursada: {}.",  cursada);
            repositorio.save(cursada);
            log.info("Se insertó correctamente la entidad Cursada: {}.", cursada);
            return cursada;
        } catch (Exception exepcion) {
            log.error("Se produjo un error al intentar persistir la entidad Cursada: {}. Se produjo la excepción {}.", cursada, exepcion);
            return null;
        }
    }

    @Override
    public Cursada update(Cursada cursada) {
        try {
            log.info("Actualizando la entidad Cursada: {}.", cursada);
            Optional<Cursada> entidad = repositorio.findByEstudianteIdAndMateriaId(cursada.getEstudiante().getId(), cursada.getMateria().getId());
            if (entidad.isEmpty()) {
                log.error("No existe la entidad Cursada que desea actualizar.");
                return null;
            }
            repositorio.save(cursada);
            log.info("Se actualizo correctamente la entidad Cursada: {}.", cursada);
            return cursada;
        } catch (Exception exepcion) {
            log.error("Se produjo un error al intentar modificar la entidad Cursada: {}. Se produjo la excepción {}.", cursada, exepcion);
            return null;
        }
    }

    @Override
    public Boolean delete(Long idEstudiante, Long idMateria) {
        log.info("Borrando la entidad Cursada con id_estudiante: {} e id_materia: {}.", idEstudiante, idMateria);
        Optional<Cursada> cursada = repositorio.findByEstudianteIdAndMateriaId(idEstudiante, idMateria);
        if (cursada.isEmpty()) {
            log.error("No se encontro ninguna entidad Cursada con id_estudiante: {} e id_materia: {}.", idEstudiante, idMateria);
            return false;
        }
        log.info("Borrando la entidad Cursada: {}.", cursada.get());
        repositorio.delete(cursada.get());
        return true;
    }
}
