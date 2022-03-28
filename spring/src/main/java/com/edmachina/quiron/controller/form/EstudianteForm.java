package com.edmachina.quiron.controller.form;

import com.edmachina.quiron.model.Estudiante;
import com.edmachina.quiron.model.EstudianteCarrera;
import com.edmachina.quiron.model.EstudianteMateria;
import lombok.Getter;
import java.util.Set;

@Getter
public class EstudianteForm {
    private String nombre;
    private String apellido;
    private String email;
    private String direccion;
    private String telefono;

    public Estudiante formEstudianteDTO(Estudiante dto){
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

        return dto;
    }
}
