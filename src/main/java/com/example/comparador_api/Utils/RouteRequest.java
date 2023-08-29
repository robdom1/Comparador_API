package com.example.comparador_api.Utils;

import com.example.comparador_api.Utils.ruta_optima.WaypointItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteRequest {

    private List<WaypointItem> origins;
    private List<WaypointItem> destinations;

    private String travelMode;
    private String routingPreference;

}
