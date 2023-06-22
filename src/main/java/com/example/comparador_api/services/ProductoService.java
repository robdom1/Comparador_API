package com.example.comparador_api.services;

import com.example.comparador_api.entities.Direccion;
import com.example.comparador_api.entities.Producto;
import com.example.comparador_api.entities.Tienda;
import com.example.comparador_api.repositories.ProductoRepo;
import com.example.comparador_api.repositories.TiendaRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ProductoService {

    private final ProductoRepo productoRepo;
    private final TiendaRepo tiendaRepo;

    private Environment env;
    @Value("${googleApiKey}")
    private String API_KEY;

    public List<Producto> obtenerTodos(){
        return productoRepo.findAll();
    }

    public Long getProductCount(){
        return productoRepo.count();
    }

    //GeoLocalizacion localizacion
    public ArrayNode obtenerDistancia(String userLocation) throws JsonProcessingException {

        String url = "https://routes.googleapis.com/distanceMatrix/v2:computeRouteMatrix";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Goog-Api-Key", API_KEY);
        headers.set("X-Goog-FieldMask", "destinationIndex,duration,distanceMeters,condition");



        // Crear ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        // Crear array response
        ArrayNode arrayResponse = objectMapper.createArrayNode();

        // Crear objeto JSON
        ObjectNode jsonObject = objectMapper.createObjectNode();

        // Agregar propiedades adicionales
        jsonObject.put("travelMode", "DRIVE");
        jsonObject.put("routingPreference", "TRAFFIC_AWARE");

        // Crear array "origins"
        ArrayNode originsArray = objectMapper.createArrayNode();
        ObjectNode origin = objectMapper.createObjectNode();
        ObjectNode originWaypoint = objectMapper.createObjectNode();
        originWaypoint.put("address", userLocation);
        origin.set("waypoint", originWaypoint);
        originsArray.add(origin);

//        // Crear array "destinations"
//        ArrayNode destinationsArray = objectMapper.createArrayNode();


        for (Tienda tienda: this.tiendaRepo.findAll()){
            // Crear array "destinations"
            ArrayNode destinationsArray = objectMapper.createArrayNode();
            Map<Integer, ObjectNode> direcciones = new HashMap<Integer, ObjectNode>();
            int i = 0;
            for(Direccion direccion: tienda.getDirecciones()){

                ObjectNode direccionObject = objectMapper.createObjectNode();
                direccionObject.put("nombreTienda", tienda.getNombre());
                direccionObject.put("direccion", direccion.getDireccion());
                direccionObject.put("nombreDireccion", direccion.getNombreLocal());
                direcciones.put(i++,direccionObject);

                ObjectNode destination = objectMapper.createObjectNode();
                ObjectNode destinationWaypoint = objectMapper.createObjectNode();
                destinationWaypoint.put("address",direccion.getDireccion());
                destination.set("waypoint", destinationWaypoint);
                destinationsArray.add(destination);
            }

            jsonObject.set("origins", originsArray);
            jsonObject.set("destinations", destinationsArray);

            // Convertir a cadena JSON
            String jsonString = objectMapper.writeValueAsString(jsonObject);

            HttpEntity<String> request = new HttpEntity<>(jsonString, headers);

            ArrayNode response = restTemplate.postForObject(url, request, ArrayNode.class);
            if(response == null){
                continue;
            }

            for(JsonNode element: response){
                ObjectNode responseElement = (ObjectNode) element;
                ObjectNode direccion = direcciones.get(responseElement.get("destinationIndex").asInt());
                responseElement.remove("destinationIndex");
                responseElement.set("address", direccion.get("direccion"));
                responseElement.set("addressName", direccion.get("nombreDireccion"));
                responseElement.set("store", direccion.get("nombreTienda"));
                System.out.println(responseElement);
            }

            arrayResponse.add(getClosestStoreLocal(response));

        }

        return arrayResponse;
//
//        return response;

    }

    public ObjectNode getClosestStoreLocal(ArrayNode localsDistances){

        ObjectNode closestLocal = (ObjectNode) localsDistances.get(0);

        for(JsonNode element: localsDistances){
            ObjectNode responseElement = (ObjectNode) element;
            if (responseElement.get("distanceMeters").asDouble() < closestLocal.get("distanceMeters").asDouble()){
                closestLocal = responseElement;
            }
        }

        return closestLocal;
    }

//    public ArrayNode callGoogleMapsApi()


}
