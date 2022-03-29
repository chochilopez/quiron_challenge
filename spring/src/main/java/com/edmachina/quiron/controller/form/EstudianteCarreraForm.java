package com.edmachina.quiron.controller.form;

import com.edmachina.quiron.model.Carrera;
import com.edmachina.quiron.model.Estudiante;
import com.edmachina.quiron.model.EstudianteCarrera;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import java.util.Date;

@Hidden
@Getter
public class EstudianteCarreraForm {

    private Estudiante estudiante;
    private Carrera carrera;
    private Date ingreso;
    private Date egreso;

    public EstudianteCarrera formEstudianteCarreraDTO(EstudianteCarrera dto){
        if (this.estudiante != null)
            dto.setEstudiante(this.getEstudiante());
        if (this.carrera != null)
            dto.setCarrera(this.getCarrera());
        if (this.ingreso != null)
            dto.setIngreso(this.getIngreso());
        if (this.egreso != null)
            dto.setEgreso(this.getEgreso());

        return dto;
    }
}
