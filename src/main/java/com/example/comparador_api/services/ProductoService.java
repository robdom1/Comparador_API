package com.example.comparador_api.services;

import com.example.comparador_api.Utils.GeoLocalizacion;
import com.example.comparador_api.entities.Producto;
import com.example.comparador_api.repositories.ProductoRepo;
import lombok.RequiredArgsConstructor;

import lombok.Value;
import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductoService {

    private final ProductoRepo productoRepo;

    @Autowired
    private Environment env;
    private String API_KEY = env.getProperty("googleApiKey");

    public List<Producto> obtenerTodos(){
        return productoRepo.findAll();
    }

    //GeoLocalizacion localizacion
    public String obtenerDistancia() {
        RestTemplate restTemplate = new RestTemplate();

        String destination= "F75X+985, Av. Bartolomé Colón,Santiago De Los Caballeros 51000";
        String origin = "19.47174187123321,-70.67976578898234";
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json";
        return restTemplate.getForObject(
                url + "?destinations=" + destination + "&origins=" + origin + "&key="+API_KEY,
                String.class
                );

    }


}
