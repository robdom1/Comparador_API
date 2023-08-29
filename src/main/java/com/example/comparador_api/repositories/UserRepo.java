package com.example.comparador_api.repositories;

import com.example.comparador_api.entities.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<Usuario, String> {

    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
