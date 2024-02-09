package com.dam.proyectospring;

import com.dam.proyectospring.modelos.Piloto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class ProyectoSpringApplication {
    private static WebClient client = WebClient.create("http://localhost:8080");
    public static void main(String[] args) {
        SpringApplication.run(ProyectoSpringApplication.class, args);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean exit = false;
        int input;
        while (!exit){
            System.out.println("\n");
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
                        searchOne(reader.readLine());
                        break;
                    case 3:
                        addOne(requestPilot(null));
                        break;
                    case 4:
                        System.out.println("Enter the pilot's id: ");
                        String id = reader.readLine();
                        Piloto pilot = client.get().uri("piloto/{id}", id).retrieve().bodyToMono(Piloto.class).block();
                        if (pilot != null) {
                            modifyOne(requestPilot(id));
                        }else{
                            System.err.println("Pilot not found");
                        }
                        break;
                    case 5:
                        System.out.println("Enter the pilot's id: ");
                        deleteOne(reader.readLine());
                        break;
                    case 6:
                        exit = true;
                        break;
                    default:
                        System.err.println("Index out of bounds");
                }
            }catch (IOException ignored){
            }catch (NumberFormatException exception){
                System.err.println("Input mismatch");
            }
        }
    }

    private static void searchAll(){
        List<Piloto> pilotoFlux = client.get()
                .uri("pilotos")
                .retrieve()
                .bodyToFlux(Piloto.class)
                .collectList()
                .block();
        if (pilotoFlux != null){
            pilotoFlux.forEach(System.out::println);
        }else{
            System.err.println("No pilots found");
        }
    }

    private static void searchOne(String id){
        Piloto pilotoMono = client.get()
                .uri("piloto/{id}", "1")
                .retrieve()
                .bodyToMono(Piloto.class)
                .block();
        if (pilotoMono != null){
            System.out.println(pilotoMono);
        }else{
            System.err.println("Pilot not found");
        }
    }

    private static void addOne(Piloto pilot){
        Piloto pilotoMono = client.post()
                .uri("pilotos")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(pilot))
                .retrieve()
                .bodyToMono(Piloto.class)
                .block();
        if (pilotoMono != null){
            System.out.println("Added: " + pilotoMono.getDriver());
        }else{
            System.err.println("Pilot could'nt be modified");
        }
    }

    private static void modifyOne(Piloto pilot){
        Piloto pilotoMono = client.put()
                .uri("pilotos")
                .body(BodyInserters.fromValue(pilot))
                .retrieve()
                .bodyToMono(Piloto.class)
                .block();
        if (pilotoMono != null){
            System.out.println("Modified: " + pilotoMono.getDriver());
        }else{
            System.err.println("Pilot could'nt be modified");
        }
    }

    private static void deleteOne(String id){
        Piloto pilotoDelete = client.get().uri("piloto/", id).retrieve().bodyToMono(Piloto.class).block();
        if (pilotoDelete != null){
            client.delete()
                    .uri("pilotos", BodyInserters.fromValue(pilotoDelete))
                    .retrieve();
            System.out.println("Driver deleted");
        }else{
            System.err.println("Pilot not found");
        }
    }

    private static Piloto requestPilot(String id) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String newPilotId;
        if(id == null){
            System.out.print("Id: ");
            newPilotId = reader.readLine();
        }else{
            newPilotId = id;
        }
        System.out.print("Name: ");
        String newPilotName = reader.readLine();
        System.out.print("Abbreviation: ");
        String newPilotAbbreviation = reader.readLine();
        boolean retry;
        int newPilotNumber = 0;
        do{
            try{
                System.out.print("Number: ");
                newPilotNumber = Integer.parseInt(reader.readLine());
                retry = false;
            }catch (NumberFormatException e){
                retry = true;
                System.err.println("Number format incorrect");
            }
        }while (retry);
        System.out.print("Team: ");
        String newPilotTeam = reader.readLine();
        System.out.print("Country: ");
        String newPilotCountry = reader.readLine();
        LocalDate newPilotDOB = null;
        do{
            try{
                System.out.print("Date of birth (YYYY-MM-DD): ");
                newPilotDOB = LocalDate.parse(reader.readLine());
                retry = false;
            }catch (DateTimeParseException e){
                retry = true;
                System.err.println("Date format incorrect");
            }
        }while (retry);
        return new Piloto(newPilotId, newPilotName, newPilotAbbreviation,
                newPilotNumber, newPilotTeam, newPilotCountry, newPilotDOB);
    }
}