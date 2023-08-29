package com.example.comparador_api.Utils.ruta_optima;

import com.example.comparador_api.Utils.RouteResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

import java.util.*;

@Data
@NoArgsConstructor
@Builder
public class RutaOptima {

    public static List<List<DireccionTienda>> findAllRoutes(List<DireccionTienda> allNodes) {
        List<List<DireccionTienda>> allRoutes = new ArrayList<>();

        Map<String, List<DireccionTienda>> tiendas = new HashMap<>();
        DireccionTienda ubicacionActual = allNodes.get(0);
        allNodes = allNodes.subList(1, allNodes.size());

        // Agrupar los locales por tienda en un mapa
        for (DireccionTienda local : allNodes) {
            String nombreTienda = local.getNombreTienda();
            tiendas.putIfAbsent(nombreTienda, new ArrayList<>());
            tiendas.get(nombreTienda).add(local);
        }

        // Generar todas las combinaciones de locales visitando una tienda a la vez
        List<String> nombreTiendasOriginal = new ArrayList<>(tiendas.keySet());

        List<List<String>> permutaciones = PermutacionTienda.permutar(nombreTiendasOriginal);

        for (List<String> permutacion : permutaciones) {
            System.out.println(permutacion);
        }

        for(List<String> nombreTiendas: permutaciones){
            for (int i = 0; i < nombreTiendas.size() - 1; i++) {
                String tiendaActual = nombreTiendas.get(i);
                System.out.println("tiendaActual: " + tiendaActual);
                List<DireccionTienda> localesTiendaActual = tiendas.get(tiendaActual);

                for (int j = i + 1; j < nombreTiendas.size(); j++) {
                    String siguienteTienda = nombreTiendas.get(j);
                    System.out.println("siguienteTienda: " + siguienteTienda);
                    List<DireccionTienda> localesSiguienteTienda = tiendas.get(siguienteTienda);

                    // Combinar cada local de la tienda actual con cada local de la siguiente tienda
                    for (DireccionTienda localActual : localesTiendaActual) {
                        for (DireccionTienda localSiguiente : localesSiguienteTienda) {
                            List<DireccionTienda> route = new ArrayList<>();
                            route.add(localActual);
                            route.add(localSiguiente);
                            allRoutes.add(route);
                        }
                    }
                }
            }
        }


        for (List<DireccionTienda> route : allRoutes) {
            route.add(0, ubicacionActual);
            route.add(ubicacionActual); // Agregar el nodo de partida al final
        }


        return allRoutes; // Retorna todas las rutas posibles
    }

    public static List<DireccionTienda> findBestRoute(List<DireccionTienda> allNodes, List<RouteResponse> distances) {

        List<List<DireccionTienda>> allRoutes = findAllRoutes(allNodes);
        List<DireccionTienda> bestRoute = null;
        int bestCost = Integer.MAX_VALUE;

        for (List<DireccionTienda> route : allRoutes) {
            int cost = calculateTotalCost(route, distances);
            if (cost < bestCost) {
                bestCost = cost;
                bestRoute = route;
            }
        }

        return bestRoute; // Retorna la ruta con menor coste
    }

    private static int calculateTotalCost(List<DireccionTienda> route, List<RouteResponse> distances) {
        int totalCost = 0;

        for (int i = 0; i < route.size() - 1; i++) {
            Integer origen = route.get(i).getId();
            Integer destino = route.get(i + 1).getId();

            // Buscar la distancia entre el origen y el destino en la lista de RouteResponse
            for (RouteResponse response : distances) {
                if (response.getOriginIndex().equals(origen) && response.getDestinationIndex().equals(destino)) {
                    totalCost += response.getDistanceMeters();
                    break;
                }
            }
        }

        return totalCost;
    }


    // DIJKSTRA
    public static List<DireccionTienda> findOptimalRoute(List<RouteResponse> routeResponses, List<DireccionTienda> nodes) {
        // Step 1: Initialize the necessary data structures
        int numNodes = nodes.size();
        int[][] adjacencyMatrix = createAdjacencyMatrix(routeResponses, nodes);
        int[] distances = new int[numNodes];
        int[] previousNodes = new int[numNodes];
        PriorityQueue<Integer> queue = new PriorityQueue<>(numNodes, Comparator.comparingInt(node -> distances[node]));

        // Step 2: Initialize distances and previous nodes arrays
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[0] = 0;

        // Step 3: Add the source node to the queue
        queue.add(0);

        // Step 4: Apply Dijkstra's algorithm
        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            System.out.println("currentNode after poll: " + currentNode);
            int[] neighbors = adjacencyMatrix[currentNode];

            for (int i = 0; i < numNodes; i++) {
                if (neighbors[i] > 0) {
                    int newDistance = distances[currentNode] + neighbors[i];
                    if (newDistance < distances[i]) {
                        distances[i] = newDistance;
                        System.out.println("currentNode: " + currentNode);
                        previousNodes[i] = currentNode;
                        queue.add(i);
                    }
                }
                System.out.println(Arrays.toString(previousNodes));
            }
        }

        // Step 5: Build the optimal route based on the previousNodes array
        List<DireccionTienda> optimalRoute = new ArrayList<>();
        int currentNode = 0;
        while (currentNode != 0 || optimalRoute.size() < numNodes) {
            optimalRoute.add(nodes.get(currentNode));
            currentNode = previousNodes[currentNode];
        }

        // Step 6: Add the source node at the end to form a closed loop
        optimalRoute.add(nodes.get(0));

        // Step 7: Return the optimal route
        return optimalRoute;
    }

    private static int[][] createAdjacencyMatrix(List<RouteResponse> routeResponses, List<DireccionTienda> nodes) {
        int numNodes = nodes.size();
        int[][] adjacencyMatrix = new int[numNodes][numNodes];

        for (RouteResponse response : routeResponses) {
            int originIndex = response.getOriginIndex();
            int destinationIndex = response.getDestinationIndex();
            int distance = response.getDistanceMeters();
            if (nodes.get(originIndex).getNombreTienda().equals(nodes.get(destinationIndex).getNombreTienda())){
                adjacencyMatrix[originIndex][destinationIndex] = 0;
            }else {
                adjacencyMatrix[originIndex][destinationIndex] = distance;
            }

        }

        for (int[] row: adjacencyMatrix){
            System.out.println(Arrays.toString(row));
        }
        return adjacencyMatrix;
    }


}



