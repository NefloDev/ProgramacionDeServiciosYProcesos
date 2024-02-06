package api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Client {
    private int id;
    private String name;
    private String location;
    private String web;
}