package com.example.comparador_api.repositories;

import com.example.comparador_api.entities.ERole;
import com.example.comparador_api.entities.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepo extends MongoRepository<Role,String> {
    Optional<Role> findByName(ERole name);
}
