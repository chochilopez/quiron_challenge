package com.edmachina.quiron.model;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@Embeddable
@Getter
@NoArgsConstructor
@Setter
public class CursadaKey implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long estudianteId;
    private Long materiaId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CursadaKey that = (CursadaKey) o;
        return Objects.equals(estudianteId, that.estudianteId) && Objects.equals(materiaId, that.materiaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estudianteId, materiaId);
    }
}

