package org.example.practica5repasoej3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.*;

@SpringBootApplication
public class Practica5RepasoEj3Application {
    public static Clock clock;

    public static void main(String[] args) {
        clock = Clock.fixed(Instant.now(), ZoneOffset.systemDefault());
        SpringApplication.run(Practica5RepasoEj3Application.class, args);
    }

}
