package org.example.practica5repasoej3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DateController {
    private final DateService dateService;
    @Autowired
    public DateController(DateService dateService) {
        this.dateService = dateService;
    }
    @GetMapping("/date")
    public ResponseEntity<Date> getCurrentDate(){
        return ResponseEntity.ok(dateService.getCurrentDate());
    }
    @GetMapping("/date/{n}")
    public ResponseEntity<Date> getDateAfterDays(@PathVariable("n") int days){
        return ResponseEntity.ok(dateService.getDateAfterDays(days));
    }
    @PostMapping("/date")
    public ResponseEntity changeServerDate(@RequestBody Date date){
        dateService.changeSystemDate(date);
        return ResponseEntity.noContent().build();
    }
}
