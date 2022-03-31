package com.edmachina.quiron.controller;

import com.edmachina.quiron.controller.form.CarreraForm;
import com.edmachina.quiron.helper.Helper;
import com.edmachina.quiron.model.Carrera;
import com.edmachina.quiron.service.implementation.CarreraServiceImplementation;
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
@RequestMapping(value = "/api/carrera")
@RequiredArgsConstructor
@RestController
public class CarreraController {

    private final CarreraServiceImplementation service;

    @Operation(
            summary = "quitarMateria - Quitar una Materia al plan de estudio de una Carrera.",
            description = "Este endpoint recibe un idCarrera y un idMateria, devulve una entidad Carrera con la materia quitada del plan de estudios."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, 1 ocurrencia. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, 0 ocurrencias. Mas info en campo estado en el header.")
    })
    @PostMapping(value = "/quitar-materia/{idCarrera}/{idMateria}")
    public ResponseEntity<Carrera> quitarMateria(
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad Carrera.", required = true, example = "4")
            @PathVariable(name = "idCarrera") @javax.validation.constraints.Size(min = 1, max = 10) Long idCarrera,
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad Materia.", required = true, example = "4")
            @PathVariable(name = "idMateria") @javax.validation.constraints.Size(min = 1, max = 10) Long idMateria
    ) throws Exception {
        Carrera carrera = service.quitarMateria(idCarrera, idMateria);
        if (carrera != null)
            return new ResponseEntity<>(carrera, Helper.cabeceraHTTP("Se quitó la Materia id: " + idMateria + " de la Carrera id: " + idCarrera + "."), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("No se quitó la Materia id: " + idMateria + " de la Carrera id: " + idCarrera + ".")).build();
    }

    @Operation(
            summary = "agregarMateria - Agregar una Materia al plan de estudio de una Carrera.",
            description = "Este endpoint recibe un idCarrera y un idMateria, devulve una entidad Carrera con la materia agregada al plan de estudios."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, 1 ocurrencia. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, 0 ocurrencias. Mas info en campo estado en el header.")
    })
    @PostMapping(value = "/agregar-materia/{idCarrera}/{idMateria}")
    public ResponseEntity<Carrera> agregarMateria(
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad Carrera.", required = true, example = "4")
            @PathVariable(name = "idCarrera") @javax.validation.constraints.Size(min = 1, max = 10) Long idCarrera,
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad Materia.", required = true, example = "4")
            @PathVariable(name = "idMateria") @javax.validation.constraints.Size(min = 1, max = 10) Long idMateria
    ) throws Exception {
        Carrera carrera = service.agregarMateria(idCarrera, idMateria);
        if (carrera != null)
            return new ResponseEntity<>(carrera, Helper.cabeceraHTTP("Se agregó la Materia id: " + idMateria + " a la Carrera id: " + idCarrera + "."), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("No se agregó la Materia id: " + idMateria + " a la Carrera id: " + idCarrera + ".")).build();
    }

    @Operation(
            summary = "count - Cantidad de entidades existentes.",
            description = "Devuelve un entero con la cantidad de entidades de tipo Carrera existentes."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, numero de entidades encontradas. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, 0 ocurrencias. Mas info en campo estado en el header.")
    })
    @GetMapping(value = "/cantidad")
    public ResponseEntity<Long> cantidad() {
        Long cantidad= service.cantidad();
        if (cantidad == 0)
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Hay 0 entidades en Carrera.")).build();
        return new ResponseEntity<>(cantidad, Helper.cabeceraHTTP("Existen " + cantidad + " en Carrera."), HttpStatus.OK);
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
    public ResponseEntity<List<Carrera>> getAll() {
        List<Carrera> carreras = service.findAll();
        if (carreras.isEmpty())
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Carrera no posee entidades.")).build();
        return new ResponseEntity<>(carreras, Helper.cabeceraHTTP("Existen " + carreras.size() + "entidades en Carrera."), HttpStatus.OK);
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
    public ResponseEntity<Carrera> findById(
            @Parameter(description = "Valor númerico de tipo Long corrspondiente al id de la entidad.", required = true, example = "4")
            @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<Carrera> carrera = service.findById(id);
        if (carrera.isPresent())
            return new ResponseEntity<>(carrera.get(), Helper.cabeceraHTTP("Se encontró la entidad Carrera con id: " + id + "."), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Carrera no posee entidades con id: " + id + ".")).build();
    }

    @Operation(
            summary = "insert - Creacion de una entidad de tipo Carrera.",
            description = "Persiste una nueva entidad Carrera."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED Endpoint consumido correctamente, 1 nueva entidad persistida. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, sin embargo la entidad no pudo ser persistida. Mas info en campo estado en el header.")
    })
    @PutMapping
    public ResponseEntity<Carrera> insert(
            @Parameter(description = "Objeto de tipo Carrera, sin id.", required = true)
            @Valid @RequestBody CarreraForm carreraForm, BindingResult bindingResult) throws Exception {
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

    @Operation(
            summary = "update - Actualizacion de una entidad de tipo Carrera.",
            description = "Persiste un nuevo estado de una entidad Carrera."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED Endpoint consumido correctamente, 1 nueva entidad persistida. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, sin embargo la entidad no pudo ser persistida. Mas info en campo estado en el header.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Carrera> update(
            @Parameter(description = "Objeto de tipo Carrera, junto con su id.", required = true)
            @Valid @RequestBody CarreraForm CarreraForm,
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad.", required = true, example = "4")
            @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<Carrera> objeto = service.findById(id);
        if (objeto.isEmpty()) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("No existe una entidad Carrera modificable con id: " + id + ".")).build();
        }
        Carrera entidad = service.update(CarreraForm.formCarreraDTO(objeto.get()));
        return new ResponseEntity<>(entidad, Helper.cabeceraHTTP("Se modificó y persistió correctamente la entidad Carrera con id: " + id + "."), HttpStatus.CREATED);
    }

    @Operation(
            summary = "delete - Destruccion de una entidad de tipo Carrera.",
            description = "Destruye una entidad Carrera."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, 1 entidad destruida. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, sin embargo la entidad no pudo ser destruida. Mas info en campo estado en el header.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad.", required = true, example = "4")
            @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
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
