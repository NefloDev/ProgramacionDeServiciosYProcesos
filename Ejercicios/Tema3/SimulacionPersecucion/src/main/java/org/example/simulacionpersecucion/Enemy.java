package org.example.simulacionpersecucion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Enemy {
    private ObservableMap<String, Integer> coordinates;
    private int id;
    public Enemy(int id){
        coordinates = FXCollections.observableHashMap();
        coordinates.put("X",0);
        coordinates.put("Y",0);
        this.id  = id;
    }

    public ObservableMap<String, Integer> getCoordinates() {
        return coordinates;
    }

    public int getX() {
        return coordinates.get("X");
    }

    public int getY() {
        return coordinates.get("Y");
    }

    public void setX(int xPos) {
        coordinates.put("X", xPos);
    }

    public void setY(int yPos) {
        coordinates.put("Y", yPos);
    }

    public void setCoordinates(int xPos, int yPos){
        coordinates.put("X", xPos);
        coordinates.put("Y", yPos);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
