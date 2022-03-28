package com.edmachina.quiron.controller;

import com.edmachina.quiron.controller.form.EstudianteForm;
import com.edmachina.quiron.helper.Helper;
import com.edmachina.quiron.model.Estudiante;
import com.edmachina.quiron.service.implementation.EstudianteServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/estudiante")
@RequiredArgsConstructor
@RestController
public class EstudianteController {

    private final EstudianteServiceImplementation service;

    @GetMapping(value = "/cantidad")
    public ResponseEntity<Long> cantidad() {
        Long cantidad= service.cantidad();
        if (cantidad == 0)
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Hay 0 entidades en Estudiante.")).build();
        return new ResponseEntity<>(cantidad, Helper.cabeceraHTTP("Existen " + cantidad + " en Estudiante."), HttpStatus.OK);
    }

    @GetMapping(value = "/cantidad/{status}")
    public ResponseEntity<Long> cantidad(@PathVariable(name = "status") @javax.validation.constraints.Size(min = 8, max = 20) String status) {
        Long cantidad= service.countByStatus(status);
        if (cantidad == 0)
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Hay 0 entidades con status " + status + ".")).build();
        return new ResponseEntity<>(cantidad, Helper.cabeceraHTTP("Existen " + cantidad + " entidades con status " + status + "."), HttpStatus.OK);
    }

    @GetMapping(value = "/todas")
    public ResponseEntity<List<Estudiante>> getAll() {
        List<Estudiante> estudiantes = service.findAll();
        if (estudiantes.isEmpty())
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Estudiante no posee entidades.")).build();
        return new ResponseEntity<>(estudiantes, Helper.cabeceraHTTP("Existen " + estudiantes.size() + " entidades en Estudiante."), HttpStatus.OK);
    }

    @GetMapping(value = "/buscar-por-id/{id}")
    public ResponseEntity<Estudiante> findById(
            @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<Estudiante> estudiante = service.findById(id);
        if (estudiante.isPresent())
            return new ResponseEntity<>(estudiante.get(), Helper.cabeceraHTTP("Se encontró la entidad Estudiante con id: " + id + "."), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Estudiante no posee entidades con id: " + id + ".")).build();
    }

    @PutMapping
    public ResponseEntity<Estudiante> insert(@Valid @RequestBody EstudianteForm estudianteForm, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()){
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Fallo al validar el objeto: " + bindingResult)).build();
        }
        Estudiante estudiante = service.insert(estudianteForm.formEstudianteDTO(new Estudiante()));
        if ( estudiante == null) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Fallo al intentar persistir el objeto Estudiante.")).build();
        } else {
            return new ResponseEntity<>(estudiante, Helper.cabeceraHTTP("Se persistió correctamente la entidad Estudiante con id: " + estudiante.getId() + "."), HttpStatus.CREATED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> update(@Valid @RequestBody EstudianteForm EstudianteForm, @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<Estudiante> objeto = service.findById(id);
        if (objeto.isEmpty()) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("No existe una entidad Estudiante modificable con id: " + id + ".")).build();
        }
        Estudiante entidad = service.update(EstudianteForm.formEstudianteDTO(new Estudiante()));
        return new ResponseEntity<>(entidad, Helper.cabeceraHTTP("Se modificó y persistió correctamente la entidad Estudiante con id: " + id + "."), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<Estudiante> entidad = service.findById(id);
        if (entidad.isEmpty()) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("No existe una entidad eliminable con id: " + id + ".")).build();
        }
        if (service.delete(id)) {
            String mensaje = "Se eliminó correctamente la entidad Estudiante con id: " + id + ".";
            return new ResponseEntity<>(mensaje, Helper.cabeceraHTTP(mensaje), HttpStatus.OK);
        } else {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP(
                    "Hubo un error al eliminar la entidad con id: " + id + "."
            )).build();
        }
    }
}
