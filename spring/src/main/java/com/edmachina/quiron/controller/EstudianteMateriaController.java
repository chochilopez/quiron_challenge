package com.edmachina.quiron.controller;

import com.edmachina.quiron.controller.form.EstudianteMateriaForm;
import com.edmachina.quiron.helper.Helper;
import com.edmachina.quiron.model.EstudianteMateria;
import com.edmachina.quiron.service.implementation.EstudianteMateriaServiceImplementation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "count - Cantidad de entidades existentes.",
            description = "Devuelve un entero con la cantidad de entidades de tipo EstudianteMateria existentes."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, numero de entidades encontradas. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, 0 ocurrencias. Mas info en campo estado en el header.")
    })
    @GetMapping(value = "/cantidad")
    public ResponseEntity<Long> cantidad() {
        Long cantidad= service.cantidad();
        if (cantidad == 0)
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Hay 0 entidades en EstudianteMateria.")).build();
        return new ResponseEntity<>(cantidad, Helper.cabeceraHTTP("Existen " + cantidad + " en EstudianteMateria."), HttpStatus.OK);
    }

    @Operation(
            summary = "findAllByMateriaId - Busqueda por idMateria.",
            description = "Este endpoint recibe un idMateria, devulve todas las entidades de tipo EstudianteMateria con ese idMateria si existiesen."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, al menor 1 ocurrencia. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, 0 ocurrencias. Mas info en campo estado en el header.")
    })
    @GetMapping(value = "/buscar-por-id-materia/{idMateria}")
    public ResponseEntity<List<EstudianteMateria>> findAllByMateriaId(
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad Materia.", required = true, example = "4")
            @PathVariable(name = "idMateria") @javax.validation.constraints.Size(min = 1, max = 10) Long idMateria) {
        List<EstudianteMateria> estudianteMaterias = service.findAllByMateriaId(idMateria);
        if (!estudianteMaterias.isEmpty())
            return new ResponseEntity<>(estudianteMaterias, Helper.cabeceraHTTP(
                    "Se encontraron " + estudianteMaterias.size() + " entidades EstudianteMateria con idMateria: " + idMateria + "."), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("EstudianteMateria no posee entidades con idMateria: " + idMateria + ".")).build();
    }

    @Operation(
            summary = "findAllByCarreraId - Busqueda por idCarrera.",
            description = "Este endpoint recibe un idCarrera, devulve todas las entidades de tipo EstudianteMateria con ese idCarrera si existiesen."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, al menor 1 ocurrencia. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, 0 ocurrencias. Mas info en campo estado en el header.")
    })
    @GetMapping(value = "/buscar-por-id-carrera/{idCarrera}")
    public ResponseEntity<List<EstudianteMateria>> findAllByCarreraId(
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad Carrera.", required = true, example = "4")
            @PathVariable(name = "idCarrera") @javax.validation.constraints.Size(min = 1, max = 10) Long idCarrera) {
        List<EstudianteMateria> estudianteMaterias = service.findAllByCarreraId(idCarrera);
        if (!estudianteMaterias.isEmpty())
            return new ResponseEntity<>(estudianteMaterias, Helper.cabeceraHTTP(
                    "Se encontraron " + estudianteMaterias.size() + " entidades EstudianteMateria con idCarrera: " + idCarrera + "."), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("EstudianteMateria no posee entidades con idCarrera: " + idCarrera + ".")).build();
    }

    @Operation(
            summary = "findAllByEstudianteId - Busqueda por idEstudiante.",
            description = "Este endpoint recibe un idEstudiante, devulve todas las entidades de tipo EstudianteMateria con ese idEstudiante si existiesen."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, al menor 1 ocurrencia. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, 0 ocurrencias. Mas info en campo estado en el header.")
    })
    @GetMapping(value = "/buscar-por-id-estudiante/{idEstudiante}")
    public ResponseEntity<List<EstudianteMateria>> findAllByEstudianteId(
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad Estudiante.", required = true, example = "4")
            @PathVariable(name = "idEstudiante") @javax.validation.constraints.Size(min = 1, max = 10) Long idEstudiante) {
        List<EstudianteMateria> estudianteMaterias = service.findAllByEstudianteId(idEstudiante);
        if (!estudianteMaterias.isEmpty())
            return new ResponseEntity<>(estudianteMaterias, Helper.cabeceraHTTP(
                    "Se encontraron " + estudianteMaterias.size() + " entidades EstudianteMateria con idEstudiante: " + idEstudiante + "."), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("EstudianteMateria no posee entidades con idEstudiante: " + idEstudiante + ".")).build();
    }

    @Operation(
            summary = "findByEstudianteIdAndCarreraId - Busqueda por idEstudiante e idCarrera.",
            description = "Este endpoint recibe un idEstudiante y un idCarrera, devulve una entidad EstudianteMateria si existiere."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, 1 ocurrencia. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, 0 ocurrencias. Mas info en campo estado en el header.")
    })
    @GetMapping(value = "/buscar-por-id-estudiante-e-id-carrera/{idEstudiante}/{idCarrera}")
    public ResponseEntity<List<EstudianteMateria>> findByEstudianteIdAndCarreraId(
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad Estudiante.", required = true, example = "4")
            @PathVariable(name = "idEstudiante") @javax.validation.constraints.Size(min = 1, max = 10) Long idEstudiante,
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad Carrera.", required = true, example = "4")
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

    @Operation(
            summary = "findByEstudianteIdAndMateriaId - Busqueda por idEstudiante e idMateria.",
            description = "Este endpoint recibe un idEstudiante y un idMateria, devulve una entidad EstudianteMateria si existiere."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, 1 ocurrencia. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, 0 ocurrencias. Mas info en campo estado en el header.")
    })
    @GetMapping(value = "/buscar-por-id-estudiante-e-id-materia/{idEstudiante}/{idMateria}")
    public ResponseEntity<EstudianteMateria> findByEstudianteIdAndMateriaId(
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad Estudiante.", required = true, example = "4")
            @PathVariable(name = "idEstudiante") @javax.validation.constraints.Size(min = 1, max = 10) Long idEstudiante,
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad Materia.", required = true, example = "4")
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

    @Operation(
            summary = "findAll - Buscar todas las entidades.",
            description = "Devuelve todas las entidades con todos sus atributos en formato JSON."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, 1 o mas ocurrencias. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, 0 ocurrencias. Mas info en campo estado en el header.")
    })
    @GetMapping(value = "/todas")
    public ResponseEntity<List<EstudianteMateria>> getAll() {
        List<EstudianteMateria> estudianteMaterias = service.findAll();
        if (estudianteMaterias.isEmpty())
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("EstudianteMateria no posee entidades.")).build();
        return new ResponseEntity<>(estudianteMaterias, Helper.cabeceraHTTP("Existen " + estudianteMaterias.size() + "entidades en EstudianteMateria."), HttpStatus.OK);
    }

    @Operation(
            summary = "findById - Buscar entidad por id.",
            description = "Busca una entidad por su identificador único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, 1 ocurrencia. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, 0 ocurrencias. Mas info en campo estado en el header.")
    })
    @GetMapping(value = "/buscar-por-id/{id}")
    public ResponseEntity<EstudianteMateria> findById(
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad.", required = true, example = "4")
            @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<EstudianteMateria> estudianteMateria = service.findById(id);
        if (estudianteMateria.isPresent())
            return new ResponseEntity<>(estudianteMateria.get(), Helper.cabeceraHTTP("Se encontró la entidad EstudianteMateria con id: " + id + "."), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("EstudianteMateria no posee entidades con id: " + id + ".")).build();
    }

    @Operation(
            summary = "insert - Creacion de una entidad de tipo EstudianteMateria.",
            description = "Persiste una nueva entidad EstudianteMateria."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED Endpoint consumido correctamente, 1 nueva entidad persistida. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, sin embargo la entidad no pudo ser persistida. Mas info en campo estado en el header.")
    })
    @PutMapping
    public ResponseEntity<EstudianteMateria> insert(
            @Parameter(description = "Objeto de tipo EstudianteMateria, sin id.", required = true)
            @Valid @RequestBody EstudianteMateriaForm estudianteMateriaForm, BindingResult bindingResult) throws Exception {
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

    @Operation(
            summary = "update - Actualizacion de una entidad de tipo EstudianteMateria.",
            description = "Persiste un nuevo estado de una entidad EstudianteMateria."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED Endpoint consumido correctamente, 1 nueva entidad persistida. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, sin embargo la entidad no pudo ser persistida. Mas info en campo estado en el header.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EstudianteMateria> update(
            @Parameter(description = "Objeto de tipo EstudianteMateria, junto con su id.", required = true)
            @Valid @RequestBody EstudianteMateriaForm EstudianteMateriaForm,
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad.", required = true, example = "4")
            @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<EstudianteMateria> objeto = service.findById(id);
        if (objeto.isEmpty()) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("No existe una entidad EstudianteMateria modificable con id: " + id + ".")).build();
        }
        EstudianteMateria entidad = service.update(EstudianteMateriaForm.formEstudianteMateriaDTO(objeto.get()));
        return new ResponseEntity<>(entidad, Helper.cabeceraHTTP("Se modificó y persistió correctamente la entidad EstudianteMateria con id: " + id + "."), HttpStatus.CREATED);
    }

    @Operation(
            summary = "delete - Destruccion de una entidad de tipo EstudianteMateria.",
            description = "Destruye una entidad EstudianteMateria."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, 1 entidad destruida. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, sin embargo la entidad no pudo ser destruida. Mas info en campo estado en el header.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad.", required = true, example = "4")
            @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
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
