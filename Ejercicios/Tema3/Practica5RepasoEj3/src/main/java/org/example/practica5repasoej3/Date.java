package org.example.practica5repasoej3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Date {
    private int day;
    private int month;
    private int year;

    @Override
    public String toString(){
        return day + "-" + month + "-" + year;
    }
}
