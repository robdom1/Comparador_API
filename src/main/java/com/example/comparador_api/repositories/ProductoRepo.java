package com.example.comparador_api.repositories;

import com.example.comparador_api.entities.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductoRepo extends MongoRepository<Producto, String> {
    Page<Producto> findByNombreContainingIgnoreCaseOrderByNombre(String nombre, Pageable pageable);

    Page<Producto> findByOrderByNombre(Pageable pageable);
}
