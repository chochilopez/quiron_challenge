package com.edmachina.quiron.controller.form;

import com.edmachina.quiron.model.Titulo;
import com.edmachina.quiron.model.enumerator.EnumGradoTitulo;
import lombok.Getter;

@Getter
public class TituloForm {

    private String nombre;
    private String resolucion;
    private EnumGradoTitulo grado;

    public Titulo formTituloDTO(Titulo dto){
        if (this.nombre != null)
            dto.setNombre(this.getNombre());
        if (this.resolucion != null)
            dto.setResolucion(this.getResolucion());
        if (this.grado != null)
            dto.setGrado(this.getGrado());

        return dto;
    }
}
