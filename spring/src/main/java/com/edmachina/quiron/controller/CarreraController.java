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

    @GetMapping(value = "/cantidad")
    public ResponseEntity<Long> cantidad() {
        Long cantidad= service.cantidad();
        if (cantidad == 0)
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Hay 0 entidades en Carrera.")).build();
        return new ResponseEntity<>(cantidad, Helper.cabeceraHTTP("Existen " + cantidad + " en Carrera."), HttpStatus.OK);
    }

    @GetMapping(value = "/todas")
    public ResponseEntity<List<Carrera>> getAll() {
        List<Carrera> carreras = service.findAll();
        if (carreras.isEmpty())
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Carrera no posee entidades.")).build();
        return new ResponseEntity<>(carreras, Helper.cabeceraHTTP("Existen " + carreras.size() + "entidades en Carrera."), HttpStatus.OK);
    }

    @GetMapping(value = "/buscar-por-id/{id}")
    public ResponseEntity<Carrera> findById(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<Carrera> carrera = service.findById(id);
        if (carrera.isPresent())
            return new ResponseEntity<>(carrera.get(), Helper.cabeceraHTTP("Se encontró la entidad Carrera con id: " + id + "."), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Carrera no posee entidades con id: " + id + ".")).build();
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
            return new ResponseEntity<>(carrera, Helper.cabeceraHTTP("Se persistió correctamente la entidad Carrera."), HttpStatus.CREATED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carrera> update(@Valid @RequestBody CarreraForm CarreraForm, @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<Carrera> objeto = service.findById(id);
        if (objeto.isEmpty()) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("No existe una entidad Carrera modificable con id: " + id + ".")).build();
        }
        Carrera entidad = service.update(CarreraForm.formCarreraDTO(new Carrera()));
        return new ResponseEntity<>(entidad, Helper.cabeceraHTTP("Se modificó y persistió correctamente la entidad Carrera con id: " + id + "."), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<Carrera> entidad = service.findById(id);
        if (entidad.isEmpty()) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("No existe una entidad eliminable con id: " + id + ".")).build();
        }
        if (service.delete(id)) {
            String mensaje = "Se eliminó correctamente la entidad Carrera con id: " + id + ".";
            return new ResponseEntity<>(mensaje, Helper.cabeceraHTTP(mensaje), HttpStatus.OK);
        } else {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Hubo un error al eliminar la entidad con id: " + id + ".")).build();
        }
    }
}
