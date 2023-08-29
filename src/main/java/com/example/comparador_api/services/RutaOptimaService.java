package com.example.comparador_api.services;

import com.example.comparador_api.Utils.RouteRequest;
import com.example.comparador_api.Utils.RouteResponse;
import com.example.comparador_api.Utils.ruta_optima.DireccionTienda;
import com.example.comparador_api.Utils.ruta_optima.Location;
import com.example.comparador_api.Utils.ruta_optima.RutaOptima;
import com.example.comparador_api.Utils.ruta_optima.WaypointItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RutaOptimaService {

    @Value("${googleApiKey}")
    private String API_KEY;
    private final TiendaService tiendaService;
    List<DireccionTienda> direccionTiendaArray;
    RouteResponse[] routeResponses;

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public RouteResponse[]  obtenerDistancia(Double latitud, Double longitud){

        String url = "https://routes.googleapis.com/distanceMatrix/v2:computeRouteMatrix";

        RouteRequest routeRequest = new RouteRequest();

        List<WaypointItem> waypointArray = new ArrayList<>();
        Map<Integer, DireccionTienda> direccionTiendaMap = new HashMap<>();

        crearListaWaypointTiendas(latitud, longitud, waypointArray, direccionTiendaMap);

        routeRequest.setOrigins(waypointArray);
        routeRequest.setDestinations(waypointArray);
        routeRequest.setTravelMode("DRIVE");
        routeRequest.setRoutingPreference("TRAFFIC_AWARE");

        String entity = gson.toJson(routeRequest);
        System.out.println(entity);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Goog-Api-Key", API_KEY);
        headers.set("X-Goog-FieldMask", "originIndex,destinationIndex,duration,distanceMeters,condition");
        HttpEntity<String> request = new HttpEntity<>(entity, headers);

        routeResponses = restTemplate.postForObject(url, request, RouteResponse[].class);

        if(routeResponses != null){
            System.out.println(gson.toJson(routeResponses));
            mapRouteResponse(List.of(routeResponses), direccionTiendaMap, latitud, longitud);
        }

        return routeResponses;

    }

    private void mapRouteResponse(List<RouteResponse> routeResponsesArg,
                                  Map<Integer, DireccionTienda> direccionTiendaMap,
                                  Double latitud, Double longitud) {

        List<RouteResponse> filteredList = routeResponsesArg.stream()
                .filter(route -> !route.getOriginIndex().equals(route.getDestinationIndex()))
                .toList();

        for (RouteResponse route : filteredList){
            Integer destinationIndex = route.getDestinationIndex();
            Integer originIndex = route.getOriginIndex();

            if (route.getDuration() == null){
                throw new IllegalArgumentException("Dirección inválida");
            }

            route.setDurationInt(Integer.parseInt(route.getDuration().substring(0, route.getDuration().length() - 1)));

            if(direccionTiendaMap.containsKey(destinationIndex)){
                route.setDestination(direccionTiendaMap.get(destinationIndex));
            }else{
                route.setDestination(
                        DireccionTienda.builder()
                                    .nombreLocal("Ubicación Actual")
                                    .nombreTienda("Ubicación Actual")
                                    .direccion(latitud + ", " + longitud)
                                    .latitude(latitud)
                                    .longitude(longitud)
                                    .build()
                );
            }

            if(direccionTiendaMap.containsKey(originIndex)){
                route.setOrigin(direccionTiendaMap.get(originIndex));
            }else{
                route.setOrigin(
                        DireccionTienda.builder()
                                .nombreLocal("Ubicación Actual")
                                .nombreTienda("Ubicación Actual")
                                .direccion(latitud + ", " + longitud)
                                .latitude(latitud)
                                .longitude(longitud)
                                .build()
                );
            }
        }

        routeResponses = filteredList.toArray(new RouteResponse[0]);

    }

    public void crearListaWaypointTiendas(
            Double latitud, Double longitud,
            List<WaypointItem> waypointArray,
            Map<Integer, DireccionTienda> direccionTiendaMap){

        direccionTiendaArray = new ArrayList<>();

        DireccionTienda direccionUsuario = DireccionTienda.builder()
                .nombreLocal("Ubicación Actual")
                .nombreTienda("Ubicación Actual")
                .direccion(latitud + ", " + longitud)
                .id(0)
                .latitude(latitud)
                .longitude(longitud)
                .build();


        direccionTiendaMap.put(0, direccionUsuario);
        direccionTiendaArray.add(direccionUsuario);
        waypointArray.add(new WaypointItem(new Location(latitud, longitud)));

        int i = 1;
        for (DireccionTienda direccion: tiendaService.obtenerDirecciones()){
            direccion.setId(i);
            direccionTiendaArray.add(direccion);

            direccionTiendaMap.put(i, direccion);

            waypointArray.add(new WaypointItem(
                    new Location(direccion.getLatitude(), direccion.getLongitude())
            ));
            i++;
        }

    }

    public List<DireccionTienda> encontrarRutaOptima(Double latitud, Double longitud) {
        routeResponses = obtenerDistancia(latitud, longitud);
//        return RutaOptima.findAllRoutes(direccionTiendaArray);
        return RutaOptima.findBestRoute(direccionTiendaArray, List.of(routeResponses));
    }

    public List<DireccionTienda> encontrarRutaOptimaDijkstra(Double latitud, Double longitud) {
        routeResponses = obtenerDistancia(latitud, longitud);
//        return RutaOptima.findAllRoutes(direccionTiendaArray);
        return RutaOptima.findOptimalRoute(List.of(routeResponses), direccionTiendaArray);
    }

}
