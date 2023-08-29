package com.example.comparador_api.controllers;

import com.example.comparador_api.services.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producto")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false) String nombre,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "50") int size){
        return productoService.obtenerTodos(nombre, page, size);
    }

    @GetMapping("/count")
    public Long getProductCount(){return productoService.getProductCount();}
}
