package com.example.comparador_api.services;

import com.example.comparador_api.Utils.GeoLocalizacion;
import com.example.comparador_api.entities.Producto;
import com.example.comparador_api.repositories.ProductoRepo;
import lombok.RequiredArgsConstructor;

import org.apache.catalina.connector.Request;
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

    public List<Producto> obtenerTodos(){
        return productoRepo.findAll();
    }

    //GeoLocalizacion localizacion
    public String obtenerDistancia() {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(
                "https://maps.googleapis.com/maps/api/distancematrix/json?destinations=F75X+985, Av. Bartolomé Colón,Santiago De Los Caballeros 51000|19.45619239996315,-70.70099395491553&origins=19.47174187123321,-70.67976578898234&key=AIzaSyBfE2yfo13M25uN_s7l-rBKy71-Y0UtJjQ",
                String.class
                );

    }


}
