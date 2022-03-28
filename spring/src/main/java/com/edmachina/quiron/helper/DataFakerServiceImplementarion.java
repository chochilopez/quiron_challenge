package com.edmachina.quiron.helper;

import com.edmachina.quiron.model.*;
import com.edmachina.quiron.model.enumerator.EnumEstadoEstudiante;
import com.edmachina.quiron.model.enumerator.EnumEstadoMateria;
import com.edmachina.quiron.model.enumerator.EnumTituloGrado;
import com.edmachina.quiron.repository.*;
import com.edmachina.quiron.service.implementation.EstudianteCarreraServiceImplementation;
import com.edmachina.quiron.service.implementation.EstudianteMateriaServiceImplementation;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataFakerServiceImplementarion {
    private final MateriaRepository materiaRepository;
    private final CarreraRepository carreraRepository;
    private final EstudianteRepository estudianteRepository;
    private final EstudianteCarreraServiceImplementation estudianteCarreraServiceImplementation;

    private final Faker faker = new Faker(new Locale("es"));


    public List<Materia> generar1000Materias() {
        try {
            List<Materia> materias = new ArrayList<>();
            for (int a = 0; a < 1000; a++) {
                Materia materia = new Materia();
                materia.setNombre(faker.name().title());
                materias.add(materia);
            }
            log.info("Se generaron correctamente las materias");
            return materiaRepository.saveAll(materias);
        } catch (Exception e) {
            log.error("Hubo un error al generar datos, exepcion: {}.", e.getMessage());
            return null;
        }
    }

    public List<Carrera> generar20Carreras() {
        try {
            List<Carrera> carreras = new ArrayList<>();
            long contador = 0;
            for (int a = 0; a < 20; a++) {
                Set<Materia> materias = new HashSet<>();
                for (int b = 0; b < 40; b++) {
                    Optional<Materia> materia = materiaRepository.findById(contador);
                    materia.ifPresent(materias::add);
                    contador ++;
                }
                Carrera carrera = new Carrera(
                        faker.educator().course(),
                        EnumTituloGrado.valueOf(a % 3 == 0 ? "TITULO_PREGRADO" : a % 3 == 1 ? "TITULO_GRADO" : "TITULO_POSTGRADO"),
                        materias

                );
                carreras.add(carrera);
            }
            return carreraRepository.saveAll(carreras);
        } catch (Exception e) {
            log.error("Hubo un error al generar datos, exepcion: {}.", e.getMessage());
            return null;
        }
    }

    public List<Estudiante> generar10000Estudiantes() {
        try {
            List<Estudiante> estudiantes = new ArrayList<>();
            for (int a = 0; a < 1000; a++) {
                Estudiante estudiante = new Estudiante(
                        faker.name().firstName(),
                        faker.name().lastName(),
                        faker.internet().emailAddress(),
                        faker.address().fullAddress(),
                        faker.phoneNumber().cellPhone(),
                        EnumEstadoEstudiante.ESTADO_LEAD,
                        Helper.getHoy(),
                        null
                );
                estudiantes.add(estudiante);
            }
            return estudianteRepository.saveAll(estudiantes);
        } catch (Exception e) {
            log.error("Hubo un error al generar datos, exepcion: {}.", e.getMessage());
            return null;
        }
    }

    public List<EstudianteCarrera> inscribir300Estudiantes() {
        try {
            List<EstudianteCarrera> estudianteCarreras = new ArrayList<>();
            for (int a = 0; a < 300; a++) {
                Optional<Estudiante> estudiante=estudianteRepository.findById((long)faker.number().numberBetween(0, 999));
                Optional<Carrera> carrera=carreraRepository.findById((long)faker.number().numberBetween(0, 19));
                if (estudiante.isPresent() && carrera.isPresent()) {
                    EstudianteCarrera inscripto = estudianteCarreraServiceImplementation.inscribirEstudiante(estudiante.get().getId(), carrera.get().getId());
                    if (inscripto != null) {
                        estudianteCarreras.add(inscripto);
                    }
                }
            }
            return estudianteCarreras;
        } catch (Exception e) {
            log.error("Hubo un error al generar datos, exepcion: {}.", e.getMessage());
            return null;
        }
    }
}
