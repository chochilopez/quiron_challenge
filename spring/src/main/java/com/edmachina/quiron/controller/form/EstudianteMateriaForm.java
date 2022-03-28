package com.edmachina.quiron.controller.form;

import com.edmachina.quiron.model.Carrera;
import com.edmachina.quiron.model.Estudiante;
import com.edmachina.quiron.model.EstudianteMateria;
import com.edmachina.quiron.model.Materia;
import com.edmachina.quiron.model.enumerator.EnumEstadoMateria;
import lombok.Getter;
import java.util.Date;

@Getter
public class EstudianteMateriaForm {

    private Estudiante estudiante;
    private Materia materia;
    private Carrera carrera;
    private EnumEstadoMateria estado;
    private Integer cursada;
    private Date inicioCursado;
    private Date finCursado;

    public EstudianteMateria formEstudianteMateriaDTO(EstudianteMateria dto){
        if (this.estudiante != null)
            dto.setEstudiante(this.getEstudiante());
        if (this.materia != null)
            dto.setMateria(this.getMateria());
        if (this.carrera != null)
            dto.setCarrera(this.getCarrera());
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
