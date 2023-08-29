package com.example.comparador_api.repositories;

import com.example.comparador_api.entities.Cart;
import com.example.comparador_api.entities.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepo extends MongoRepository<Cart, String> {
}
