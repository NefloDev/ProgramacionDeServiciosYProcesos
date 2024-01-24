package org.example.simulacionpersecucion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Enemy {
    private static Enemy enemy;
    private ObservableMap<String, Integer> coordinates;
    private Enemy(){
        coordinates = FXCollections.observableHashMap();
        coordinates.put("X",0);
        coordinates.put("Y",0);
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

    public static Enemy getInstance() {
        if(enemy == null){
            enemy = new Enemy();
        }
        return enemy;
    }
}
