package com.example.comparador_api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Direccion {
    private String direccion;
    private String nombreLocal;
    private Double latitude;
    private Double longitude;
}
