package com.example.comparador_api.repositories;

import com.example.comparador_api.entities.Producto;
import com.example.comparador_api.entities.Tienda;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TiendaRepo extends MongoRepository<Tienda, String> {
}
