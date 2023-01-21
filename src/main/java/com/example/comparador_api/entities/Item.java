package com.example.comparador_api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private String id;
    private String Nombre;
    private String Vendedor;
    private String Categoria;
    private List<String> Tags;
    private Double Precio;
    private List<String> Imagen;
    private String Tienda;
}
