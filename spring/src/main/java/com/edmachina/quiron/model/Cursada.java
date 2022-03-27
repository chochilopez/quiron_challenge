package com.edmachina.quiron.model;

import com.edmachina.quiron.model.enumerator.EnumEstadoCursada;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@Setter
@RequiredArgsConstructor
public class Cursada {

    @EmbeddedId
    private CursadaKey id = new CursadaKey();

    @ManyToOne
    @MapsId("estudianteId")
    private Estudiante estudiante;

    @ManyToOne
    @MapsId("materiaId")
    private Materia materia;

    @Enumerated(EnumType.STRING)
    private EnumEstadoCursada estado;

    private Integer cursada;

    @Temporal(TemporalType.TIMESTAMP)
    private Date inicioCursado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date finCursado;

    public Cursada(Estudiante estudiante, Materia materia, EnumEstadoCursada estado, Integer cursada, Date inicioCursado, Date finCursado) {
        this.estudiante = estudiante;
        this.materia = materia;
        this.estado = estado;
        this.cursada = cursada;
        this.inicioCursado = inicioCursado;
        this.finCursado = finCursado;
    }
}

