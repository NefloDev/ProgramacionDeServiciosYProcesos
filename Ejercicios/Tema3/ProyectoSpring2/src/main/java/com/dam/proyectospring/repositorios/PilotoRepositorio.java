package com.dam.proyectospring.repositorios;

import com.dam.proyectospring.modelos.Piloto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PilotoRepositorio extends MongoRepository<Piloto, String> {
    Optional<Piloto> findPilotoById(String id);
}
