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

    @OneToMany(mappedBy = "estudiante")
    Set<Cursada> cursadas;

    @OneToMany(mappedBy = "estudiante")
    Set<Carrera> carreras;
}

