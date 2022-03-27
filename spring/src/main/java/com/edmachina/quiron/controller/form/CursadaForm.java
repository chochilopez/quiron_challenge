package com.edmachina.quiron.controller.form;

import com.edmachina.quiron.model.Cursada;
import com.edmachina.quiron.model.Estudiante;
import com.edmachina.quiron.model.Materia;
import com.edmachina.quiron.model.enumerator.EnumEstadoCursada;
import lombok.Getter;

import java.util.Date;

@Getter
public class CursadaForm {
    private Estudiante estudiante;
    private Materia materia;
    private EnumEstadoCursada estado;
    private Integer cursada;
    private Date inicioCursado;
    private Date finCursado;

    public Cursada formCursadaDTO(Cursada dto){
        if (this.estudiante != null)
            dto.setEstudiante(this.getEstudiante());
        if (this.materia != null)
            dto.setMateria(this.getMateria());
        if (this.estado != null)
            dto.setEstado(this.getEstado());
        if (this.cursada != null)
            dto.setCursada(this.getCursada());
        if (this.inicioCursado != null)
            dto.setInicioCursado(this.getInicioCursado());
        if (this.finCursado != null)
            dto.setFinCursado(this.getFinCursado());

        return dto;
    }
}
