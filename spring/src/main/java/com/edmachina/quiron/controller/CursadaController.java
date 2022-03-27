package com.edmachina.quiron.controller;

import com.edmachina.quiron.controller.form.CursadaForm;
import com.edmachina.quiron.helper.Helper;
import com.edmachina.quiron.model.Cursada;
import com.edmachina.quiron.service.implementation.CursadaServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/cursada")
@RequiredArgsConstructor
@RestController
public class CursadaController {

    private final CursadaServiceImplementation service;

    @GetMapping(value = "/todas")
    public ResponseEntity<List<Cursada>> getAll() {
        List<Cursada> cursadas = service.findAll();
        if (cursadas.isEmpty())
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Cursada no posee entidades.")).build();
        return new ResponseEntity<>(cursadas, Helper.cabeceraHTTP("Existen " + cursadas.size() + "entidades en Cursada."), HttpStatus.OK);
    }

    @GetMapping(value = "/buscar-por-id/{idEstudiante}/{idMateria}")
    public ResponseEntity<Cursada> findById(
            @PathVariable(name = "idEstudiante") @javax.validation.constraints.Size(min = 1, max = 10) Long idEstudiante,
            @PathVariable(name = "idMateria") @javax.validation.constraints.Size(min = 1, max = 10) Long idMateria
    ) {
        Optional<Cursada> cursada = service.findByEstudianteIdAndMateriaId(idEstudiante, idMateria);
        if (cursada.isPresent())
            return new ResponseEntity<>(cursada.get(), Helper.cabeceraHTTP(
                    "Se encontró la entidad Cursada con idEstudiante " + idEstudiante + " e idMateria " + idMateria + "."
            ), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP(
                    "Cursada no posee entidades con idEstudiante " + idEstudiante + " e idMateria " + idMateria + "."
            )).build();
    }

    @PutMapping
    public ResponseEntity<Cursada> insert(@Valid @RequestBody CursadaForm cursadaForm, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()){
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Fallo al validar el objeto: " + bindingResult)).build();
        }
        Cursada cursada = service.insert(cursadaForm.formCursadaDTO(new Cursada()));
        if ( cursada == null) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP(
                    "Fallo al intentar persistir el objeto Cursada."
            )).build();
        } else {
            return new ResponseEntity<>(cursada, Helper.cabeceraHTTP(
                    "Se persistió correctamente la entidad Cursada con idEstudiante " + cursada.getEstudiante().getId() + " e idMateria " + cursada.getMateria().getId() + "."
            ), HttpStatus.CREATED);
        }
    }

    @PutMapping("/{idEstudiante}/{idMateria}")
    public ResponseEntity<Cursada> update(
            @Valid @RequestBody CursadaForm CursadaForm,
            @PathVariable(name = "idEstudiante") @javax.validation.constraints.Size(min = 1, max = 10) Long idEstudiante,
            @PathVariable(name = "idMateria") @javax.validation.constraints.Size(min = 1, max = 10) Long idMateria
    ) {
        Optional<Cursada> objeto = service.findByEstudianteIdAndMateriaId(idEstudiante, idMateria);
        if (objeto.isEmpty()) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP(
                    "No existe una entidad Cursada modificable con idEstudiante " + idEstudiante + " e idMateria " + idMateria + "."
            )).build();
        }
        Cursada entidad = service.update(CursadaForm.formCursadaDTO(new Cursada()));
        return new ResponseEntity<>(entidad, Helper.cabeceraHTTP(
                "Se modificó y persistió correctamente la entidad Cursada con idEstudiante " + idEstudiante + " e idMateria " + idMateria + "."
        ), HttpStatus.CREATED);
    }

    @DeleteMapping("/{idEstudiante}/{idMateria}")
    public ResponseEntity<String> delete(
            @PathVariable(name = "idEstudiante") @javax.validation.constraints.Size(min = 1, max = 10) Long idEstudiante,
            @PathVariable(name = "idMateria") @javax.validation.constraints.Size(min = 1, max = 10) Long idMateria
    ) {
        Optional<Cursada> entidad = service.findByEstudianteIdAndMateriaId(idEstudiante, idMateria);
        if (entidad.isEmpty()) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP(
                    "No existe una entidad eliminable con idEstudiante " + idEstudiante + " e idMateria " + idMateria + "."
            )).build();
        }
        if (service.delete(idEstudiante, idMateria)) {
            String mensaje = "Se eliminó correctamente la entidad Cursada con idEstudiante " + idEstudiante + " e idMateria " + idMateria + ".";
            return new ResponseEntity<>(mensaje, Helper.cabeceraHTTP(mensaje), HttpStatus.OK);
        } else {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP(
                    "Hubo un error al eliminar la entidad con idEstudiante " + idEstudiante + " e idMateria " + idMateria + "."
            )).build();
        }
    }
}
