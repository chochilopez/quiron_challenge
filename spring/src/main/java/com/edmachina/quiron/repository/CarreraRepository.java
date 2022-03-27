package com.edmachina.quiron.repository;

import com.edmachina.quiron.model.Carrera;
import com.edmachina.quiron.model.CarreraKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, CarreraKey> {

    Optional<Carrera> findByEstudianteIdAndTituloId(Long idEstudiante, Long idTitulo);
}
