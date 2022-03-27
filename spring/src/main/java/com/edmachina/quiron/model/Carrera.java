package com.edmachina.quiron.model;

import com.edmachina.quiron.model.enumerator.EnumTituloGrado;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Entity
@Setter
@RequiredArgsConstructor
public class Carrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Enumerated(EnumType.STRING)
    private EnumTituloGrado grado;

    @OneToMany
    Set<Materia> materias;

    @Temporal(TemporalType.TIMESTAMP)
    private Date ingreso;

    @Temporal(TemporalType.TIMESTAMP)
    private Date egreso;

    public Carrera(String titulo, EnumTituloGrado grado, Set<Materia> materias, Date ingreso, Date egreso) {
        this.titulo = titulo;
        this.grado = grado;
        this.materias = materias;
        this.ingreso = ingreso;
        this.egreso = egreso;
    }
}

