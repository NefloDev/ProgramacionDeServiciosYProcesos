package org.example.practica5repasoej3;

public interface DateService {
    Date getCurrentDate();
    Date getDateAfterDays(int days);
    void changeSystemDate(Date date);
}
