package com.example.comparador_api.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("Tiendas")
public class Tienda {
    @Id
    private String nombre;
    private List<Direccion> direcciones;
}
