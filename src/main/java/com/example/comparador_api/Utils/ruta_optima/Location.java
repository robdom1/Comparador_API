package com.example.comparador_api.Utils.ruta_optima;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private LatLng latLng;

    public Location(Double latitud, Double longitud){
        this.latLng = new LatLng(latitud, longitud);
    }
}
