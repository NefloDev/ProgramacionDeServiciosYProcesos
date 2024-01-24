package org.example.simulacionpersecucion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulationApplication extends Application {

    public static double WIDTH = 1600;
    public static double HEIGHT = 900;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SimulationApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle("Persecution Simulator");
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setScene(scene);
        stage.show();
        SimulationController controller = fxmlLoader.getController();
        scene.setOnKeyPressed(e -> {
            KeyCode code = e.getCode();
            if(code == KeyCode.W || code == KeyCode.S || code == KeyCode.D || code == KeyCode.A ||
            code == KeyCode.UP || code == KeyCode.DOWN || code == KeyCode.RIGHT || code == KeyCode.LEFT){
                controller.movePlayer(code);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}