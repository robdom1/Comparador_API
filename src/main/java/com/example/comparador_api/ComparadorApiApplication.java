package com.example.comparador_api;

import com.example.comparador_api.entities.Direccion;
import com.example.comparador_api.entities.ERole;
import com.example.comparador_api.entities.Role;
import com.example.comparador_api.entities.Tienda;
import com.example.comparador_api.repositories.RoleRepo;
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
@RequiredArgsConstructor
public class ComparadorApiApplication implements CommandLineRunner {

    private static Logger LOG = LoggerFactory.getLogger(ComparadorApiApplication.class);
    private final TiendaRepo tiendaRepo;
    private final RoleRepo roleRepo;
    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(ComparadorApiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Tienda laSirena = new Tienda();
        laSirena.setNombre("La Sirena");
        laSirena.setDirecciones(new ArrayList<>());
        Direccion laSirena1 = new Direccion("F75X+98 Santiago De Los Caballeros","La Sirena, Av. Bartolomé Colón", 19.458440357685028, -70.70164573559349);
        laSirena.getDirecciones().add(laSirena1);
        Direccion laSirena2 = new Direccion("F72W+C2 Santiago De Los Caballeros","La Sirena, Calle del Sol", 19.45115017617285, -70.70490318222045);
        laSirena.getDirecciones().add(laSirena2);
        Direccion laSirena3 = new Direccion("F7PR+333 Santiago De Los Caballeros","La Sirena, Av. Estrella Sadhalá", 19.484760202272618, -70.70921334611683);
        laSirena.getDirecciones().add(laSirena3);
        Direccion laSirena4 = new Direccion("C8VJ+F3 Santiago De Los Caballeros","La Sirena, El Embrujo", 19.4437422862552, -70.66978108222047);
        laSirena.getDirecciones().add(laSirena4);
        tiendaRepo.save(laSirena);

        Tienda plazaLama = new Tienda();
        plazaLama.setNombre("Plaza Lama");
        plazaLama.setDirecciones(new ArrayList<>());
        Direccion plazaLama1 = new Direccion("F74X+FJC Santiago De Los Caballeros","Plaza Lama, Av. 27 de Febrero", 19.456188626547608, -70.70099024262281);
        plazaLama.getDirecciones().add(plazaLama1);
        tiendaRepo.save(plazaLama);

        Role userRole = Role.builder()
                .id("1")
                .name(ERole.ROLE_USER)
                .build();
        roleRepo.save(userRole);

        Role adminRole = Role.builder()
                .id("2")
                .name(ERole.ROLE_ADMIN)
                .build();
        roleRepo.save(adminRole);

    }
}
