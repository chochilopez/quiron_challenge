package com.edmachina.quiron.controller.form;

import com.edmachina.quiron.model.Carrera;
import com.edmachina.quiron.model.Materia;
import com.edmachina.quiron.model.enumerator.EnumTituloGrado;
import lombok.Getter;
import java.util.Set;

@Getter
public class CarreraForm {
    private String titulo;
    private EnumTituloGrado grado;
    private Set<Materia> planEstudio;

    public Carrera formCarreraDTO(Carrera dto){
        if (this.titulo != null)
            dto.setTitulo(this.getTitulo());
        if (this.grado != null)
            dto.setGrado(this.getGrado());
        if (this.planEstudio != null)
            dto.setPlanEstudio(this.getPlanEstudio());

        return dto;
    }
}
