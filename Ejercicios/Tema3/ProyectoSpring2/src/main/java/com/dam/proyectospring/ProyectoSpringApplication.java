package com.dam.proyectospring;

import com.dam.proyectospring.modelos.Piloto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.sound.midi.Soundbank;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
public class ProyectoSpringApplication {
    private static WebClient client = WebClient.create("http://localhost:8080");
    public static void main(String[] args) {
        SpringApplication.run(ProyectoSpringApplication.class, args);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean exit = false;
        int input;
        while (!exit){
            try{
                System.out.println("""
                        ######### OPCIONES #########
                        1.- Buscar todos los pilotos
                        2.- Buscar un piloto por id
                        3.- Crear un nuevo piloto
                        4.- Modificar un piloto
                        5.- Eliminar un piloto
                        6.- Salir
                        """);
                input = Integer.parseInt(reader.readLine());
                switch (input){
                    case 1:
                        searchAll();
                        break;
                    case 2:
                        System.out.print("Enter the pilot's id: ");
                        String id = reader.readLine();
                        searchOne(id);
                    case 3:
                        System.out.print("Id: ");
                        String newPilotId = reader.readLine();
                        System.out.print("Name: ");
                        String newPilotName = reader.readLine();
                        System.out.print("Abbreviation: ");
                        String newPilotAbbreviation = reader.readLine();
                        boolean retry = false;
                        do{
                            try{
                                System.out.print("Id: ");
                                int newPilotNumber = Integer.parseInt(reader.readLine());
                            }catch (NumberFormatException e){
                                System.err.println("Number format incorrect");
                            }
                        }while (retry);
                        System.out.print("Id: ");
                        String newPilotId = reader.readLine();
                        System.out.print("Id: ");
                        String newPilotId = reader.readLine();
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    default:
                        System.err.println("Index out of bounds");
                }
            }catch (IOException ignored){
            }catch (NumberFormatException exception){
                System.err.println("Input ");
            }
        }
    }

    private static void searchAll(){
        Flux<Piloto> pilotoMono = client.get()
                .uri("pilotos")
                .retrieve()
                .bodyToFlux(Piloto.class);
        pilotoMono.subscribe(System.out::println);
    }

    private static void searchOne(String id){
        Flux<Piloto> pilotoMono = client.get()
                .uri("piloto/{id}", "1")
                .retrieve()
                .bodyToFlux(Piloto.class);
        pilotoMono.subscribe(System.out::println);
    }
}