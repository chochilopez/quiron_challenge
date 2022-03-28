package com.edmachina.quiron.model;

import lombok.*;
import javax.persistence.*;

@AllArgsConstructor
@Getter
@Entity
@NoArgsConstructor
@Setter
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    public Materia(String nombre) {
        this.nombre = nombre;
    }
}
