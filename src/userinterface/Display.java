package userinterface;

import database.GameInfo;
import gameplay.Controller;

import javafx.animation.FadeTransition;
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
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Sets up the grid and displays the entire frontend of the project
 * @author Victorio
 */
public class Display extends StackPane {

    final int horizontalGridSize = 75;
    final int verticalGridSize = 50;
    private Rectangle grid[][];
    StackPane gameoverTotal, roundoverTotal;

    Button startBtn, restartBtn, menuBtn, roundBtn;
    GameInfo GameInfo;
    final Controller controller;
    Label controllerLabel, roundLabel, EndLabel;
    ImageView Viewer;
    VBox gameoverScreen, roundoverScreen;
    HBox buttonGrp;

    int player1point, player2point;
    boolean endGame;
    StackPane switchGrp;

    /**
     * Display constructor which sets up everything the user sees.
     * @param stage
     * @param viewer
     * @param gameInfo all info necessary for a game
     */
    public Display(final Stage stage, ImageView viewer, GameInfo gameInfo) {

        endGame = false;

        GameInfo = gameInfo;

        GameInfo.setDisplay(this);

        this.Viewer = viewer;

        controller = Controller.getInstance();

        grid = new Rectangle[horizontalGridSize][verticalGridSize];
        GridPane gridpanel = new GridPane();
        gridpanel.setAlignment(Pos.CENTER);
        gridpanel.setHgap(0);
        gridpanel.setVgap(0);

        makeSquares(gridpanel);

        gameoverScreen = new VBox();

        buttonGrp = new HBox();
        buttonGrp.setAlignment(Pos.CENTER);
        restartBtn = new Button("Restart");
        menuBtn = new Button("Main Menu");
        buttonGrp.getChildren().addAll(restartBtn, menuBtn);

        EndLabel = new Label("Game Over ");
        EndLabel.setFont(new Font(26));
        EndLabel.setTextFill(Color.PINK);
        gameoverScreen.setAlignment(Pos.CENTER);

        Label warningLabel = new Label("You must double click");
        warningLabel.setFont(new Font(14));
        warningLabel.setTextFill(Color.PINK);

        gameoverScreen.getChildren().addAll(EndLabel, warningLabel, buttonGrp);
        gameoverScreen.setAlignment(Pos.CENTER);

        roundoverScreen = new VBox();

        roundLabel = new Label("Round Over");
        roundLabel.setFont(new Font(26));
        roundLabel.setTextFill(Color.PINK);

        roundBtn = new Button("Next Round");
        roundBtn.setMinWidth(120);

        roundoverScreen.getChildren().addAll(roundLabel, roundBtn);
        roundoverScreen.setSpacing(20);
        roundoverScreen.setAlignment(Pos.CENTER);

        Rectangle gameoverBack = new Rectangle();
        gameoverBack.setWidth(200);
        gameoverBack.setHeight(90);
        gameoverBack.setFill(Color.BLACK);

        Rectangle roundoverBack = new Rectangle();
        roundoverBack.setWidth(200);
        roundoverBack.setHeight(90);
        roundoverBack.setFill(Color.BLACK);

        gameoverTotal = new StackPane();
        gameoverTotal.getChildren().addAll(gameoverBack, gameoverScreen);
        gameoverTotal.setOpacity(0);

        roundoverTotal = new StackPane();
        roundoverTotal.getChildren().addAll(roundoverBack, roundoverScreen);
        roundoverTotal.setOpacity(0);

        startBtn = new Button("Start Game");

        VBox mainGamePlay = new VBox();

        Label nameLabel = new Label(gameInfo.getPlayerName(0) + "                                               " + gameInfo.getPlayerName(1));
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setFont(new Font(20));
        controllerLabel = new Label("up: W, left: A, down: S, right: D      up: I, left: J, down: K, right: L");
        controllerLabel.setTextFill(Color.WHITE);
        controllerLabel.setFont(new Font(20));
        roundLabel = new Label("Rounds Won : 0                                       Rounds Won : 0");
        roundLabel.setTextFill(Color.WHITE);
        roundLabel.setFont(new Font(20));
        roundLabel.setOpacity(0);

        mainGamePlay.getChildren().addAll(gridpanel, nameLabel, controllerLabel, roundLabel);
        mainGamePlay.setAlignment(Pos.CENTER);

        switchGrp = new StackPane();
        switchGrp.getChildren().addAll(gameoverTotal, roundoverTotal);

        getChildren().add(viewer);
        getChildren().add(mainGamePlay);
        getChildren().add(switchGrp);
        getChildren().add(startBtn);
        setOnKeyPressed(controller);

        startBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            /**
             * The start button appears and the round will begin
             */
            public void handle(ActionEvent e) {
                getChildren().remove(startBtn);

                FadeTransition inFadeTransition
                        = new FadeTransition(Duration.millis(2000), controllerLabel);
                inFadeTransition.setCycleCount(1);
                inFadeTransition.setAutoReverse(true);
                inFadeTransition.setFromValue(1.0);
                inFadeTransition.setToValue(0.0);
                inFadeTransition.play();

                FadeTransition outFadeTransition
                        = new FadeTransition(Duration.millis(2000), roundLabel);
                outFadeTransition.setCycleCount(1);
                outFadeTransition.setAutoReverse(true);
                outFadeTransition.setFromValue(0.0);
                outFadeTransition.setToValue(1.0);
                outFadeTransition.play();

                GameInfo.startRound();
            }
        });

        restartBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            /**
             * When the restart button is clicked, it will clear the map and gameover screen fades away
             */
            public void handle(ActionEvent e) {

                GameInfo newGameInfo = new GameInfo(GameInfo.getFirstUser(), GameInfo.getFirstUserColour(),
                        GameInfo.getSecondUser(), GameInfo.getSecondUserColour(), GameInfo.getSpeed(), GameInfo.getMap());

                Display Gameplay = new Display(stage, Viewer, newGameInfo);
                Scene Gameplayscene = new Scene(Gameplay);
                stage.setScene(Gameplayscene);
            }
        });

        menuBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            /**
             * Menu button allows user to get back to main menu.
             */
            public void handle(ActionEvent e) {
                MenuUI mainMenu = new MenuUI(stage, Viewer, GameInfo.getFirstUser(), GameInfo.getSecondUser());
                Scene Menuscene = new Scene(mainMenu);
                stage.setScene(Menuscene);

            }
        });

        // Update the player scores on round button click
        roundBtn.setOnAction(new EventHandler<ActionEvent>() {            
            @Override
            public void handle(ActionEvent e) {
                if (endGame) {
                    switchGrp.getChildren().remove(roundoverTotal);
                } else {
                    roundLabel.setText("Rounds Won : " + player1point + "                                       Rounds Won : " + player2point);
                    clearGrid();
                    roundoverTotal.setOpacity(0);
                    GameInfo.startRound();
                }
            }
        });

    }

    /**
     * Sets a grey rectangle in each index of the grid to display the map.
     * @param panel 
     */
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

    /**
     * Resets the map by making everything grey again.
     */
    public void clearGrid() {
        for (int i = 0; i < horizontalGridSize; i++) {
            for (int j = 0; j < verticalGridSize; j++) {
                grid[i][j].setFill(Color.GREY);
            }
        }
    }

    /**
     * Fill the grid from start to end of coordinates passed.
     * @param xposStart
     * @param yposStart
     * @param xposEnd
     * @param yposEnd
     * @param color 
     */
    public void displayMultipleWalls(int xposStart, int yposStart, int xposEnd, int yposEnd, String color) {
        for (int i = xposStart; i < xposEnd; i++) {
            for (int j = yposStart; j < yposEnd; j++) {
                grid[i][j].setFill(Color.BLACK);
            }
        }
    }

    /**
     * Displays a wall with a given color at various positions.
     * @param xpos
     * @param ypos
     * @param color 
     */
    public void displayWall(int xpos, int ypos, String color) {
        grid[xpos][ypos].setFill(Color.web(color));
    }

    /**
     * Displays the roundover screen and updates each player's points.
     * @param winner
     * @param player1win
     * @param player2win 
     */
    public void roundover(String winner, int player1win, int player2win) {

        FadeTransition fadeTransition
                = new FadeTransition(Duration.millis(500), roundoverTotal);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();

        player1point = player1win;
        player2point = player2win;

    }

    /**
     * Displays the gameover screen.
     * @param winner 
     */
    public void gameover(String winner) {

        endGame = true;

        FadeTransition fadeTransition
                = new FadeTransition(Duration.millis(500), gameoverTotal);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();

        this.getChildren().remove(roundoverScreen);

    }
}
