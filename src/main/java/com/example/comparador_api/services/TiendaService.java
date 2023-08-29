package com.example.comparador_api.services;

import com.example.comparador_api.Utils.ruta_optima.DireccionTienda;
import com.example.comparador_api.entities.Direccion;
import com.example.comparador_api.entities.Tienda;
import com.example.comparador_api.repositories.TiendaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TiendaService {

    private final TiendaRepo tiendaRepo;

    public List<Tienda> obtenerTiendas(){
        return tiendaRepo.findAll();
    }

    public List<DireccionTienda> obtenerDirecciones(){

        List<DireccionTienda> direcciones = new ArrayList<>();

        for(Tienda tienda: obtenerTiendas()){
            for (Direccion direccion: tienda.getDirecciones()){
                DireccionTienda direccionTienda = new DireccionTienda();
                direccionTienda.setNombreTienda(tienda.getNombre());
                direccionTienda.setDireccion(direccion.getDireccion());
                direccionTienda.setNombreLocal(direccion.getNombreLocal());
                direccionTienda.setLatitude(direccion.getLatitude());
                direccionTienda.setLongitude(direccion.getLongitude());
                direcciones.add(direccionTienda);
            }
        }

        return direcciones;
    }
}
