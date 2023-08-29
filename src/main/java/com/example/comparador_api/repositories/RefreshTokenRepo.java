package com.example.comparador_api.repositories;

import com.example.comparador_api.entities.RefreshToken;
import com.example.comparador_api.entities.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RefreshTokenRepo extends MongoRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);
    Optional<String> deleteByUsuario(Usuario user);
}
