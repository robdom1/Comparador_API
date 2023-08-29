package com.example.comparador_api.Utils;

import com.example.comparador_api.Utils.ruta_optima.DireccionTienda;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteResponse {

    private Integer originIndex;
    private Integer destinationIndex;
    private String duration;
    private Integer durationInt;
    private Integer distanceMeters;
    private String condition;
    private DireccionTienda origin;
    private DireccionTienda destination;
}
