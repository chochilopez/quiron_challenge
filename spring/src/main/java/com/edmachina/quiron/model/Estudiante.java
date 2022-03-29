package com.edmachina.quiron.model;

import com.edmachina.quiron.model.enumerator.EnumEstadoEstudiante;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@Schema(description = "Modelo de persona con interes en al menos una carrera, posee datos personales, un estado y una fecha de ingreso como lead y otra como estudiante.")
@Setter
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre propio del lead/estudiante.", type = "String")
    private String nombre;
    @Schema(description = "Apellido propio del lead/estudiante.", type = "String")
    private String apellido;
    @Schema(description = "Email propio del lead/estudiante.", type = "String")
    private String email;
    @Schema(description = "Direccion propio del lead/estudiante.", type = "String")
    private String direccion;
    @Schema(description = "Telefono propio del lead/estudiante.", type = "String")
    private String telefono;

    @Schema(description = "Si una persona es ingresada por cualquier canal al sistema, pero no se inscribe a ninguna carrera es Lead. En cambio, si esta completa el registro y matricula pasa as ser estudiante", type = "EnumEstadoEstudiante")
    private EnumEstadoEstudiante status;

    @Schema(description = "Fecha en que la persona es ingresada por primera vez al sistema, pero no matriculada a una carrera.", type = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ingresoLead;
    @Schema(description = "Fecha en que la persona se matricula por primera vez a una carrera.", type = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ingresoEstudiante;

    public Estudiante() {
    }

    public Estudiante(String nombre, String apellido, String email, String direccion, String telefono, EnumEstadoEstudiante status, Date ingresoLead, Date ingresoEstudiante) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.direccion = direccion;
        this.telefono = telefono;
        this.status = status;
        this.ingresoLead = ingresoLead;
        this.ingresoEstudiante = ingresoEstudiante;
    }
}

