package com.example.comparador_api.services;

import com.example.comparador_api.entities.Producto;
import com.example.comparador_api.repositories.ProductoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductoService {

    private final ProductoRepo productoRepo;

    public ResponseEntity<?> obtenerTodos(String nombre, int page, int size){
        try {
            List<Producto> productos = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);

            Page<Producto> pageProduct;

            if(nombre == null){
                pageProduct = productoRepo.findByOrderByNombre(paging);
            }else{
                pageProduct = productoRepo.findByNombreContainingIgnoreCaseOrderByNombre(nombre, paging);
            }

            productos = pageProduct.getContent();

            return ResponseEntity.ok(productos);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public Long getProductCount(){
        return productoRepo.count();
    }

    //GeoLocalizacion localizacion


}
