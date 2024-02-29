package org.example.ejercicio3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/random")
public class RandomNumberController {

    private final RandomNumberService randomService;

    @Autowired
    public RandomNumberController(RandomNumberService randomService) {
        this.randomService = randomService;
    }
    /*
        GET
    */
    @GetMapping("/numbers")
    public ResponseEntity<List<Number>> getRandomNumbers() {
        return ResponseEntity.ok(randomService.getRandomNumbers());
    }
    @GetMapping("/number/{d}")
    public ResponseEntity<Number> getRandomNumber(@PathVariable("d") int length) {
        return ResponseEntity.ok(randomService.getRandomNumber(length));
    }
    /*
        PUT
     */
    @PutMapping("number")
    public ResponseEntity<Number> getRandomNumberOf(Number num){
        return ResponseEntity.ok(randomService.getRandomNumberOf(num));
    }
}
