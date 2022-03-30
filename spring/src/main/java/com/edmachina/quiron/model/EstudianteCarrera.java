package com.edmachina.quiron.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@Schema(description = "Modelo de entidad de tipo pivot. Relacion (ManyToMany) entre estudiantes y carreras. Un estudiante puede inscribirse en multiples carreras y una carrera puede ser accecida por multiples estudiantes.")
@Setter
public class EstudianteCarrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Estudiante.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "estudiante_id")
    @Schema(description = "Entidad de tipo Estudiante, persona que se matricula en la Carrera. Relacion ManyToOne.", type = "Estudiante")
    private Estudiante estudiante;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Carrera.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "carrera_id")
    @Schema(description = "Entidad de tipo Carrera, donde se matricula el estudiante. Relacion ManyToOne.", type = "Carrera")
    private Carrera carrera;

    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "Fecha de ingreso del estudiante a la Carrera.", type = "Date")
    private Date ingreso;

    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "Fecha de finalizacion de la Carrera por parte del estudiante.", type = "Date")
    private Date egreso;

    public EstudianteCarrera() {
    }

    public EstudianteCarrera(Estudiante estudiante, Carrera carrera, Date ingreso, Date egreso) {
        this.estudiante = estudiante;
        this.carrera = carrera;
        this.ingreso = ingreso;
        this.egreso = egreso;
    }

    public EstudianteCarrera(Long id, Estudiante estudiante, Carrera carrera, Date ingreso, Date egreso) {
        this.id = id;
        this.estudiante = estudiante;
        this.carrera = carrera;
        this.ingreso = ingreso;
        this.egreso = egreso;
    }
}

