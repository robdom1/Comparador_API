package com.example.comparador_api.Utils.ruta_optima;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
public class PermutacionTienda {

    public static List<List<String>> permutar(List<String> lista) {
        List<List<String>> permutaciones = new ArrayList<>();
        permutarRecursivamente(lista, 0, permutaciones);
        return permutaciones;
    }

    private static void permutarRecursivamente(List<String> lista, int indice, List<List<String>> permutaciones) {
        if (indice == lista.size() - 1) {
            permutaciones.add(new ArrayList<>(lista));
        } else {
            for (int i = indice; i < lista.size(); i++) {
                intercambiar(lista, indice, i);
                permutarRecursivamente(lista, indice + 1, permutaciones);
                intercambiar(lista, indice, i);
            }
        }
    }

    private static void intercambiar(List<String> lista, int i, int j) {
        String temp = lista.get(i);
        lista.set(i, lista.get(j));
        lista.set(j, temp);
    }
}
