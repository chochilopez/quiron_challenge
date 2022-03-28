package com.edmachina.quiron.controller.form;

import com.edmachina.quiron.model.Materia;
import lombok.Getter;

@Getter
public class MateriaForm {

    private String nombre;

    public Materia formMateriaDTO(Materia dto){
        if (this.nombre != null)
            dto.setNombre(this.getNombre());

        return dto;
    }
}
