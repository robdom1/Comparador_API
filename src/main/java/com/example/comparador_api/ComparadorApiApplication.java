package com.example.comparador_api;

import com.example.comparador_api.entities.Direccion;
import com.example.comparador_api.entities.Tienda;
import com.example.comparador_api.repositories.TiendaRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;

@SpringBootApplication
@EnableMongoRepositories
@NoArgsConstructor
@AllArgsConstructor
public class ComparadorApiApplication implements CommandLineRunner {

    private static Logger LOG = LoggerFactory.getLogger(ComparadorApiApplication.class);
    @Autowired
    private TiendaRepo tiendaRepo;
    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(ComparadorApiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Tienda laSirena = new Tienda();
        laSirena.setNombre("La Sirena");
        laSirena.setDirecciones(new ArrayList<>());
        Direccion laSirena1 = new Direccion("F75X+98 Santiago De Los Caballeros","La Sirena, Av. Bartolomé Colón");
        laSirena.getDirecciones().add(laSirena1);
        Direccion laSirena2 = new Direccion("F72W+C2 Santiago De Los Caballeros","La Sirena, Calle del Sol");
        laSirena.getDirecciones().add(laSirena2);
        Direccion laSirena3 = new Direccion("F7PR+333 Santiago De Los Caballeros","La Sirena, Av. Estrella Sadhalá");
        laSirena.getDirecciones().add(laSirena3);
        Direccion laSirena4 = new Direccion("C8VJ+F3 Santiago De Los Caballeros","La Sirena, El Embrujo");
        laSirena.getDirecciones().add(laSirena4);
        tiendaRepo.save(laSirena);

        Tienda plazaLama = new Tienda();
        plazaLama.setNombre("Plaza Lama");
        plazaLama.setDirecciones(new ArrayList<>());
        Direccion plazaLama1 = new Direccion("F74X+FJC Santiago De Los Caballeros","Plaza Lama, Av. 27 de Febrero");
        plazaLama.getDirecciones().add(plazaLama1);
        tiendaRepo.save(plazaLama);
    }
}
