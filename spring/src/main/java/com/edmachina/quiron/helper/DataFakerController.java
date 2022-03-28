package com.edmachina.quiron.helper;

import com.edmachina.quiron.model.Carrera;
import com.edmachina.quiron.model.Estudiante;
import com.edmachina.quiron.model.EstudianteCarrera;
import com.edmachina.quiron.model.Materia;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/faker")
@RequiredArgsConstructor
@RestController
public class DataFakerController {

    private final DataFakerServiceImplementarion service;

    @PostMapping(value = "/materias")
    public ResponseEntity<List<Materia>> generateAssign() {
        List<Materia> materias = service.generar1000Materias();
        if (materias.isEmpty())
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Hubo un error al crear las entidades.")).build();
        return new ResponseEntity<>(materias, Helper.cabeceraHTTP("Existen " + materias.size() + "entidades en Carrera."), HttpStatus.OK);
    }

    @PostMapping(value = "/carreras")
    public ResponseEntity<List<Carrera>> generateTitle() {
        List<Carrera> titulos = service.generar20Carreras();
        if (titulos.isEmpty())
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Hubo un error al crear las entidades.")).build();
        return new ResponseEntity<>(titulos, Helper.cabeceraHTTP("Existen " + titulos.size() + "entidades en Carrera."), HttpStatus.OK);
    }

    @PostMapping(value = "/estudiantes")
    public ResponseEntity<List<Estudiante>> generateStudent() {
        List<Estudiante> estudiantes = service.generar10000Estudiantes();
        if (estudiantes.isEmpty())
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Hubo un error al crear las entidades.")).build();
        return new ResponseEntity<>(estudiantes, Helper.cabeceraHTTP("Existen " + estudiantes.size() + "entidades en Estudiante."), HttpStatus.OK);
    }

    @PostMapping(value = "/estudiante-carrera")
    public ResponseEntity<List<EstudianteCarrera>> generateCareers() {
        List<EstudianteCarrera> estudiantes = service.inscribir300Estudiantes();
        if (estudiantes.isEmpty())
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Hubo un error al crear las entidades.")).build();
        return new ResponseEntity<>(estudiantes, Helper.cabeceraHTTP("Existen " + estudiantes.size() + "entidades en Estudiante."), HttpStatus.OK);
    }
}
