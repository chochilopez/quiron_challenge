package com.edmachina.quiron.controller.form;

import com.edmachina.quiron.model.Carrera;
import com.edmachina.quiron.model.Materia;
import com.edmachina.quiron.model.enumerator.EnumTituloGrado;
import lombok.Getter;
import java.util.Date;
import java.util.Set;

@Getter
public class CarreraForm {
    private String titulo;
    private Date ingreso;
    private Date egreso;
    private EnumTituloGrado grado;
    Set<Materia> materias;

    public Carrera formCarreraDTO(Carrera dto){
        if (this.egreso != null)
            dto.setEgreso(this.getEgreso());
        if (this.titulo != null)
            dto.setTitulo(this.getTitulo());
        if (this.ingreso != null)
            dto.setIngreso(this.getIngreso());
        if (this.grado != null)
            dto.setGrado(this.getGrado());
        if (this.materias != null)
            dto.setMaterias(this.getMaterias());

        return dto;
    }
}
