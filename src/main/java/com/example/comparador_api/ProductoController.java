package com.example.comparador_api;

import com.example.comparador_api.Utils.GeoLocalizacion;
import com.example.comparador_api.entities.Producto;
import com.example.comparador_api.repositories.ProductoRepo;
import com.example.comparador_api.services.ProductoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("distancia")
    public ArrayNode obtenerDistancia(@RequestBody ObjectNode strDireccion) throws JsonProcessingException {
        System.out.println(strDireccion);
        return productoService.obtenerDistancia(strDireccion.get("direccion").asText());
    }

    @GetMapping("count")
    public Long getProductCount(){return productoService.getProductCount();}
}
