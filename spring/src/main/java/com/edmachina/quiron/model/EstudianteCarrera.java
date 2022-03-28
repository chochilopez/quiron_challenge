package com.edmachina.quiron.model;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@Setter
public class EstudianteCarrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Estudiante.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Carrera.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "carrera_id")
    private Carrera carrera;

    @Temporal(TemporalType.TIMESTAMP)
    private Date ingreso;

    @Temporal(TemporalType.TIMESTAMP)
    private Date egreso;

    public EstudianteCarrera() {
    }

    public EstudianteCarrera(Estudiante estudiante, Carrera carrera, Date ingreso, Date egreso) {
        this.estudiante = estudiante;
        this.carrera = carrera;
        this.ingreso = ingreso;
        this.egreso = egreso;
    }
}

