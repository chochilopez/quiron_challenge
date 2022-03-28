package com.edmachina.quiron.model;

import com.edmachina.quiron.model.enumerator.EnumTituloGrado;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Entity
@Setter
public class Carrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Enumerated(EnumType.STRING)
    private EnumTituloGrado grado;

    @OneToMany
    private Set<Materia> planEstudio;

    public Carrera() {
    }

    public Carrera(String titulo, EnumTituloGrado grado, Set<Materia> planEstudio) {
        this.titulo = titulo;
        this.grado = grado;
        this.planEstudio = planEstudio;
    }
}