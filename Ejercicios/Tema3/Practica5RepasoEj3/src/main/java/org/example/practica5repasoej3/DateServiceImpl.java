package org.example.practica5repasoej3;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.*;

@Service
public class DateServiceImpl implements DateService{
    @Override
    public Date getCurrentDate() {
        LocalDate date = LocalDate.ofInstant(Practica5RepasoEj3Application.clock.instant(), ZoneOffset.systemDefault());
        return DateMapper.mapDate(date);
    }

    @Override
    public Date getDateAfterDays(int days) {
        LocalDate date = LocalDate.ofInstant(Practica5RepasoEj3Application.clock.instant(), ZoneOffset.systemDefault());
        return DateMapper.mapDate(date.plusDays(days));
    }

    @Override
    public void changeSystemDate(Date date) {
        LocalDateTime temp = LocalDateTime.now();
        LocalDateTime time = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDay(), temp.getHour(), temp.getMinute());
        Practica5RepasoEj3Application.clock = Clock.fixed(time.toInstant(ZoneOffset.UTC), ZoneOffset.systemDefault());
    }
}
