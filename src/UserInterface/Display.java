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

    final int horizontalGridSize = 50;
    final int verticalGridSize = 50;
    private Rectangle grid[][];
    StackPane gameoverTotal;
    Map baseMap;
    Button startBtn;
    Button restartBtn;
    final Controller controller;

    public Display(final Stage stage,ImageView veiwer) {



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
            }
        });

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
