package com.edmachina.quiron.controller;

import com.edmachina.quiron.controller.form.CarreraForm;
import com.edmachina.quiron.helper.Helper;
import com.edmachina.quiron.model.Carrera;
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
@RequestMapping(value = "/api/carrera")
@RequiredArgsConstructor
@RestController
public class CarreraController {

    private final CarreraServiceImplementation service;

    @GetMapping(value = "/todas")
    public ResponseEntity<List<Carrera>> getAll() {
        List<Carrera> carreras = service.findAll();
        if (carreras.isEmpty())
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Carrera no posee entidades.")).build();
        return new ResponseEntity<>(carreras, Helper.cabeceraHTTP("Existen " + carreras.size() + "entidades en Carrera."), HttpStatus.OK);
    }

    @GetMapping(value = "/buscar-por-id/{idEstudiante}/{idTitulo}")
    public ResponseEntity<Carrera> findById(
            @PathVariable(name = "idEstudiante") @javax.validation.constraints.Size(min = 1, max = 10) Long idEstudiante,
            @PathVariable(name = "idTitulo") @javax.validation.constraints.Size(min = 1, max = 10) Long idTitulo
    ) {
        Optional<Carrera> carrera = service.findByEstudianteIdAndTituloId(idEstudiante, idTitulo);
        if (carrera.isPresent())
            return new ResponseEntity<>(carrera.get(), Helper.cabeceraHTTP(
                    "Se encontró la entidad Carrera con idEstudiante " + idEstudiante + " e idTitulo " + idTitulo + "."
            ), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP(
                    "Carrera no posee entidades con idEstudiante " + idEstudiante + " e idTitulo " + idTitulo + "."
            )).build();
    }

    @PutMapping
    public ResponseEntity<Carrera> insert(@Valid @RequestBody CarreraForm carreraForm, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()){
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Fallo al validar el objeto: " + bindingResult)).build();
        }
        Carrera carrera = service.insert(carreraForm.formCarreraDTO(new Carrera()));
        if ( carrera == null) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP(
                    "Fallo al intentar persistir el objeto Carrera."
            )).build();
        } else {
            return new ResponseEntity<>(carrera, Helper.cabeceraHTTP(
                    "Se persistió correctamente la entidad Carrera con idEstudiante " + carrera.getEstudiante().getId() + " e idTitulo " + carrera.getTitulo().getId() + "."
            ), HttpStatus.CREATED);
        }
    }

    @PutMapping("/{idEstudiante}/{idTitulo}")
    public ResponseEntity<Carrera> update(
            @Valid @RequestBody CarreraForm CarreraForm,
            @PathVariable(name = "idEstudiante") @javax.validation.constraints.Size(min = 1, max = 10) Long idEstudiante,
            @PathVariable(name = "idTitulo") @javax.validation.constraints.Size(min = 1, max = 10) Long idTitulo
    ) {
        Optional<Carrera> objeto = service.findByEstudianteIdAndTituloId(idEstudiante, idTitulo);
        if (objeto.isEmpty()) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP(
                    "No existe una entidad Carrera modificable con idEstudiante " + idEstudiante + " e idTitulo " + idTitulo + "."
            )).build();
        }
        Carrera entidad = service.update(CarreraForm.formCarreraDTO(new Carrera()));
        return new ResponseEntity<>(entidad, Helper.cabeceraHTTP(
                "Se modificó y persistió correctamente la entidad Carrera con idEstudiante " + idEstudiante + " e idTitulo " + idTitulo + "."
        ), HttpStatus.CREATED);
    }

    @DeleteMapping("/{idEstudiante}/{idTitulo}")
    public ResponseEntity<String> delete(
            @PathVariable(name = "idEstudiante") @javax.validation.constraints.Size(min = 1, max = 10) Long idEstudiante,
            @PathVariable(name = "idTitulo") @javax.validation.constraints.Size(min = 1, max = 10) Long idTitulo
    ) {
        Optional<Carrera> entidad = service.findByEstudianteIdAndTituloId(idEstudiante, idTitulo);
        if (entidad.isEmpty()) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP(
                    "No existe una entidad eliminable con idEstudiante " + idEstudiante + " e idTitulo " + idTitulo + "."
            )).build();
        }
        if (service.delete(idEstudiante, idTitulo)) {
            String mensaje = "Se eliminó correctamente la entidad Carrera con idEstudiante " + idEstudiante + " e idTitulo " + idTitulo + ".";
            return new ResponseEntity<>(mensaje, Helper.cabeceraHTTP(mensaje), HttpStatus.OK);
        } else {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP(
                    "Hubo un error al eliminar la entidad con idEstudiante " + idEstudiante + " e idTitulo " + idTitulo + "."
            )).build();
        }
    }
}
