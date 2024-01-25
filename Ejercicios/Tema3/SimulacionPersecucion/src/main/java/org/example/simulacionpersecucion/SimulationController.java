package org.example.simulacionpersecucion;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.media.*;
import javafx.util.Duration;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationController {

    @FXML
    Pane pane;
    @FXML
    VBox vBox;
    private Label gameOverText;
    private Label movesText;
    private Label countDownText;
    private Label movesScoreLabel;
    private Label highScoreLabel;
    private Circle playerCircle;
    private List<Circle> enemiesCircles;
    private final int gridSize = 50;
    private final int base = 25;
    private int topWidth;
    private int topHeight;
    private Player player;
    private List<Enemy> enemies;
    private final Image img = new Image("https://i.kym-cdn.com/photos/images/original/002/422/058/391.jpg");
    private final BooleanProperty gameFinished = new SimpleBooleanProperty();
    private int moves;
    private File f;
    private int highScore;
    private static final int ENEMY_MOVES = 2;
    public static final int AMOUNT_ENEMIES = 3;

    public void initialize(){
        createElements();
        initializeSimulation();
    }
    private void createElements(){
        setAllCircles();
        setAllLabels();
        setHighScore();

        double screenWidth = SimulationApplication.WIDTH;
        double screenHeight = SimulationApplication.HEIGHT;
        topWidth = (((int)screenWidth/gridSize)*gridSize)-base;
        topHeight = ((int)screenHeight/gridSize)*gridSize-base;

        //Set labels size
        gameOverText.setPrefSize(screenWidth, screenHeight);
        movesText.setPrefSize(screenWidth, screenHeight);
        countDownText.setPrefSize(screenWidth, screenHeight);

        //Set grid
        pane.setPrefSize(screenWidth, screenHeight);
        pane.autosize();
        pane.setStyle("""
                -fx-background-color: #000000,
                        linear-gradient(from 0.0px 0.0px to 50.0px 0.0px, repeat, #222222 5%, transparent 5%),
                        linear-gradient(from 0.0px 0.0px to 0.0px 50.0px, repeat, #222222 5%, transparent 5%)""");
    }
    private void setAllCircles(){
        player = Player.getInstance();

        enemiesCircles = new ArrayList<>();
        enemies = new ArrayList<>();

        playerCircle = setCircle("blue", null);
        pane.getChildren().add(playerCircle);

        for (int i = 0; i < AMOUNT_ENEMIES; i++) {
            enemies.add(new Enemy(i));

            enemiesCircles.add(setCircle(null, new ImagePattern(img)));
        }
        pane.getChildren().addAll(enemiesCircles);
    }
    private Circle setCircle(String color, ImagePattern image){
        Circle temp = new Circle();
        temp.setFill(image != null ? image : Paint.valueOf(color));
        temp.setRadius(25);
        return temp;
    }
    private void setAllLabels(){
        gameOverText = setLabel("GAME OVER", 140, false, "");
        movesText = setLabel("", 40, false, "220 0 0 0");
        countDownText = setLabel("", 30, false, "380 0 0 0");
        movesScoreLabel = setLabel("", 30, true, "80 50 50 50");
        highScoreLabel = setLabel("", 30, true, "50");

        pane.getChildren().add(gameOverText);
        pane.getChildren().add(movesText);
        pane.getChildren().add(countDownText);
        pane.getChildren().add(movesScoreLabel);
        pane.getChildren().add(highScoreLabel);
    }
    private Label setLabel(String text, double size, boolean visible, String padding){
        Label temp = new Label();
        temp.setText(text);
        temp.setAlignment(Pos.CENTER);
        temp.setFont(Font.font(size));
        temp.setStyle("-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: " + (!padding.isEmpty() ? padding: "0") + ";");
        temp.setVisible(visible);
        return temp;
    }
    private void setHighScore(){
        f = Paths.get(".", "highScore.txt").toFile();
        try{
            if(!f.createNewFile()){
                BufferedReader reader = new BufferedReader(new FileReader(f));
                highScore = Integer.parseInt(reader.readLine());
            }
        }catch (IOException | NumberFormatException ignored){
            highScore = 0;
        }
    }
    private void initializeSimulation(){
        gameFinished.set(false);

        initializeListeners();
        generateEntities();

        moves = 0;
        movesScoreLabel.setText("Score: " + moves);
        highScoreLabel.setText("Highest score: " + highScore);
    }
    private void initializeListeners(){
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
            if(enemyHasCollidedWithPlayer()){
                gameFinished.set(true);
            }
        });
        //Enemy coordinates change move enemy
        enemies.forEach(e -> e.getCoordinates().addListener((MapChangeListener<String, Integer>) change -> {
            enemiesCircles.get(e.getId()).translateXProperty().set(change.getMap().get("X"));
            enemiesCircles.get(e.getId()).translateYProperty().set(change.getMap().get("Y"));
        }));

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
    }
    private boolean enemyHasCollidedWithPlayer(){
        boolean collide = false;
        int i = 0;
        while(!collide && i < enemies.size()){
            collide =
                    enemies.get(i).getX() == player.getX() &&
                            enemies.get(i).getY() == player.getY();
            i++;
        }
        return collide;
    }
    private void showGameOver(){
        //TODO - Descomentar para unas risas

        /*ImageView imgV = new ImageView(img);
        imgV.setFitWidth(pane.getWidth());
        imgV.setFitHeight(pane.getHeight());
        pane.getChildren().add(imgV);
        Media audio = new Media(new File("./audio/blue-lobster-meme.mp3").toURI().toString());
        MediaPlayer mp = new MediaPlayer(audio);
        mp.setOnEndOfMedia(Platform::exit);
        mp.play();*/

        AtomicInteger sec = new AtomicInteger(10);
        countDownText.setText("Closing in... " + sec);
        countDownText.setVisible(true);

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

        enemies.forEach(e -> e.setCoordinates(0, 0));
        int radius = gridSize*5;

        for(int i = 0; i < enemies.size(); i++){
            boolean retry;
            do {
                retry = false;
                if(enemies.get(i).getX() == 0){
                    x = generateRandomPosition(maxWidth)*gridSize+base;
                }
                if(enemies.get(i).getY() == 0){
                    y = generateRandomPosition(maxHeight)*gridSize+base;
                }
                if(enemyNotOverlaps(i, x, y)){
                    if(Math.abs(player.getY() - y) >= radius || Math.abs(player.getX() - x) >= radius){
                        enemies.get(i).setCoordinates(x, y);
                    }else{
                        retry = true;
                    }
                }else{
                    retry = true;
                }
            }while (retry);
        }
    }
    private int generateRandomPosition(int max){
        Random r = new Random();
        return r.nextInt(1, max);
    }
    private boolean enemyNotOverlaps(int pos, int xPos, int yPos){
        boolean overlap = false;
        int i = 0;
        while (!overlap && i < enemies.size()){
            if(i == pos){
                i++;
            }else{
                overlap = enemies.get(i).getX() == xPos &&
                        enemies.get(i).getY() == yPos;
            }
            i++;
        }
        return !overlap;
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
        if(!enemyHasCollidedWithPlayer()){

            for (Enemy enemy : enemies){

                if(Math.abs(enemy.getX() - x) < Math.abs(enemy.getY() - y) &&
                        enemyNotOverlaps(enemy.getId(), enemy.getX(), enemy.getY() - 50) &&
                        enemyNotOverlaps(enemy.getId(), enemy.getX(), enemy.getY() + 50)){

                    enemy.setY(y < enemy.getY() ? enemy.getY() - 50 : enemy.getY() + 50);
                }else{
                    if(enemyNotOverlaps(enemy.getId(), enemy.getX() - 50, enemy.getY()) &&
                            enemyNotOverlaps(enemy.getId(), enemy.getX() + 50, enemy.getY())){

                        enemy.setX(x < enemy.getX() ? enemy.getX() - 50 : enemy.getX() + 50);
                    }else{
                        enemy.setY(y < enemy.getY() ? enemy.getY() - 50 : enemy.getY() + 50);
                    }
                }
            }
        }
    }

}