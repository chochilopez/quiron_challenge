package com.edmachina.quiron.model;

import com.edmachina.quiron.model.enumerator.EnumTituloGrado;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Entity
@Schema(description = "El modelo Carrera posee un título propio, el nivel de la carrera y un conjunto de materias.")
@Setter
public class Carrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Título que otorga la carrera tras su finalización.", type = "String")
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Tipo de grado academico del titulo.", type = "EnumTituloGrado")
    private EnumTituloGrado grado;

    @OneToMany
    @Schema(description = "Conjunto de elementos compuesto por todas las materias que conforman el plan de estudio de una Carrera.", type = "Set<Materia>")
    private Set<Materia> planEstudio;

    public Carrera() {
    }

    public Carrera(String titulo, EnumTituloGrado grado, Set<Materia> planEstudio) {
        this.titulo = titulo;
        this.grado = grado;
        this.planEstudio = planEstudio;
    }
}