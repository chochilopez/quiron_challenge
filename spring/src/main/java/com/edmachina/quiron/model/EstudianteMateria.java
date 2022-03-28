package com.edmachina.quiron.model;

import com.edmachina.quiron.model.enumerator.EnumEstadoMateria;
import lombok.*;
import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@Getter
@Entity
@NoArgsConstructor
@Setter
public class EstudianteMateria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Estudiante.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Materia.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "materia_id")
    private Materia materia;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Carrera.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "carrera_id")
    private Carrera carrera;

    @Enumerated(EnumType.STRING)
    private EnumEstadoMateria estado;

    private Integer cursada;

    @Temporal(TemporalType.TIMESTAMP)
    private Date inicioCursado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date finCursado;

    public EstudianteMateria(Estudiante estudiante, Materia materia, Carrera carrera, EnumEstadoMateria estado, Integer cursada, Date inicioCursado, Date finCursado) {
        this.estudiante = estudiante;
        this.materia = materia;
        this.carrera = carrera;
        this.estado = estado;
        this.cursada = cursada;
        this.inicioCursado = inicioCursado;
        this.finCursado = finCursado;
    }
}

