package com.example.comparador_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class ComparadorApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComparadorApiApplication.class, args);
    }

}
