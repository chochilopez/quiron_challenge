package com.edmachina.quiron.helper;

import com.edmachina.quiron.model.*;
import com.edmachina.quiron.model.enumerator.EnumGradoTitulo;
import com.edmachina.quiron.repository.EstudianteRepository;
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
    private final TituloServiceImplementation tituloService;
    private final MateriaServiceImplementation materiaService;
    private final EstudianteServiceImplementation estudianteService;
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
            return materiaService.saveAll(materias);
        } catch (Exception e) {
            log.error("Hubo un error al generar datos, exepcion: {}.", e.getMessage());
            return null;
        }
    }

    public List<Titulo> generar20Titulos() {
        try {
            List<Titulo> titulos = new ArrayList<>();
            long contador = 0;
            for (int a = 0; a < 20; a++) {
                Titulo titulo = new Titulo();
                titulo.setNombre(faker.educator().course());
                titulo.setResolucion(String.valueOf(faker.number().numberBetween(10000, 20000)));
                titulo.setGrado(EnumGradoTitulo.valueOf(a % 3 == 0 ? "ESTADO_GRADO_PREGRADO" : a % 3 == 1 ? "ESTADO_GRADO_GRADO" : "ESTADO_GRADO_POSTGRADO"));
                Set<Materia> materias = new HashSet<>();
                for (int b = 0; b < 40; b++) {
                    Optional<Materia> materia = materiaService.findById(contador);
                    if (materia.isPresent()) {
                        materias.add(materia.get());
                    }
                    contador ++;
                }
                titulo.setMaterias(materias);
                titulos.add(titulo);
            }
            return tituloService.saveAll(titulos);
        } catch (Exception e) {
            log.error("Hubo un error al generar datos, exepcion: {}.", e.getMessage());
            return null;
        }
    }

    public List<Estudiante> generar10000Estudiantes() {
        try {
            List<Estudiante> estudiantes = new ArrayList<>();
            for (int a = 0; a < 10000; a++) {
                Estudiante estudiante = new Estudiante();
                estudiante.setNombre(faker.name().firstName());
                estudiante.setApellido(faker.name().lastName());
                estudiante.setEmail(faker.internet().emailAddress());
                estudiante.setDireccion(faker.address().fullAddress());
                estudiante.setTelefono(faker.phoneNumber().cellPhone());
                estudiante = estudianteRepository.save(estudiante);
                estudianteService.addCareer(estudiante.getId(), (long)faker.number().numberBetween(0, 19));
                estudiantes.add(estudiante);
            }
            return estudiantes;
        } catch (Exception e) {
            log.error("Hubo un error al generar datos, exepcion: {}.", e.getMessage());
            return null;
        }
    }
}
