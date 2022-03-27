package com.edmachina.quiron.helper;

import com.edmachina.quiron.model.*;
import com.edmachina.quiron.model.enumerator.EnumTituloGrado;
import com.edmachina.quiron.repository.CarreraRepository;
import com.edmachina.quiron.repository.EstudianteRepository;
import com.edmachina.quiron.repository.MateriaRepository;
import com.edmachina.quiron.service.implementation.*;
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
                    if (materia.isPresent()) {
                        materias.add(materia.get());
                    }
                    contador ++;
                }
                Carrera carrera = new Carrera(
                        faker.educator().course(),
                        EnumTituloGrado.valueOf(a % 3 == 0 ? "TITULO_PREGRADO" : a % 3 == 1 ? "TITULO_GRADO" : "TITULO_POSTGRADO"),
                        materias,
                        Helper.getHoy(),
                        null);
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
            for (int a = 0; a < 1; a++) {
                Set<Carrera> carreras = new HashSet<>();
                Optional<Carrera> carrera = carreraRepository.findById((long)faker.number().numberBetween(0, 20));
                carreras.add(carrera.get());
                Estudiante estudiante = new Estudiante(
                        faker.name().firstName(),
                        faker.name().lastName(),
                        faker.internet().emailAddress(),
                        faker.address().fullAddress(),
                        faker.phoneNumber().cellPhone(),
                        carreras
                );
                estudiantes.add(estudiante);
            }
            return estudianteRepository.saveAll(estudiantes);
        } catch (Exception e) {
            log.error("Hubo un error al generar datos, exepcion: {}.", e.getMessage());
            return null;
        }
    }
}
