package com.example.comparador_api.Utils.ruta_optima;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DireccionTienda {
    private Integer id;
    private String direccion;
    private String nombreLocal;
    private String nombreTienda;
    private Double latitude;
    private Double longitude;
}
