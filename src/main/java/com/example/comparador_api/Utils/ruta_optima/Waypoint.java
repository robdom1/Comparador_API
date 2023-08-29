package com.example.comparador_api.Utils.ruta_optima;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Waypoint {
    private String address;
    private Location location;

    public Waypoint(String address) {
        this.address = address;
    }

    public Waypoint(Location location) {
        this.location = location;
    }

}

