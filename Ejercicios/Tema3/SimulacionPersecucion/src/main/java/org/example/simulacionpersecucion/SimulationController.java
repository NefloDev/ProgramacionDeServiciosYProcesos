package org.example.simulacionpersecucion;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.*;
import java.nio.file.Paths;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationController {

    @FXML
    Circle playerCircle;
    @FXML
    Circle enemyCircle;
    @FXML
    Pane pane;
    @FXML
    VBox vBox;
    @FXML
    Label gameOverText;
    @FXML
    Label movesText;
    @FXML
    Label countDownText;
    @FXML
    Label movesScoreLabel;
    @FXML
    Label highScoreLabel;
    private final int gridSize = 50;
    private final int base = 25;
    private int topWidth;
    private int topHeight;
    private Player player;
    private Enemy enemy;
    private static final int ENEMY_MOVES = 2;
    private final BooleanProperty gameFinished = new SimpleBooleanProperty();
    private int moves;
    private File f;
    private int highScore;

    public void initialize(){
        highScore = 0;
        f = Paths.get(".", "highScore.txt").toFile();
        try{
            if(!f.createNewFile()){
                BufferedReader reader = new BufferedReader(new FileReader(f));
                highScore = Integer.parseInt(reader.readLine());
            }
        }catch (IOException | NumberFormatException ignored){}

        double screenWidth = SimulationApplication.WIDTH;
        double screenHeight = SimulationApplication.HEIGHT;

        player = Player.getInstance();
        enemy = Enemy.getInstance();

        topWidth = (((int)screenWidth/gridSize)*gridSize)-base;
        topHeight = ((int)screenHeight/gridSize)*gridSize-base;

        gameOverText.setPrefSize(screenWidth, screenHeight);
        movesText.setPrefSize(screenWidth, screenHeight);
        countDownText.setPrefSize(screenWidth, screenHeight);

        pane.setPrefSize(screenWidth, screenHeight);
        pane.autosize();
        pane.setStyle("""
                -fx-background-color: #000000,
                        linear-gradient(from 0.0px 0.0px to 50.0px 0.0px, repeat, #222222 5%, transparent 5%),
                        linear-gradient(from 0.0px 0.0px to 0.0px 50.0px, repeat, #222222 5%, transparent 5%)""");

        initializeSimulation();
    }

    private void initializeSimulation(){
        gameFinished.set(false);
        //Player coordinates change move player
        player.getCoordinates().addListener((MapChangeListener<String, Integer>) change -> {
            playerCircle.translateXProperty().set(change.getMap().get("X"));
            playerCircle.translateYProperty().set(change.getMap().get("Y"));
            movesScoreLabel.setText("Score: " + ++moves);
        });
        //Player coordinates change, change enemy coordinates
        player.getCoordinates().addListener((MapChangeListener<String, Integer>) change -> {
            int i = 0;
            while(i < ENEMY_MOVES && !gameFinished.get()){
                moveEnemy(change.getMap().get("X"), change.getMap().get("Y"));
                i++;
            }
            if(enemy.getX() == player.getX() && enemy.getY() == player.getY()){
                gameFinished.set(true);
            }
        });
        //Enemy coordinates change move enemy
        enemy.getCoordinates().addListener((MapChangeListener<String, Integer>) change -> {
            enemyCircle.translateXProperty().set(change.getMap().get("X"));
            enemyCircle.translateYProperty().set(change.getMap().get("Y"));
        });
        gameFinished.addListener((observableValue, aBoolean, t1) -> {
            gameOverText.setVisible(true);
            movesScoreLabel.setVisible(false);
            highScoreLabel.setVisible(false);

            if(moves > highScore){
                movesText.setText("Your score was " + moves + " moves.\nNEW HIGH SCORE!");
                try(FileWriter writer = new FileWriter(f)){
                    writer.write(String.valueOf(moves));
                }catch (IOException ignored){}
            }else{
                movesText.setText("Your score was " + moves + " moves.");
            }
            movesText.setVisible(true);

            showGameOver();
        });

        generateEntities();

        moves = 0;
        movesScoreLabel.setText("Score: " + moves);
        highScoreLabel.setText("Highest score: " + highScore);
    }

    private void showGameOver(){
        AtomicInteger sec = new AtomicInteger(10);
        countDownText.setText("Closing in... " + sec);

        createCustomTimeLine(new KeyFrame(Duration.seconds(1.0),
                evt -> countDownText.setText("Closing in... " + sec.decrementAndGet())));
        createCustomTimeLine(gameOverText);
        createCustomTimeLine(movesText);

        new Thread(() -> {
            try{
                Thread.sleep(10000);
            }catch (InterruptedException ignored){}
            Platform.exit();
        }).start();
    }

    private void createCustomTimeLine(KeyFrame... frames){
        Timeline t = new Timeline(frames);
        t.setCycleCount(Animation.INDEFINITE);
        t.play();
    }

    private void createCustomTimeLine(Control element){
        Timeline t = new Timeline(new KeyFrame(Duration.seconds(1.5), evt -> element.setVisible(true)),
                new KeyFrame(Duration.seconds(0.8), evt -> element.setVisible(false)));
        t.setCycleCount(Animation.INDEFINITE);
        t.play();
    }
    private void generateEntities(){
        int maxWidth = (int)(pane.getWidth()/gridSize);
        int maxHeight = (int)(pane.getHeight()/gridSize);

        int x = generateRandomPosition(maxWidth)*gridSize+base;
        int y = generateRandomPosition(maxHeight)*gridSize+base;

        player.setX(x);
        player.setY(y);

        boolean retry;
        do {
            retry = false;
            if(enemy.getX() == 0){
                x = generateRandomPosition(maxWidth)*gridSize+base;
            }
            if(enemy.getY() == 0){
                y = generateRandomPosition(maxHeight)*gridSize+base;
            }
            if(Math.abs(player.getX() - x) >= gridSize*5){
                enemy.setX(x);
            }else{
                retry = true;
            }
            if(Math.abs(player.getY() - y) >= gridSize*5){
                enemy.setY(y);
            }else{
                retry = true;
            }
        }while (retry);
    }

    public void movePlayer(KeyCode code){
        int yPos = (int)playerCircle.translateYProperty().get();
        int xPos = (int)playerCircle.translateXProperty().get();
        if(!gameFinished.get()){
            switch (code){
                case W: case UP:
                    if(yPos != base){player.setY(yPos-gridSize);}
                    break;
                case S: case DOWN:
                    if(yPos != topHeight){player.setY(yPos+gridSize);}
                    break;
                case A: case LEFT:
                    if(xPos != base){player.setX(xPos-gridSize);}
                    break;
                case D: case RIGHT:
                    if(xPos != topWidth){player.setX(xPos+gridSize);}
                    break;
                default:
            }
        }
    }

    private void moveEnemy(int x, int y){
        if(Math.abs(enemy.getX() - x) < Math.abs(enemy.getY() - y)){
            enemy.setY(y < enemy.getY() ? enemy.getY() - 50 : enemy.getY() + 50);
        }else{
            enemy.setX(x < enemy.getX() ? enemy.getX() - 50 : enemy.getX() + 50);
        }
    }

    private int generateRandomPosition(int max){
        Random r = new Random();
        return r.nextInt(1, max);
    }

}