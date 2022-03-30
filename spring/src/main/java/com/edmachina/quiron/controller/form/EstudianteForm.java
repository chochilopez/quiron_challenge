package com.edmachina.quiron.controller.form;

import com.edmachina.quiron.model.Estudiante;
import com.edmachina.quiron.model.EstudianteCarrera;
import com.edmachina.quiron.model.EstudianteMateria;
import com.edmachina.quiron.model.enumerator.EnumEstadoEstudiante;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Set;

@Getter
@Hidden
public class EstudianteForm {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String direccion;
    private String telefono;
    private EnumEstadoEstudiante status;
    private Date ingresoLead;
    private Date ingresoEstudiante;

    public Estudiante formEstudianteDTO(Estudiante dto){
        if (this.id != null)
            dto.setId(this.getId());
        if (this.nombre != null)
            dto.setNombre(this.getNombre());
        if (this.apellido != null)
            dto.setApellido(this.getApellido());
        if (this.email != null)
            dto.setEmail(this.getEmail());
        if (this.direccion != null)
            dto.setDireccion(this.getDireccion());
        if (this.telefono != null)
            dto.setTelefono(this.getTelefono());
        if (this.status != null)
            dto.setStatus(this.getStatus());
        if (this.ingresoLead != null)
            dto.setIngresoLead(this.getIngresoLead());
        if (this.ingresoEstudiante != null)
            dto.setIngresoEstudiante(this.getIngresoEstudiante());

        return dto;
    }
}
