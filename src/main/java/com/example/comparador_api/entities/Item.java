package com.example.comparador_api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {
    private String id;
    @Field("nombre")
    private String nombre;
    @Field("tienda")
    private String tienda;
    @Field("image")
    private String image;
    @Field("lastUpdate")
    private Date lastUpdate;
    @Field("lastPrice")
    private Double lastPrice;
    @Field("historial")
    private List<HistorialItem> historial;

}
