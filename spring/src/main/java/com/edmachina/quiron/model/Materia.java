package com.edmachina.quiron.model;

import com.edmachina.quiron.model.enumerator.EnumEstadoMateria;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@Setter
@RequiredArgsConstructor
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Enumerated(EnumType.STRING)
    private EnumEstadoMateria estado;

    private Integer cursada;

    @Temporal(TemporalType.TIMESTAMP)
    private Date inicioCursado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date finCursado;
}
