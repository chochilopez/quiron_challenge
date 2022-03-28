package com.edmachina.quiron.model;

import com.edmachina.quiron.model.enumerator.EnumEstadoEstudiante;
import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@Setter
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String email;
    private String direccion;
    private String telefono;

    private EnumEstadoEstudiante status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date ingresoLead;
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

