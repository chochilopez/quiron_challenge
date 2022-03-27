package com.edmachina.quiron.controller.form;

import com.edmachina.quiron.model.Carrera;
import com.edmachina.quiron.model.Estudiante;
import com.edmachina.quiron.model.Titulo;
import lombok.Getter;

import java.util.Date;

@Getter
public class CarreraForm {
    private Estudiante estudiante;
    private Titulo titulo;
    private Date ingreso;

    public Carrera formCarreraDTO(Carrera dto){
        if (this.estudiante != null)
            dto.setEstudiante(this.getEstudiante());
        if (this.titulo != null)
            dto.setTitulo(this.getTitulo());
        if (this.ingreso != null)
            dto.setIngreso(this.getIngreso());

        return dto;
    }
}
