package com.edmachina.quiron.helper;

import com.edmachina.quiron.controller.form.CarreraForm;
import com.edmachina.quiron.model.Carrera;
import com.edmachina.quiron.model.Estudiante;
import com.edmachina.quiron.model.Materia;
import com.edmachina.quiron.model.Titulo;
import com.edmachina.quiron.service.implementation.CarreraServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/faker")
@RequiredArgsConstructor
@RestController
public class DataFakerController {

    private final DataFakerServiceImplementarion service;

    @GetMapping(value = "/materias")
    public ResponseEntity<List<Materia>> generateAssign() {
        List<Materia> materias = service.generar1000Materias();
        if (materias.isEmpty())
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Hubo un error al crear las entidades.")).build();
        return new ResponseEntity<>(materias, Helper.cabeceraHTTP("Existen " + materias.size() + "entidades en Carrera."), HttpStatus.OK);
    }

    @GetMapping(value = "/titulos")
    public ResponseEntity<List<Titulo>> generateTitle() {
        List<Titulo> titulos = service.generar20Titulos();
        if (titulos.isEmpty())
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Hubo un error al crear las entidades.")).build();
        return new ResponseEntity<>(titulos, Helper.cabeceraHTTP("Existen " + titulos.size() + "entidades en Carrera."), HttpStatus.OK);
    }

    @GetMapping(value = "/estudiantes")
    public ResponseEntity<List<Estudiante>> generateStudent() {
        List<Estudiante> estudiantes = service.generar10000Estudiantes();
        if (estudiantes.isEmpty())
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Hubo un error al crear las entidades.")).build();
        return new ResponseEntity<>(estudiantes, Helper.cabeceraHTTP("Existen " + estudiantes.size() + "entidades en Estudiante."), HttpStatus.OK);
    }
}
