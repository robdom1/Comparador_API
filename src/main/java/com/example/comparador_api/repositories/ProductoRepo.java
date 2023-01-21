package com.example.comparador_api.repositories;

import com.example.comparador_api.entities.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductoRepo extends MongoRepository<Producto, String> {
}
