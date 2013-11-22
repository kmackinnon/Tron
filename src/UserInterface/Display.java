/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import Gameplay.Map;
import Gameplay.Controller;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Victorio
 */
public class Display extends StackPane {

    final int horizontalGridSize = 75;
    final int verticalGridSize = 50;
    private Rectangle grid[][];
    StackPane gameoverTotal;
    Map baseMap;
    Button startBtn;
    Button restartBtn;
    final Controller controller;
    ImageView Veiwer;
    
    
    
    

    public Display(final Stage stage,ImageView veiwer) {

        this.Veiwer = veiwer;
        
        controller = Controller.getInstance();

        
        grid = new Rectangle[horizontalGridSize][verticalGridSize];
        GridPane gridpanel = new GridPane();
        gridpanel.setAlignment(Pos.CENTER);
        gridpanel.setHgap(0);
        gridpanel.setVgap(0);

        makeSquares(gridpanel);

        VBox gameoverScreen = new VBox();
        Button restartBtn = new Button("Restart");
        Label restartLabel = new Label("Game Over");
        restartLabel.setFont(new Font(26));
        restartLabel.setTextFill(Color.PINK);
        gameoverScreen.setAlignment(Pos.CENTER);

        gameoverScreen.getChildren().addAll(restartLabel, restartBtn);

        Rectangle gameoverBack = new Rectangle();
        gameoverBack.setWidth(200);
        gameoverBack.setHeight(90);
        gameoverBack.setFill(Color.BLACK);

        gameoverTotal = new StackPane();
        gameoverTotal.getChildren().addAll(gameoverBack, gameoverScreen);
        gameoverTotal.setOpacity(0);

        startBtn = new Button("Start Game");


        getChildren().add(veiwer);
        getChildren().add(gridpanel);
        getChildren().add(gameoverTotal);
        getChildren().add(startBtn);
        setOnKeyPressed(controller);
        baseMap = Map.makeDemo(this);
        
        
        /*for (int i = 15; i < 25; i++) {
            for (int j = 20; j < 30; j++) {
               displayWall(i, j, "0x000");
            }
        }
        
         for (int i = 50; i < 60; i++) {
            for (int j = 20; j < 30; j++) {
               displayWall(i, j, "0x000");
            }
        }*/
       /* for (int i = 30; i < 45; i++) {
            for (int j = 20; j < 30; j++) {
               displayWall(i, j, "0x000");
            }
        }
        
         for (int i = 5; i < 25; i++) {
            for (int j = 25; j < 45; j++) {
               displayWall(i, j, "0x000");
            }
        }
        
       for (int i = 50; i < 70; i++) {
            for (int j = 5; j < 25; j++) {
               displayWall(i, j, "0x000");
            }
        }*/
       
        
        


        //This is for the restart button when it is clicked, it will clear the map and fadeaway the gameover screen
        restartBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                FadeTransition fadeTransition =
                        new FadeTransition(Duration.millis(2000), gameoverTotal);
                fadeTransition.setCycleCount(1);
                fadeTransition.setAutoReverse(true);
                fadeTransition.setFromValue(1.0);
                fadeTransition.setToValue(0.0);
                fadeTransition.play();
                setDisable(true);

                //baseMap = Map.makeDemo(this);
                clearGrid();
                
                Display Gameplay = new Display(stage, Veiwer);
                Scene Gameplayscene = new Scene(Gameplay);
                stage.setScene(Gameplayscene);
            }
        });

        
        // The start button will 
        startBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                getChildren().remove(startBtn);
                baseMap.run();
            }
        });
    }

    public void makeSquares(GridPane panel) {

        for (int i = 0; i < horizontalGridSize; i++) {
            for (int j = 0; j < verticalGridSize; j++) {
                grid[i][j] = new Rectangle();
                grid[i][j].setWidth(10);
                grid[i][j].setHeight(10);
                grid[i][j].setFill(Color.GREY);
                panel.add(grid[i][j], i, j);
            }
        }
    }

    public void clearGrid() {
        for (int i = 0; i < horizontalGridSize; i++) {
            for (int j = 0; j < verticalGridSize; j++) {
                grid[i][j].setFill(Color.GREY);
            }
        }
    }
    
    public void displayMultipleWalls(int xposStart, int yposStart,int xposEnd, int yposEnd, String color)
    {
         for (int i = xposStart; i < xposEnd; i++) {
            for (int j = yposStart; j < yposEnd; j++) {
                grid[i][j].setFill(Color.BLACK);
            }
        }
    }

    public void displayWall(int xpos, int ypos, String color) {
        grid[xpos][ypos].setFill(Color.web(color));
    }

    public void gameover() {

        FadeTransition fadeTransition =
                new FadeTransition(Duration.millis(2000), gameoverTotal);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(true);

        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();

    }
}
