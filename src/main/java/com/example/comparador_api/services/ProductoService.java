package com.example.comparador_api.services;

import com.example.comparador_api.entities.Producto;
import com.example.comparador_api.repositories.ProductoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductoService {

    private final ProductoRepo productoRepo;
    private Environment env;
    @Value("${googleApiKey}")
    private String API_KEY;

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
