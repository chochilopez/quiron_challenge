package com.edmachina.quiron.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@Embeddable
@Getter
@NoArgsConstructor
@Setter
public class CarreraKey implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long estudianteId;
    private Long tituloId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarreraKey that = (CarreraKey) o;
        return Objects.equals(estudianteId, that.estudianteId) && Objects.equals(tituloId, that.tituloId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estudianteId, tituloId);
    }
}

