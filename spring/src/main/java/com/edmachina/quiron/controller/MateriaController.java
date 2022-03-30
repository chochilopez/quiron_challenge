package com.edmachina.quiron.controller;

import com.edmachina.quiron.controller.form.MateriaForm;
import com.edmachina.quiron.helper.Helper;
import com.edmachina.quiron.model.Materia;
import com.edmachina.quiron.service.implementation.MateriaServiceImplementation;
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
@RequestMapping(value = "/api/materia")
@RequiredArgsConstructor
@RestController
public class MateriaController {

    private final MateriaServiceImplementation service;

    @Operation(
            summary = "count - Cantidad de entidades existentes.",
            description = "Devuelve un entero con la cantidad de entidades de tipo Materia existentes."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, numero de entidades encontradas. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, 0 ocurrencias. Mas info en campo estado en el header.")
    })
    @GetMapping(value = "/cantidad")
    public ResponseEntity<Long> cantidad() {
        Long cantidad= service.cantidad();
        if (cantidad == 0)
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Hay 0 entidades en Materia.")).build();
        return new ResponseEntity<>(cantidad, Helper.cabeceraHTTP("Existen " + cantidad + " en Materia."), HttpStatus.OK);
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
    public ResponseEntity<List<Materia>> getAll() {
        List<Materia> materias = service.findAll();
        if (materias.isEmpty())
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Materia no posee entidades.")).build();
        return new ResponseEntity<>(materias, Helper.cabeceraHTTP("Existen " + materias.size() + "entidades en Materia."), HttpStatus.OK);
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
    public ResponseEntity<Materia> findById(
            @Parameter(description = "Valor númerico de tipo Long corrspondiente al id de la entidad.", required = true, example = "4")
            @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<Materia> materia = service.findById(id);
        if (materia.isPresent())
            return new ResponseEntity<>(materia.get(), Helper.cabeceraHTTP("Se encontró la entidad Materia con id: " + id + "."), HttpStatus.OK);
        else
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("Materia no posee entidades con id: " + id + ".")).build();
    }

    @Operation(
            summary = "insert - Creacion de una entidad de tipo Materia.",
            description = "Persiste una nueva entidad Materia."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED Endpoint consumido correctamente, 1 nueva entidad persistida. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, sin embargo la entidad no pudo ser persistida. Mas info en campo estado en el header.")
    })
    @PutMapping
    public ResponseEntity<Materia> insert(
            @Parameter(description = "Objeto de tipo Materia, sin id.", required = true)
            @Valid @RequestBody MateriaForm materiaForm, BindingResult bindingResult) throws Exception {
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

    @Operation(
            summary = "update - Actualizacion de una entidad de tipo Materia.",
            description = "Persiste un nuevo estado de una entidad Materia."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED Endpoint consumido correctamente, 1 nueva entidad persistida. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, sin embargo la entidad no pudo ser persistida. Mas info en campo estado en el header.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Materia> update(
            @Parameter(description = "Objeto de tipo Materia, junto con su id.", required = true)
            @Valid @RequestBody MateriaForm MateriaForm,
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad.", required = true, example = "4")
            @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
        Optional<Materia> objeto = service.findById(id);
        if (objeto.isEmpty()) {
            return ResponseEntity.accepted().headers(Helper.cabeceraHTTP("No existe una entidad Materia modificable con id: " + id + ".")).build();
        }
        Materia entidad = service.update(MateriaForm.formMateriaDTO(objeto.get()));
        return new ResponseEntity<>(entidad, Helper.cabeceraHTTP("Se modificó y persistió correctamente la entidad Materia con id: " + id + "."), HttpStatus.CREATED);
    }

    @Operation(
            summary = "delete - Destruccion de una entidad de tipo Materia.",
            description = "Destruye una entidad Materia."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK Endpoint consumido correctamente, 1 entidad destruida. Mas info en campo estado en el header."),
            @ApiResponse(responseCode = "202", description = "ACCEPTED Endpoint consumido correctamente, sin embargo la entidad no pudo ser destruida. Mas info en campo estado en el header.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @Parameter(description = "Valor númerico de tipo Long correspondiente al id de la entidad.", required = true, example = "4")
            @PathVariable(name = "id") @javax.validation.constraints.Size(min = 1, max = 10) Long id) {
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
