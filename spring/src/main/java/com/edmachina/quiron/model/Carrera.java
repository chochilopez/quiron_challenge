package com.edmachina.quiron.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@Setter
@RequiredArgsConstructor
public class Carrera {

    @EmbeddedId
    private CarreraKey id = new CarreraKey();

    @ManyToOne
    @MapsId("estudianteId")
    private Estudiante estudiante;

    @ManyToOne
    @MapsId("tituloId")
    private Titulo titulo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date ingreso;

    public Carrera(Estudiante estudiante, Titulo titulo, Date hoy) {
    }
}

