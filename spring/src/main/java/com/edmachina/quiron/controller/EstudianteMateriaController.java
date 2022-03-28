package com.edmachina.quiron.controller;

import com.edmachina.quiron.controller.form.EstudianteMateriaForm;
import com.edmachina.quiron.helper.Helper;
import com.edmachina.quiron.model.EstudianteMateria;
import com.edmachina.quiron.service.implementation.EstudianteMateriaServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/estudiante-materia")
@RequiredArgsConstructor
@RestController
public class EstudianteMateriaController {

    private final EstudianteMateriaServiceImplementation service;

    @GetMapping(value = "/cantidad")
    public ResponseEntity<Long> cantidad() {
        Long cantidad= service.cantidad();
        if (cantidad == 0)
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Hay 0 entidades en EstudianteMateria.")).build();
        return new ResponseEntity<>(cantidad, Helper.cabeceraHTTP("Existen " + cantidad + " en EstudianteMateria."), HttpStatus.OK);
    }

    @GetMapping(value = "/buscar-por-id-materia/{idMateria}")
    public ResponseEntity<List<EstudianteMateria>> findAllByMateriaId(@PathVariable(name = "idMateria") @javax.validation.constraints.Size(min = 1, max = 10) Long idMateria) {
        List<EstudianteMateria> estudianteMaterias = service.findAllByMateriaId(idMateria);
        if (!estudianteMaterias.isEmpty())
            return new ResponseEntity<>(estudianteMaterias, Helper.cabeceraHTTP(
                    "Se encontraron " + estudianteMaterias.size() + " entidades EstudianteMateria con idMateria: " + idMateria + "."), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("EstudianteMateria no posee entidades con idMateria: " + idMateria + ".")).build();
    }

    @GetMapping(value = "/buscar-por-id-carrera/{idCarrera}")
    public ResponseEntity<List<EstudianteMateria>> findAllByCarreraId(@PathVariable(name = "idCarrera") @javax.validation.constraints.Size(min = 1, max = 10) Long idCarrera) {
        List<EstudianteMateria> estudianteMaterias = service.findAllByCarreraId(idCarrera);
        if (!estudianteMaterias.isEmpty())
            return new ResponseEntity<>(estudianteMaterias, Helper.cabeceraHTTP(
                    "Se encontraron " + estudianteMaterias.size() + " entidades EstudianteMateria con idCarrera: " + idCarrera + "."), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("EstudianteMateria no posee entidades con idCarrera: " + idCarrera + ".")).build();
    }

    @GetMapping(value = "/buscar-por-id-estudiante/{idEstudiante}")
    public ResponseEntity<List<EstudianteMateria>> findAllByEstudianteId(@PathVariable(name = "idEstudiante") @javax.validation.constraints.Size(min = 1, max = 10) Long idEstudiante) {
        List<EstudianteMateria> estudianteMaterias = service.findAllByEstudianteId(idEstudiante);
        if (!estudianteMaterias.isEmpty())
            return new ResponseEntity<>(estudianteMaterias, Helper.cabeceraHTTP(
                    "Se encontraron " + estudianteMaterias.size() + " entidades EstudianteMateria con idEstudiante: " + idEstudiante + "."), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("EstudianteMateria no posee entidades con idEstudiante: " + idEstudiante + ".")).build();
    }

    @GetMapping(value = "/buscar-por-id-estudiante-e-id-carrera/{idEstudiante}/{idCarrera}")
    public ResponseEntity<List<EstudianteMateria>> findByEstudianteIdAndCarreraId(
            @PathVariable(name = "idEstudiante") @javax.validation.constraints.Size(min = 1, max = 10) Long idEstudiante,
            @PathVariable(name = "idCarrera") @javax.validation.constraints.Size(min = 1, max = 10) Long idCarrera
    ) {
        List<EstudianteMateria> estudianteMaterias = service.findAllByEstudianteIdAndCarreraId(idEstudiante, idCarrera);
        if (!estudianteMaterias.isEmpty())
            return new ResponseEntity<>(estudianteMaterias, Helper.cabeceraHTTP(
                    "Se encontraron " + estudianteMaterias.size() + " entidades EstudianteMateria con idEstudiante: " + idEstudiante + " e idCarrera: " + idCarrera + "."
            ), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP(
                    "EstudianteMateria no posee entidades con idEstudiante: " + idEstudiante + " e idCarrera: " + idCarrera + "."
            )).build();
    }

    @GetMapping(value = "/buscar-por-id-estudiante-e-id-materia/{idEstudiante}/{idMateria}")
    public ResponseEntity<EstudianteMateria> findByEstudianteIdAndMateriaId(
            @PathVariable(name = "idEstudiante") @javax.validation.constraints.Size(min = 1, max = 10) Long idEstudiante,
            @PathVariable(name = "idMateria") @javax.validation.constraints.Size(min = 1, max = 10) Long idMateria
    ) {
        Optional<EstudianteMateria> estudianteMateria = service.findByEstudianteIdAndMateriaId(idEstudiante, idMateria);
        if (estudianteMateria.isPresent())
            return new ResponseEntity<>(estudianteMateria.get(), Helper.cabeceraHTTP(
                    "Se encontró la entidad EstudianteMateria con idEstudiante: " + idEstudiante + " e idCarrera: " + idMateria + "."
            ), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP(
                    "EstudianteMateria no posee entidades con idEstudiante: " + idEstudiante + " e idCarrera: " + idMateria + "."
            )).build();
    }

    @GetMapping(value = "/todas")
    public ResponseEntity<List<EstudianteMateria>> getAll() {
        List<EstudianteMateria> estudianteMaterias = service.findAll();
        if (estudianteMaterias.isEmpty())
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("EstudianteMateria no posee entidades.")).build();
        return new ResponseEntity<>(estudianteMaterias, Helper.cabeceraHTTP("Existen " + estudianteMaterias.size() + "entidades en EstudianteMateria."), HttpStatus.OK);
    }

    @GetMapping(value = "/buscar-por-id/{id}")
    public ResponseEntity<EstudianteMateria> findById(
            @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<EstudianteMateria> estudianteMateria = service.findById(id);
        if (estudianteMateria.isPresent())
            return new ResponseEntity<>(estudianteMateria.get(), Helper.cabeceraHTTP("Se encontró la entidad EstudianteMateria con id: " + id + "."), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("EstudianteMateria no posee entidades con id: " + id + ".")).build();
    }

    @PutMapping
    public ResponseEntity<EstudianteMateria> insert(@Valid @RequestBody EstudianteMateriaForm estudianteMateriaForm, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()){
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Fallo al validar el objeto: " + bindingResult)).build();
        }
        EstudianteMateria estudianteMateria = service.insert(estudianteMateriaForm.formEstudianteMateriaDTO(new EstudianteMateria()));
        if ( estudianteMateria == null) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Fallo al intentar persistir el objeto EstudianteMateria.")).build();
        } else {
            return new ResponseEntity<>(estudianteMateria, Helper.cabeceraHTTP("Se persistió correctamente la entidad EstudianteMateria con id: " + estudianteMateria.getId() + "."), HttpStatus.CREATED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstudianteMateria> update(@Valid @RequestBody EstudianteMateriaForm EstudianteMateriaForm, @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<EstudianteMateria> objeto = service.findById(id);
        if (objeto.isEmpty()) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("No existe una entidad EstudianteMateria modificable con id: " + id + ".")).build();
        }
        EstudianteMateria entidad = service.update(EstudianteMateriaForm.formEstudianteMateriaDTO(new EstudianteMateria()));
        return new ResponseEntity<>(entidad, Helper.cabeceraHTTP("Se modificó y persistió correctamente la entidad EstudianteMateria con id: " + id + "."), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<EstudianteMateria> entidad = service.findById(id);
        if (entidad.isEmpty()) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("No existe una entidad eliminable con id: " + id + ".")).build();
        }
        if (service.delete(id)) {
            String mensaje = "Se eliminó correctamente la entidad EstudianteMateria con id: " + id + ".";
            return new ResponseEntity<>(mensaje, Helper.cabeceraHTTP(mensaje), HttpStatus.OK);
        } else {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP(
                    "Hubo un error al eliminar la entidad con id: " + id + "."
            )).build();
        }
    }
}
