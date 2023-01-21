package com.example.comparador_api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("Productos")
public class Producto {
    @Id
    private String id;
    private List<Item> Productos;
    private String Nombre;
}
