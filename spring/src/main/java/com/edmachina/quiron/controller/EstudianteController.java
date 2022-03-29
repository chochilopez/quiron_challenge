package com.edmachina.quiron.controller;

import com.edmachina.quiron.controller.form.EstudianteForm;
import com.edmachina.quiron.helper.Helper;
import com.edmachina.quiron.model.Estudiante;
import com.edmachina.quiron.service.implementation.EstudianteServiceImplementation;
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
@RequestMapping(value = "/api/estudiante")
@RequiredArgsConstructor
@RestController
public class EstudianteController {

    private final EstudianteServiceImplementation service;

    @Operation(
            summary = "findAllByStatus - Busca todas las entidades por EstadoEstudiante.",
            description = "Devuelve un listado con todas las entidades estudiantes que correspondan al estado ingresado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, numero de entidades encontradas. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, 0 ocurrencias. Mas info en campo estado en el header.")
    })
    @GetMapping(value = "/buscar-por-status/{status}")
    public ResponseEntity<List<Estudiante>> findAllByStatus(
            @Parameter(description = "Cadena con el estado de los estudiantes sobre los cuales se desea obtener el listado. ESTADO_LEAD ESTADO_ESTUDIANTE", required = true, example = "ESTADO_LEAD")
            @PathVariable(name = "status") @javax.validation.constraints.Size(min = 8, max = 20) String status) {
        List<Estudiante> estudiantes = service.findAllByStatus(status);
        if (estudiantes.isEmpty())
                return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Estudiante no posee entidades con estado " + status + ".")).build();
        return new ResponseEntity<>(estudiantes, Helper.cabeceraHTTP("Existen " + estudiantes.size() + " entidades en Estudiante con estado " + status + "."), HttpStatus.OK);
    }

    @Operation(
            summary = "count - Cantidad de entidades existentes.",
            description = "Devuelve un entero con la cantidad de entidades de tipo Estudiante existentes."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, numero de entidades encontradas. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, 0 ocurrencias. Mas info en campo estado en el header.")
    })
    @GetMapping(value = "/cantidad")
    public ResponseEntity<Long> cantidad() {
        Long cantidad= service.cantidad();
        if (cantidad == 0)
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Hay 0 entidades en Estudiante.")).build();
        return new ResponseEntity<>(cantidad, Helper.cabeceraHTTP("Existen " + cantidad + " en Estudiante."), HttpStatus.OK);
    }

    @Operation(
            summary = "countByStatus - Cantidad de entidades existentes por estado.",
            description = "Devuelve un entero con la cantidad de entidades de tipo Estudiante existentes con el estado solicitado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, numero de entidades encontradas. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, 0 ocurrencias. Mas info en campo estado en el header.")
    })
    @GetMapping(value = "/cantidad/{status}")
    public ResponseEntity<Long> cantidad(
            @Parameter(description = "Cadena con el estado de los estudiantes sobre los cuales se desea saber la cantidad. ESTADO_LEAD ESTADO_ESTUDIANTE", required = true, example = "ESTADO_LEAD")
            @PathVariable(name = "status") @javax.validation.constraints.Size(min = 8, max = 20) String status) {
        Long cantidad= service.countByStatus(status);
        if (cantidad == 0)
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Hay 0 entidades con status " + status + ".")).build();
        return new ResponseEntity<>(cantidad, Helper.cabeceraHTTP("Existen " + cantidad + " entidades con status " + status + "."), HttpStatus.OK);
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
    public ResponseEntity<List<Estudiante>> getAll() {
        List<Estudiante> estudiantes = service.findAll();
        if (estudiantes.isEmpty())
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Estudiante no posee entidades.")).build();
        return new ResponseEntity<>(estudiantes, Helper.cabeceraHTTP("Existen " + estudiantes.size() + " entidades en Estudiante."), HttpStatus.OK);
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
    public ResponseEntity<Estudiante> findById(
            @Parameter(description = "Valor númerico de tipo Long corrspondiente al id de la entidad.", required = true, example = "4")
            @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<Estudiante> estudiante = service.findById(id);
        if (estudiante.isPresent())
            return new ResponseEntity<>(estudiante.get(), Helper.cabeceraHTTP("Se encontró la entidad Estudiante con id: " + id + "."), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Estudiante no posee entidades con id: " + id + ".")).build();
    }

    @Operation(
            summary = "insert - Creacion de una entidad de tipo Estudiante.",
            description = "Persiste una nueva entidad Estudiante."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED Endpoint consumido correctamente, 1 nueva entidad persistida. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, sin embargo la entidad no pudo ser persistida. Mas info en campo estado en el header.")
    })
    @PutMapping
    public ResponseEntity<Estudiante> insert(
            @Parameter(description = "Objeto de tipo Estudiante, sin id.", required = true)
            @Valid @RequestBody EstudianteForm estudianteForm, BindingResult bindingResult) throws Exception {
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

    @Operation(
            summary = "update - Actualizacion de una entidad de tipo Estudiante.",
            description = "Persiste un nuevo estado de una entidad Estudiante."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED Endpoint consumido correctamente, 1 nueva entidad persistida. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, sin embargo la entidad no pudo ser persistida. Mas info en campo estado en el header.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> update(
            @Parameter(description = "Objeto de tipo Estudiante, junto con su id.", required = true)
            @Valid @RequestBody EstudianteForm EstudianteForm,
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad.", required = true, example = "4")
            @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<Estudiante> objeto = service.findById(id);
        if (objeto.isEmpty()) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("No existe una entidad Estudiante modificable con id: " + id + ".")).build();
        }
        Estudiante entidad = service.update(EstudianteForm.formEstudianteDTO(new Estudiante()));
        return new ResponseEntity<>(entidad, Helper.cabeceraHTTP("Se modificó y persistió correctamente la entidad Estudiante con id: " + id + "."), HttpStatus.CREATED);
    }

    @Operation(
            summary = "delete - Destruccion de una entidad de tipo Estudiante.",
            description = "Destruye una entidad Estudiante."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, 1 entidad destruida. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, sin embargo la entidad no pudo ser destruida. Mas info en campo estado en el header.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad.", required = true, example = "4")
            @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
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
