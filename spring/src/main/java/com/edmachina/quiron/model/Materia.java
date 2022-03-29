package com.edmachina.quiron.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.persistence.*;

@AllArgsConstructor
@Getter
@Entity
@NoArgsConstructor
@Schema(description = "Modelo de asignatura, posee un nombre propio.")
@Setter
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre propio de la asignatura.", type = "String")
    private String nombre;

    public Materia(String nombre) {
        this.nombre = nombre;
    }
}
