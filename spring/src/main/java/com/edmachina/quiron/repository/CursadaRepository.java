package com.edmachina.quiron.repository;

import com.edmachina.quiron.model.Cursada;
import com.edmachina.quiron.model.CursadaKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CursadaRepository extends JpaRepository<Cursada, CursadaKey> {

    Optional<Cursada> findByEstudianteIdAndMateriaId(Long estudianteId, Long materiaId);
}
