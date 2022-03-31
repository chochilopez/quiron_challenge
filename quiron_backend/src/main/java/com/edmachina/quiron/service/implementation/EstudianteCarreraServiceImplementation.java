package com.edmachina.quiron.service.implementation;

import com.edmachina.quiron.helper.Helper;
import com.edmachina.quiron.model.Carrera;
import com.edmachina.quiron.model.Estudiante;
import com.edmachina.quiron.model.EstudianteCarrera;
import com.edmachina.quiron.model.enumerator.EnumEstadoEstudiante;
import com.edmachina.quiron.repository.CarreraRepository;
import com.edmachina.quiron.repository.EstudianteCarreraRepository;
import com.edmachina.quiron.repository.EstudianteRepository;
import com.edmachina.quiron.service.EstudianteCarreraServiceInterfase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstudianteCarreraServiceImplementation implements EstudianteCarreraServiceInterfase {

    private final EstudianteCarreraRepository repositorio;
    private final EstudianteRepository estudianteRepository;
    private final CarreraRepository carreraRepository;

    private final EstudianteMateriaServiceImplementation estudianteMateriaServiceImplementation;

    @Override
    public Long cantidad() {
        Long cuenta = repositorio.count();
        log.info("Existen {} entidades en EstudianteCarrera.", cuenta);
        return cuenta;
    }


    @Override
    public EstudianteCarrera inscribirEstudiante(Long idEstudiante, Long idCarrera) {
        try {
            Optional<Estudiante> estudiante=estudianteRepository.findById(idEstudiante);
            Optional<Carrera> carrera=carreraRepository.findById(idCarrera);
            if (carrera.isPresent() && estudiante.isPresent()) {
                if (!repositorio.existsByEstudianteIdAndCarreraId(idEstudiante, idCarrera)) {
                    EstudianteCarrera estudianteCarrera=new EstudianteCarrera(estudiante.get(), carrera.get(), Helper.getHoy(), null);
                    if (estudiante.get().getStatus() == EnumEstadoEstudiante.ESTADO_LEAD) {
                        estudiante.get().setStatus(EnumEstadoEstudiante.ESTADO_ESTUDIANTE);
                        estudiante.get().setIngresoEstudiante(Helper.getHoy());
                        estudianteRepository.save(estudiante.get());
                    }
                    repositorio.save(estudianteCarrera);
                    estudianteMateriaServiceImplementation.inscribirMaterias(estudianteCarrera);
                    return estudianteCarrera;
                }
            }
            log.warn("Ocurrio un error al inscribir el estudiante: {} a la carrera: {}.", idEstudiante, idCarrera);
        } catch (Exception exepcion) {
            log.warn("Ocurrio un error al inscribir el estudiante: {} a la carrera: {}. Exepcion: {}.", idEstudiante, idCarrera, exepcion);
        }
        return null;
    }

    @Override
    public Optional<EstudianteCarrera> findByEstudianteIdAndCarreraId(Long estudianteId, Long carreraId) {
        log.info("Buscando la entidades EstudianteMateria con estudianteId: " + estudianteId + " y carreraId: " + carreraId + ".");
        Optional<EstudianteCarrera> estudianteCarrera = repositorio.findByEstudianteIdAndCarreraId(estudianteId, carreraId);
        if (estudianteCarrera.isEmpty()) {
            log.info("La tabla no posee entidades EstudianteCarrera con estudianteId: " + estudianteId + " y carreraId: " + carreraId + ".");
        } else {
            log.info("Se encontraró entidades EstudianteCarrera.");
        }
        return estudianteCarrera;
    }

    @Override
    public List<EstudianteCarrera> findAllByEstudianteId(Long id) {
        log.info("Buscando todas las entidades EstudianteCarrera con idEstudiante: " + id + ".");
        List<EstudianteCarrera> estudianteCarreras = repositorio.findAllByEstudianteId(id);
        if (estudianteCarreras.isEmpty()) {
            log.info("La tabla no posee entidades EstudianteCarrera con idEstudiante: " + id + ".");
        } else {
            log.info("Se encontraron {} entidades EstudianteCarrera.", estudianteCarreras.size());
        }
        return estudianteCarreras;
    }

    @Override
    public List<EstudianteCarrera> findAllByCarreraId(Long id) {
        log.info("Buscando todas las entidades EstudianteCarrera con idCarrera: " + id + ".");
        List<EstudianteCarrera> estudianteCarreras = repositorio.findAllByCarreraId(id);
        if (estudianteCarreras.isEmpty()) {
            log.info("La tabla no posee entidades EstudianteCarrera con idCarrera: " + id + ".");
        } else {
            log.info("Se encontraron {} entidades EstudianteCarrera.", estudianteCarreras.size());
        }
        return estudianteCarreras;
    }

    @Override
    public Optional<EstudianteCarrera> findById(Long id) {
        log.info("Buscando la entidad EstudianteCarrera con id: {}.", id);
        Optional<EstudianteCarrera> estudianteCarrera = repositorio.findById(id);
        if (estudianteCarrera.isEmpty()) {
            log.info("No existe la entidad EstudianteCarrera con id: {}.", id);
        } else {
            log.info("Se encontró la entidad EstudianteCarrera: {}.", estudianteCarrera);
        }
        return estudianteCarrera;
    }

    @Override
    public List<EstudianteCarrera> findAll() {
        log.info("Buscando todas las entidades EstudianteCarrera.");
        List<EstudianteCarrera> estudianteCarreras = repositorio.findAll();
        if (estudianteCarreras.isEmpty()) {
            log.info("La tabla no posee entidades EstudianteCarrera.");
        } else {
            log.info("Se encontraron {} entidades EstudianteCarrera.", estudianteCarreras.size());
        }
        return estudianteCarreras;
    }

    @Override
    public EstudianteCarrera insert(EstudianteCarrera estudianteCarrera) throws Exception {
        try {
            log.info("Insertando la entidad EstudianteCarrera: {}.",  estudianteCarrera);
            repositorio.save(estudianteCarrera);
            log.info("Se insertó correctamente la entidad EstudianteCarrera: {}.", estudianteCarrera);
            return estudianteCarrera;
        } catch (Exception exepcion) {
            log.error("Se produjo un error al intentar persistir la entidad EstudianteCarrera: {}. Se produjo la excepción {}.", estudianteCarrera, exepcion);
            return null;
        }
    }

    @Override
    public EstudianteCarrera update(EstudianteCarrera estudianteCarrera) {
        try {
            log.info("Actualizando la entidad EstudianteCarrera: {}.", estudianteCarrera);
            Optional<EstudianteCarrera> entidad = repositorio.findById(estudianteCarrera.getId());
            if (entidad.isEmpty()) {
                log.error("No existe la entidad EstudianteCarrera que desea actualizar.");
                return null;
            }
            repositorio.save(estudianteCarrera);
            log.info("Se actualizo correctamente la entidad EstudianteCarrera: {}.", estudianteCarrera);
            return estudianteCarrera;
        } catch (Exception exepcion) {
            log.error("Se produjo un error al intentar modificar la entidad EstudianteCarrera: {}. Se produjo la excepción {}.", estudianteCarrera, exepcion);
            return null;
        }
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Borrando la entidad EstudianteCarrera con id: {}.", id);
        Optional<EstudianteCarrera> estudianteCarrera = repositorio.findById(id);
        if (estudianteCarrera.isEmpty()) {
            log.error("No se encontro ninguna entidad EstudianteCarrera con id: {}.", id);
        }
        log.info("Borrando la entidad EstudianteCarrera: {}.", estudianteCarrera.get());
        repositorio.delete(estudianteCarrera.get());
        return true;
    }
}
