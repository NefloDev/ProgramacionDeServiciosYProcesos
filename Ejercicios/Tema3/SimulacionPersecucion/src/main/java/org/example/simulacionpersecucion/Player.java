package org.example.simulacionpersecucion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Player{
    private static Player player;
    private ObservableMap<String, Integer> coordinates;
    private Player(){
        coordinates = FXCollections.observableHashMap();
        coordinates.put("X", 0);
        coordinates.put("Y", 0);
    }

    public ObservableMap<String, Integer> getCoordinates() {
        return coordinates;
    }

    public int getX(){
        return coordinates.get("X");
    }

    public int getY(){
        return coordinates.get("Y");
    }

    public void setY(int posY){
        coordinates.put("Y", posY);
    }

    public void setX(int posX){
        coordinates.put("X", posX);
    }

    public static Player getInstance() {
        if(player == null){
            player = new Player();
        }
        return player;
    }
}
