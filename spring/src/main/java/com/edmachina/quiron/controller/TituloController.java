package com.edmachina.quiron.controller;

import com.edmachina.quiron.controller.form.TituloForm;
import com.edmachina.quiron.helper.Helper;
import com.edmachina.quiron.model.Titulo;
import com.edmachina.quiron.service.implementation.TituloServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/titulo")
@RequiredArgsConstructor
@RestController
public class TituloController {

    private final TituloServiceImplementation service;

    @GetMapping(value = "/todas")
    public ResponseEntity<List<Titulo>> getAll() {
        List<Titulo> titulos = service.findAll();
        if (titulos.isEmpty())
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Titulo no posee entidades.")).build();
        return new ResponseEntity<>(titulos, Helper.cabeceraHTTP("Existen " + titulos.size() + "entidades en Titulo."), HttpStatus.OK);
    }

    @GetMapping(value = "/buscar-por-id/{id}")
    public ResponseEntity<Titulo> findById(
            @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<Titulo> titulo = service.findById(id);
        if (titulo.isPresent())
            return new ResponseEntity<>(titulo.get(), Helper.cabeceraHTTP("Se encontró la entidad Titulo con id: " + id + "."), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Titulo no posee entidades con id: " + id + ".")).build();
    }

    @PutMapping
    public ResponseEntity<Titulo> insert(@Valid @RequestBody TituloForm tituloForm, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()){
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Fallo al validar el objeto: " + bindingResult)).build();
        }
        Titulo titulo = service.insert(tituloForm.formTituloDTO(new Titulo()));
        if ( titulo == null) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Fallo al intentar persistir el objeto Titulo.")).build();
        } else {
            return new ResponseEntity<>(titulo, Helper.cabeceraHTTP("Se persistió correctamente la entidad Titulo con id: " + titulo.getId() + "."), HttpStatus.CREATED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Titulo> update(@Valid @RequestBody TituloForm TituloForm, @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<Titulo> objeto = service.findById(id);
        if (objeto.isEmpty()) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("No existe una entidad Titulo modificable con id: " + id + ".")).build();
        }
        Titulo entidad = service.update(TituloForm.formTituloDTO(new Titulo()));
        return new ResponseEntity<>(entidad, Helper.cabeceraHTTP("Se modificó y persistió correctamente la entidad Titulo con id: " + id + "."), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<Titulo> entidad = service.findById(id);
        if (entidad.isEmpty()) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("No existe una entidad eliminable con id: " + id + ".")).build();
        }
        if (service.delete(id)) {
            String mensaje = "Se eliminó correctamente la entidad Titulo con id: " + id + ".";
            return new ResponseEntity<>(mensaje, Helper.cabeceraHTTP(mensaje), HttpStatus.OK);
        } else {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP(
                    "Hubo un error al eliminar la entidad con id: " + id + "."
            )).build();
        }
    }
}
