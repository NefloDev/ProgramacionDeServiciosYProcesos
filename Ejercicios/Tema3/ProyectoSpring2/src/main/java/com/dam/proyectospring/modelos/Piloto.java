package com.dam.proyectospring.modelos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("pilots")
public class Piloto implements Serializable {
    @Id
    @Field("_id")
    private String _id;
    @Field("driver")
    private String driver;
    @Field("abbreviation")
    private String abbreviation;
    @Field("no")
    private int no;
    @Field("team")
    private String team;
    @Field("country")
    private String country;
    @Field("date_of_birth")
    private LocalDate date_of_birth;
}
