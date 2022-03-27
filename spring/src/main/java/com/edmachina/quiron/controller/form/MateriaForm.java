package com.edmachina.quiron.controller.form;

import com.edmachina.quiron.model.Materia;
import com.edmachina.quiron.model.enumerator.EnumEstadoMateria;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
public class MateriaForm {

    private String nombre;
    private EnumEstadoMateria estado;
    private Integer cursada;
    private Date inicioCursado;
    private Date finCursado;

    public Materia formMateriaDTO(Materia dto){
        if (this.nombre != null)
            dto.setNombre(this.getNombre());
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
