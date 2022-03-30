package com.edmachina.quiron.model;

import com.edmachina.quiron.model.enumerator.EnumEstadoMateria;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@Schema(description = "Modelo de entidad de tipo pivot. Relacion (ManyToMany) entre estudiantes y materias. Un estudiante puede inscribirse en multiples materias y una materia puede ser accecida por multiples estudiantes.")
@Setter
public class EstudianteMateria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Estudiante.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "estudiante_id")
    @Schema(description = "Entidad de tipo Estudiante, persona que cursa o debe cursar la materia. Relacion ManyToOne.", type = "Estudiante")
    private Estudiante estudiante;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Materia.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "materia_id")
    @Schema(description = "Asignatura correspondiente al plan de estudios de la carrera. Relacion ManyToOne.", type = "Materia")
    private Materia materia;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Carrera.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "carrera_id")
    @Schema(description = "Carrera a la cual corresponde la materia. Una materia puede tener igual nombre en diferentes carreras. Relacion ManyToOne.", type = "Carrera")
    private Carrera carrera;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Atributo que da cuenta si el estudiante curso o no la materia.", type = "EnumEstadoMateria")
    private EnumEstadoMateria estado;

    @Schema(description = "Cantidad de veces que el alumno curso una materia. Si es cero no esta inscripto a ella, si es 1 la cursa por primera vez.", type = "Integer")
    private Integer cursada;

    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "Inicio del ciclo de cursado de una materia. Periodo de tiempo reiniciable entre recursadas a la materia.", type="Date")
    private Date inicioCursado;

    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "Fin del ciclo de cursado de una materia.", type="Date")
    private Date finCursado;

    public EstudianteMateria() {
    }

    public EstudianteMateria(Estudiante estudiante, Materia materia, Carrera carrera, EnumEstadoMateria estado, Integer cursada, Date inicioCursado, Date finCursado) {
        this.estudiante = estudiante;
        this.materia = materia;
        this.carrera = carrera;
        this.estado = estado;
        this.cursada = cursada;
        this.inicioCursado = inicioCursado;
        this.finCursado = finCursado;
    }

    public EstudianteMateria(Long id, Estudiante estudiante, Materia materia, Carrera carrera, EnumEstadoMateria estado, Integer cursada, Date inicioCursado, Date finCursado) {
        this.id = id;
        this.estudiante = estudiante;
        this.materia = materia;
        this.carrera = carrera;
        this.estado = estado;
        this.cursada = cursada;
        this.inicioCursado = inicioCursado;
        this.finCursado = finCursado;
    }
}

