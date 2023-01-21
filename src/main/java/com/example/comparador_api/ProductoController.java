package com.example.comparador_api;

import com.example.comparador_api.Utils.GeoLocalizacion;
import com.example.comparador_api.entities.Producto;
import com.example.comparador_api.repositories.ProductoRepo;
import com.example.comparador_api.services.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("producto")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public List<Producto> getAll(){
        return productoService.obtenerTodos();
    }

    // @RequestBody GeoLocalizacion localizacion
    @GetMapping("distancia")
    public String obtenerDistancia(){
        return productoService.obtenerDistancia();
    }
}
