package com.edmachina.quiron.model;

import com.edmachina.quiron.model.enumerator.EnumGradoTitulo;
import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Getter
@Entity
@Setter
@RequiredArgsConstructor
public class Titulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String resolucion;

    @Enumerated(EnumType.STRING)
    private EnumGradoTitulo grado;

    @OneToMany
    Set<Materia> materias;
}
