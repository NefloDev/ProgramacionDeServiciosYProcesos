package org.example.practica5repasoej3;

import java.time.LocalDate;

public class DateMapper {
    public static Date mapDate(LocalDate date){
        return new Date(
                date.getDayOfMonth(),
                date.getMonthValue(),
                date.getYear()
        );
    }
}
