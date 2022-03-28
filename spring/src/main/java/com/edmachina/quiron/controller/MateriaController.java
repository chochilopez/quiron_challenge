package com.edmachina.quiron.controller;

import com.edmachina.quiron.controller.form.MateriaForm;
import com.edmachina.quiron.helper.Helper;
import com.edmachina.quiron.model.Materia;
import com.edmachina.quiron.service.implementation.MateriaServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/materia")
@RequiredArgsConstructor
@RestController
public class MateriaController {

    private final MateriaServiceImplementation service;

    @GetMapping(value = "/cantidad")
    public ResponseEntity<Long> cantidad() {
        Long cantidad= service.cantidad();
        if (cantidad == 0)
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Hay 0 entidades en Materia.")).build();
        return new ResponseEntity<>(cantidad, Helper.cabeceraHTTP("Existen " + cantidad + " en Materia."), HttpStatus.OK);
    }

    @GetMapping(value = "/todas")
    public ResponseEntity<List<Materia>> getAll() {
        List<Materia> materias = service.findAll();
        if (materias.isEmpty())
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Materia no posee entidades.")).build();
        return new ResponseEntity<>(materias, Helper.cabeceraHTTP("Existen " + materias.size() + "entidades en Materia."), HttpStatus.OK);
    }

    @GetMapping(value = "/buscar-por-id/{id}")
    public ResponseEntity<Materia> findById(
            @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<Materia> materia = service.findById(id);
        if (materia.isPresent())
            return new ResponseEntity<>(materia.get(), Helper.cabeceraHTTP("Se encontró la entidad Materia con id: " + id + "."), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Materia no posee entidades con id: " + id + ".")).build();
    }

    @PutMapping
    public ResponseEntity<Materia> insert(@Valid @RequestBody MateriaForm materiaForm, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()){
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Fallo al validar el objeto: " + bindingResult)).build();
        }
        Materia materia = service.insert(materiaForm.formMateriaDTO(new Materia()));
        if ( materia == null) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Fallo al intentar persistir el objeto Materia.")).build();
        } else {
            return new ResponseEntity<>(materia, Helper.cabeceraHTTP("Se persistió correctamente la entidad Materia con id: " + materia.getId() + "."), HttpStatus.CREATED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Materia> update(@Valid @RequestBody MateriaForm MateriaForm, @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<Materia> objeto = service.findById(id);
        if (objeto.isEmpty()) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("No existe una entidad Materia modificable con id: " + id + ".")).build();
        }
        Materia entidad = service.update(MateriaForm.formMateriaDTO(new Materia()));
        return new ResponseEntity<>(entidad, Helper.cabeceraHTTP("Se modificó y persistió correctamente la entidad Materia con id: " + id + "."), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<Materia> entidad = service.findById(id);
        if (entidad.isEmpty()) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("No existe una entidad eliminable con id: " + id + ".")).build();
        }
        if (service.delete(id)) {
            String mensaje = "Se eliminó correctamente la entidad Materia con id: " + id + ".";
            return new ResponseEntity<>(mensaje, Helper.cabeceraHTTP(mensaje), HttpStatus.OK);
        } else {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP(
                    "Hubo un error al eliminar la entidad con id: " + id + "."
            )).build();
        }
    }
}
