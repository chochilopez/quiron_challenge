package com.edmachina.quiron.controller.form;

import com.edmachina.quiron.model.Materia;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;

@Getter
@Hidden
public class MateriaForm {
    private Long id;
    private String nombre;

    public Materia formMateriaDTO(Materia dto){
        if (this.id != null)
            dto.setId(this.getId());
        if (this.nombre != null)
            dto.setNombre(this.getNombre());

        return dto;
    }
}
