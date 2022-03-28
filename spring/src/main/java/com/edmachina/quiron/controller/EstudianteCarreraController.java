package com.edmachina.quiron.controller;

import com.edmachina.quiron.controller.form.EstudianteCarreraForm;
import com.edmachina.quiron.helper.Helper;
import com.edmachina.quiron.model.Carrera;
import com.edmachina.quiron.model.Estudiante;
import com.edmachina.quiron.model.EstudianteCarrera;
import com.edmachina.quiron.service.implementation.EstudianteCarreraServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/estudiante-carrera")
@RequiredArgsConstructor
@RestController
public class EstudianteCarreraController {

    private final EstudianteCarreraServiceImplementation service;

    @GetMapping(value = "/cantidad")
    public ResponseEntity<Long> cantidad() {
        Long cantidad= service.cantidad();
        if (cantidad == 0)
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Hay 0 entidades en EstudianteCarrera.")).build();
        return new ResponseEntity<>(cantidad, Helper.cabeceraHTTP("Existen " + cantidad + " en EstudianteCarrera."), HttpStatus.OK);
    }

    @GetMapping(value = "/inscribir-estudiante/{idEstudiante}/{idCarrera}")
    public ResponseEntity<EstudianteCarrera> inscribirEstudiante(
            @PathVariable(name = "idEstudiante") @javax.validation.constraints.Size(min = 1, max = 10) Long idEstudiante,
            @PathVariable(name = "idCarrera") @javax.validation.constraints.Size(min = 1, max = 10) Long idCarrera
    ) {
        EstudianteCarrera estudianteCarrera = service.inscribirEstudiante(idEstudiante, idCarrera);
        if (estudianteCarrera != null)
            return new ResponseEntity<>(estudianteCarrera, Helper.cabeceraHTTP(
                    "Se inscribió al estudiante: " + estudianteCarrera.getEstudiante().getApellido() + " en la carrera: " + estudianteCarrera.getCarrera().getTitulo() + " correctamente."
            ), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP(
                    "No se pudo inscribir al idEstudiante: " + idEstudiante + " e idCarrera: " + idCarrera + "."
            )).build();
    }

    @GetMapping(value = "/buscar-por-id-estudiante-e-id-carrera/{idEstudiante}/{idCarrera}")
    public ResponseEntity<EstudianteCarrera> findByEstudianteIdAndCarreraId(
            @PathVariable(name = "idEstudiante") @javax.validation.constraints.Size(min = 1, max = 10) Long idEstudiante,
            @PathVariable(name = "idCarrera") @javax.validation.constraints.Size(min = 1, max = 10) Long idCarrera
    ) {
        Optional<EstudianteCarrera> estudianteCarrera = service.findByEstudianteIdAndCarreraId(idEstudiante, idCarrera);
        if (estudianteCarrera.isPresent())
            return new ResponseEntity<>(estudianteCarrera.get(), Helper.cabeceraHTTP(
                    "Se encontró la entidad EstudianteCarrera con idEstudiante: " + idEstudiante + " e idCarrera: " + idCarrera + "."
            ), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP(
                    "EstudianteCarrera no posee entidades con idEstudiante: " + idEstudiante + " e idCarrera: " + idCarrera + "."
            )).build();
    }

    @GetMapping(value = "/buscar-por-id-estudiante/{idEstudiante}")
    public ResponseEntity<List<EstudianteCarrera>> findAllByEstudianteId(@PathVariable(name = "idEstudiante") @javax.validation.constraints.Size(min = 1, max = 10) Long idEstudiante) {
        List<EstudianteCarrera> estudianteCarreras = service.findAllByEstudianteId(idEstudiante);
        if (estudianteCarreras.isEmpty())
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("EstudianteCarrera no posee entidades con idEstudiante: " + idEstudiante + ".")).build();
        return new ResponseEntity<>(estudianteCarreras, Helper.cabeceraHTTP(
                "Existen " + estudianteCarreras.size() + "entidades en EstudianteCarrera con idEstudiante: " + idEstudiante + "."
        ), HttpStatus.OK);
    }

    @GetMapping(value = "/buscar-por-id-carrera/{idCarrera}")
    public ResponseEntity<List<EstudianteCarrera>> findAllByCarreraId(
            @PathVariable(name = "idCarrera") @javax.validation.constraints.Size(min = 1, max = 10) Long idCarrera
    ) {
        List<EstudianteCarrera> estudianteCarreras = service.findAllByCarreraId(idCarrera);
        if (estudianteCarreras.isEmpty())
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("EstudianteCarrera no posee entidades con idCarrera: " + idCarrera + ".")).build();
        return new ResponseEntity<>(estudianteCarreras, Helper.cabeceraHTTP(
                "Existen " + estudianteCarreras.size() + "entidades en EstudianteCarrera con idCarrera: " + idCarrera + "."
        ), HttpStatus.OK);
    }

    @GetMapping(value = "/todas")
    public ResponseEntity<List<EstudianteCarrera>> getAll() {
        List<EstudianteCarrera> estudianteCarreras = service.findAll();
        if (estudianteCarreras.isEmpty())
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("EstudianteCarrera no posee entidades.")).build();
        return new ResponseEntity<>(estudianteCarreras, Helper.cabeceraHTTP("Existen " + estudianteCarreras.size() + "entidades en EstudianteCarrera."), HttpStatus.OK);
    }

    @GetMapping(value = "/buscar-por-id/{id}")
    public ResponseEntity<EstudianteCarrera> findById(
            @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<EstudianteCarrera> estudianteCarrera = service.findById(id);
        if (estudianteCarrera.isPresent())
            return new ResponseEntity<>(estudianteCarrera.get(), Helper.cabeceraHTTP("Se encontró la entidad EstudianteCarrera con id: " + id + "."), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("EstudianteCarrera no posee entidades con id: " + id + ".")).build();
    }

    @PutMapping
    public ResponseEntity<EstudianteCarrera> insert(@Valid @RequestBody EstudianteCarreraForm estudianteCarreraForm, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()){
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Fallo al validar el objeto: " + bindingResult)).build();
        }
        EstudianteCarrera estudianteCarrera = service.insert(estudianteCarreraForm.formEstudianteCarreraDTO(new EstudianteCarrera()));
        if ( estudianteCarrera == null) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Fallo al intentar persistir el objeto EstudianteCarrera.")).build();
        } else {
            return new ResponseEntity<>(estudianteCarrera, Helper.cabeceraHTTP("Se persistió correctamente la entidad EstudianteCarrera con id: " + estudianteCarrera.getId() + "."), HttpStatus.CREATED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstudianteCarrera> update(@Valid @RequestBody EstudianteCarreraForm EstudianteCarreraForm, @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<EstudianteCarrera> objeto = service.findById(id);
        if (objeto.isEmpty()) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("No existe una entidad EstudianteCarrera modificable con id: " + id + ".")).build();
        }
        EstudianteCarrera entidad = service.update(EstudianteCarreraForm.formEstudianteCarreraDTO(new EstudianteCarrera()));
        return new ResponseEntity<>(entidad, Helper.cabeceraHTTP("Se modificó y persistió correctamente la entidad EstudianteCarrera con id: " + id + "."), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<EstudianteCarrera> entidad = service.findById(id);
        if (entidad.isEmpty()) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("No existe una entidad eliminable con id: " + id + ".")).build();
        }
        if (service.delete(id)) {
            String mensaje = "Se eliminó correctamente la entidad EstudianteCarrera con id: " + id + ".";
            return new ResponseEntity<>(mensaje, Helper.cabeceraHTTP(mensaje), HttpStatus.OK);
        } else {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP(
                    "Hubo un error al eliminar la entidad con id: " + id + "."
            )).build();
        }
    }
}
