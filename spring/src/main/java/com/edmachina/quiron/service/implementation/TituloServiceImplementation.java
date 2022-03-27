package com.edmachina.quiron.service.implementation;

import com.edmachina.quiron.model.Titulo;
import com.edmachina.quiron.repository.TituloRepository;
import com.edmachina.quiron.service.TituloServiceInterfase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TituloServiceImplementation implements TituloServiceInterfase {

    private final TituloRepository repositorio;

    @Override
    public Optional<Titulo> findById(Long id) {
        log.info("Buscando la entidad Titulo con id: {}.", id);
        Optional<Titulo> titulo = repositorio.findById(id);
        if (titulo.isEmpty()) {
            log.info("No existe la entidad Titulo con id: {}.", id);
        } else {
            log.info("Se encontró la entidad Titulo: {}.", titulo);
        }
        return titulo;
    }

    @Override
    public List<Titulo> findAll() {
        log.info("Buscando todas las entidades Titulo.");
        List<Titulo> titulos = repositorio.findAll();
        if (titulos.isEmpty()) {
            log.info("La tabla no posee entidades Titulo.");
        } else {
            log.info("Se encontraron {} entidades Titulo, incluidas las borradas.", titulos.size());
        }
        return titulos;
    }

    @Override
    public List<Titulo> saveAll(List<Titulo> titulos) throws Exception {
        try {
            log.info("Insertando listado de entidades Titulo.");
            return repositorio.saveAll(titulos);
        } catch (Exception exepcion) {
            log.error("Se produjo un error al intentar persistir el listado de entidades Titulo.");
            return null;
        }
    }

    @Override
    public Titulo insert(Titulo titulo) throws Exception {
        try {
            log.info("Insertando la entidad Titulo: {}.",  titulo);
            repositorio.save(titulo);
            log.info("Se insertó correctamente la entidad Titulo: {}.", titulo);
            return titulo;
        } catch (Exception exepcion) {
            log.error("Se produjo un error al intentar persistir la entidad Titulo: {}. Se produjo la excepción {}.", titulo, exepcion);
            return null;
        }
    }

    @Override
    public Titulo update(Titulo titulo) {
        try {
            log.info("Actualizando la entidad Titulo: {}.", titulo);
            Optional<Titulo> entidad = repositorio.findById(titulo.getId());
            if (entidad.isEmpty()) {
                log.error("No existe la entidad Titulo que desea actualizar.");
                return null;
            }
            repositorio.save(titulo);
            log.info("Se actualizo correctamente la entidad Titulo: {}.", titulo);
            return titulo;
        } catch (Exception exepcion) {
            log.error("Se produjo un error al intentar modificar la entidad Titulo: {}. Se produjo la excepción {}.", titulo, exepcion);
            return null;
        }
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Borrando la entidad Titulo con id: {}.", id);
        Optional<Titulo> titulo = repositorio.findById(id);
        if (titulo.isEmpty()) {
            log.error("No se encontro ninguna entidad Titulo con id: {}.", id);
            return false;
        }
        log.info("Borrando la entidad Titulo: {}.", titulo.get());
        repositorio.delete(titulo.get());
        return true;
    }
}
