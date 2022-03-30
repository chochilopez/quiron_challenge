package com.edmachina.quiron.controller;

import com.edmachina.quiron.controller.form.EstudianteCarreraForm;
import com.edmachina.quiron.helper.Helper;
import com.edmachina.quiron.model.Carrera;
import com.edmachina.quiron.model.Estudiante;
import com.edmachina.quiron.model.EstudianteCarrera;
import com.edmachina.quiron.service.implementation.EstudianteCarreraServiceImplementation;
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
@RequestMapping(value = "/api/estudiante-carrera")
@RequiredArgsConstructor
@RestController
public class EstudianteCarreraController {

    private final EstudianteCarreraServiceImplementation service;

    @Operation(
            summary = "count - Cantidad de entidades existentes.",
            description = "Devuelve un entero con la cantidad de entidades de tipo EstudianteCarrera existentes."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, numero de entidades encontradas. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, 0 ocurrencias. Mas info en campo estado en el header.")
    })
    @GetMapping(value = "/cantidad")
    public ResponseEntity<Long> cantidad() {
        Long cantidad= service.cantidad();
        if (cantidad == 0)
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Hay 0 entidades en EstudianteCarrera.")).build();
        return new ResponseEntity<>(cantidad, Helper.cabeceraHTTP("Existen " + cantidad + " en EstudianteCarrera."), HttpStatus.OK);
    }

    @Operation(
            summary = "inscribirEstudiante - Inscripcion de un estudiante/lead a una carrera.",
            description = "Este endpoint recibe un idEstudiante y un idCarrera, inscribe al alumno y agrega las materias del plan de estudio de la carrera al alumno."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, el estudiante fue inscripto a la carrera. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, sin embargo el estudiante no pudo ser inscripto a la carrera. Mas info en campo estado en el header.")
    })
    @PostMapping(value = "/inscribir-estudiante/{idEstudiante}/{idCarrera}")
    public ResponseEntity<EstudianteCarrera> inscribirEstudiante(
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad Estudiante.", required = true, example = "4")
            @PathVariable(name = "idEstudiante") @javax.validation.constraints.Size(min = 1, max = 10) Long idEstudiante,
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad Carrera.", required = true, example = "4")
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

    @Operation(
            summary = "findByEstudianteIdAndCarreraId - Busqueda por idEstudiante e idCarrera.",
            description = "Este endpoint recibe un idEstudiante y un idCarrera, devulve una entidad EstudianteCarrera si existiere."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, 1 ocurrencia. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, 0 ocurrencias. Mas info en campo estado en el header.")
    })
    @GetMapping(value = "/buscar-por-id-estudiante-e-id-carrera/{idEstudiante}/{idCarrera}")
    public ResponseEntity<EstudianteCarrera> findByEstudianteIdAndCarreraId(
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad Estudiante.", required = true, example = "4")
            @PathVariable(name = "idEstudiante") @javax.validation.constraints.Size(min = 1, max = 10) Long idEstudiante,
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad Carrera.", required = true, example = "4")
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

    @Operation(
            summary = "findAllByEstudianteId - Busqueda por idEstudiante.",
            description = "Este endpoint recibe un idEstudiante, devulve todas las entidades de tipo EstudianteCarrera con ese idEstudiante si existiesen."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, al menor 1 ocurrencia. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, 0 ocurrencias. Mas info en campo estado en el header.")
    })
    @GetMapping(value = "/buscar-por-id-estudiante/{idEstudiante}")
    public ResponseEntity<List<EstudianteCarrera>> findAllByEstudianteId(
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad Estudiante.", required = true, example = "4")
            @PathVariable(name = "idEstudiante") @javax.validation.constraints.Size(min = 1, max = 10) Long idEstudiante) {
        List<EstudianteCarrera> estudianteCarreras = service.findAllByEstudianteId(idEstudiante);
        if (estudianteCarreras.isEmpty())
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("EstudianteCarrera no posee entidades con idEstudiante: " + idEstudiante + ".")).build();
        return new ResponseEntity<>(estudianteCarreras, Helper.cabeceraHTTP(
                "Existen " + estudianteCarreras.size() + "entidades en EstudianteCarrera con idEstudiante: " + idEstudiante + "."
        ), HttpStatus.OK);
    }

    @Operation(
            summary = "findAllByCarreraId - Busqueda por idCarrera.",
            description = "Este endpoint recibe un idCarrera, devulve todas las entidades de tipo EstudianteCarrera con ese idCarrera si existiesen."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, al menor 1 ocurrencia. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, 0 ocurrencias. Mas info en campo estado en el header.")
    })
    @GetMapping(value = "/buscar-por-id-carrera/{idCarrera}")
    public ResponseEntity<List<EstudianteCarrera>> findAllByCarreraId(
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad Carrera.", required = true, example = "4")
            @PathVariable(name = "idCarrera") @javax.validation.constraints.Size(min = 1, max = 10) Long idCarrera
    ) {
        List<EstudianteCarrera> estudianteCarreras = service.findAllByCarreraId(idCarrera);
        if (estudianteCarreras.isEmpty())
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("EstudianteCarrera no posee entidades con idCarrera: " + idCarrera + ".")).build();
        return new ResponseEntity<>(estudianteCarreras, Helper.cabeceraHTTP(
                "Existen " + estudianteCarreras.size() + "entidades en EstudianteCarrera con idCarrera: " + idCarrera + "."
        ), HttpStatus.OK);
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
    public ResponseEntity<List<EstudianteCarrera>> getAll() {
        List<EstudianteCarrera> estudianteCarreras = service.findAll();
        if (estudianteCarreras.isEmpty())
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("EstudianteCarrera no posee entidades.")).build();
        return new ResponseEntity<>(estudianteCarreras, Helper.cabeceraHTTP("Existen " + estudianteCarreras.size() + "entidades en EstudianteCarrera."), HttpStatus.OK);
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
    public ResponseEntity<EstudianteCarrera> findById(
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad.", required = true, example = "4")
            @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<EstudianteCarrera> estudianteCarrera = service.findById(id);
        if (estudianteCarrera.isPresent())
            return new ResponseEntity<>(estudianteCarrera.get(), Helper.cabeceraHTTP("Se encontró la entidad EstudianteCarrera con id: " + id + "."), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("EstudianteCarrera no posee entidades con id: " + id + ".")).build();
    }

    @Operation(
            summary = "insert - Creacion de una entidad de tipo EstudianteCarrera.",
            description = "Persiste una nueva entidad EstudianteCarrera."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED Endpoint consumido correctamente, 1 nueva entidad persistida. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, sin embargo la entidad no pudo ser persistida. Mas info en campo estado en el header.")
    })
    @PutMapping
    public ResponseEntity<EstudianteCarrera> insert(
            @Parameter(description = "Objeto de tipo EstudianteCarrera, sin id.", required = true)
            @Valid @RequestBody EstudianteCarreraForm estudianteCarreraForm, BindingResult bindingResult) throws Exception {
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

    @Operation(
            summary = "update - Actualizacion de una entidad de tipo EstudianteCarrera.",
            description = "Persiste un nuevo estado de una entidad EstudianteCarrera."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED Endpoint consumido correctamente, 1 nueva entidad persistida. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, sin embargo la entidad no pudo ser persistida. Mas info en campo estado en el header.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EstudianteCarrera> update(
            @Parameter(description = "Objeto de tipo EstudianteCarrera, junto con su id.", required = true)
            @Valid @RequestBody EstudianteCarreraForm EstudianteCarreraForm,
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad.", required = true, example = "4")
            @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<EstudianteCarrera> objeto = service.findById(id);
        if (objeto.isEmpty()) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("No existe una entidad EstudianteCarrera modificable con id: " + id + ".")).build();
        }
        EstudianteCarrera entidad = service.update(EstudianteCarreraForm.formEstudianteCarreraDTO(objeto.get()));
        return new ResponseEntity<>(entidad, Helper.cabeceraHTTP("Se modificó y persistió correctamente la entidad EstudianteCarrera con id: " + id + "."), HttpStatus.CREATED);
    }

    @Operation(
            summary = "delete - Destruccion de una entidad de tipo EstudianteCarrera.",
            description = "Destruye una entidad EstudianteCarrera."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, 1 entidad destruida. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, sin embargo la entidad no pudo ser destruida. Mas info en campo estado en el header.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad.", required = true, example = "4")
            @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
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
