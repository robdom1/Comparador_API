package com.example.comparador_api.Utils.ruta_optima;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaypointItem {

    private Waypoint waypoint;

    public WaypointItem(String address) {
        this.waypoint = new Waypoint(address);
    }

    public WaypointItem(Location location) {
        this.waypoint = new Waypoint(location);
    }

}
