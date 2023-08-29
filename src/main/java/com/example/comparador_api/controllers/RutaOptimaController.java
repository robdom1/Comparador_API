package com.example.comparador_api.controllers;

import com.example.comparador_api.services.RutaOptimaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ruta")
@RequiredArgsConstructor
public class RutaOptimaController {

    private final RutaOptimaService rutaOptimaService;

    @PostMapping("/distancia")
    public ResponseEntity<?> obtenerDistancia(@RequestParam Double latitud, @RequestParam Double longitud) {
        return ResponseEntity.ok().body(rutaOptimaService.obtenerDistancia(latitud, longitud));
    }

    @PostMapping
    public ResponseEntity<?> obtenerRutaOptima(@RequestParam Double latitud, @RequestParam Double longitud) {
        return ResponseEntity.ok().body(rutaOptimaService.encontrarRutaOptima(latitud, longitud));
    }

    @PostMapping("/dijkstra")
    public ResponseEntity<?> obtenerRutaOptimaDijkstra(@RequestParam Double latitud, @RequestParam Double longitud) {
        return ResponseEntity.ok().body(rutaOptimaService.encontrarRutaOptimaDijkstra(latitud, longitud));
    }
}
