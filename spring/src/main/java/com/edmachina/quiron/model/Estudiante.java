package com.edmachina.quiron.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Entity
@Setter
@RequiredArgsConstructor
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String email;
    private String direccion;
    private String telefono;

    @OneToMany
    Set<Carrera> carreras;

    public Estudiante(String nombre, String apellido, String email, String direccion, String telefono, Set<Carrera> carreras) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.direccion = direccion;
        this.telefono = telefono;
        this.carreras = carreras;
    }
}

