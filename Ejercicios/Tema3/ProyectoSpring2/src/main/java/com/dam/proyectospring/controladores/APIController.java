package com.dam.proyectospring.controladores;

import com.dam.proyectospring.modelos.Piloto;
import com.dam.proyectospring.servicios.PilotoServicio;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class APIController {
    private final PilotoServicio pilotoServicio;
    @Autowired
    public APIController(PilotoServicio pilotoServicio){
        this.pilotoServicio = pilotoServicio;
    }

    // GET de todos los pilotos, devuelve un JSON con todos los pilotos
    @GetMapping("/pilotos")
    public ResponseEntity<List<Piloto>> getPilots() {
        List<Piloto> pilotos = pilotoServicio.findAllPilotos();
        return new ResponseEntity<>(pilotos, HttpStatus.OK);
    }

    // GET de un piloto, devuelve un JSON del piloto
    @GetMapping("/piloto/{id}")
    public ResponseEntity<Piloto> getPilot(@PathVariable long id) {
        return new ResponseEntity<>(pilotoServicio.findById(String.valueOf(id)).get(), HttpStatus.OK);
    }

    // POST de un piloto, crea un piloto
    @PostMapping("/pilotos")
    public ResponseEntity<Piloto> addPilot(@RequestBody Piloto piloto) {
        if (pilotoServicio.findById(piloto.get_id()).isPresent()){
            return  ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(pilotoServicio.savePilot(piloto), HttpStatus.OK);
    }

    // PUT de un piloto, actualiza un piloto
    @PutMapping("/pilotos")
    public ResponseEntity<Piloto> modifyPilot(@RequestBody Piloto piloto) {
        return new ResponseEntity<>(pilotoServicio.savePilot(piloto), HttpStatus.OK);
    }

    @DeleteMapping("/piloto/{id}")
    public ResponseEntity<Response> deletePilot(@PathVariable long id) {
        pilotoServicio.removePilotById(String.valueOf(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
