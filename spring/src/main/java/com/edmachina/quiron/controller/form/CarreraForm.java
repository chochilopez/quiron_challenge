package com.edmachina.quiron.controller.form;

import com.edmachina.quiron.model.Carrera;
import com.edmachina.quiron.model.Materia;
import com.edmachina.quiron.model.enumerator.EnumTituloGrado;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import java.util.Set;

@Getter
@Hidden
public class CarreraForm {
    private Long id;
    private String titulo;
    private EnumTituloGrado grado;
    private Set<Materia> planEstudio;

    public Carrera formCarreraDTO(Carrera dto){
        if (this.id != null)
            dto.setId(this.getId());
        if (this.titulo != null)
            dto.setTitulo(this.getTitulo());
        if (this.grado != null)
            dto.setGrado(this.getGrado());
        if (this.planEstudio != null)
            dto.setPlanEstudio(this.getPlanEstudio());

        return dto;
    }
}
