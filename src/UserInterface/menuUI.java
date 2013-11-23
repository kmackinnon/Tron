package UserInterface;

import Database.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class menuUI extends StackPane {

    Label desctiptionLabel;
    ImageView veiwer, mapSelect;
    User firstUser, secondUser;
    boolean isSinglePlayer;
    Rectangle colorOne, colorTwo;
    int colorOnePlace, colorTwoPlace;
    final String[] COLORS = {"0xF00", "0xFF0", "0x0F0", "0x0FF", "0x00F", "0xF0F"};
    Image[] maps = new Image[3];
    int mapposition;

    public menuUI(final Stage stage, final ImageView veiwer, User userA, User userB) {

        //might have to change the names coming in.
        if (userA != null) {
            isSinglePlayer = false;
            this.firstUser = userA;
            this.secondUser = userB;
        } else {
            isSinglePlayer = true;
            this.firstUser = userB;
        }

        this.veiwer = veiwer;

        HBox options = new HBox();

        VBox gameInitGrp = new VBox();

        Button playBtn = new Button("Play Game");
        playBtn.setFont(new Font(16));
        playBtn.setMinSize(180, 50);
        if (isSinglePlayer) {
            playBtn.setDisable(true);
        }

        HBox mapGrp = new HBox();

        maps[0] = new Image("/image/map0.png");
        maps[1] = new Image("/image/map1.png");
        maps[2] = new Image("/image/map2.png");

        mapSelect = new ImageView();
        mapSelect.setImage(maps[0]);
        mapSelect.setFitWidth(300);
        mapSelect.setFitHeight(200);
        Button leftMapBtn = new Button("←");
        Button rightMapBtn = new Button("→");

        mapGrp.setAlignment(Pos.CENTER);
        mapGrp.setSpacing(10);
        mapGrp.getChildren().addAll(leftMapBtn, mapSelect, rightMapBtn);


        HBox colorGrp = new HBox();

        Button leftColorOneBtn = new Button("←");
        Button rightColorOneBtn = new Button("→");

        Button leftColorTwoBtn = new Button("←");
        Button rightColorTwoBtn = new Button("→");

        colorOne = new Rectangle();
        colorOne.setWidth(30);
        colorOne.setHeight(30);
        colorOne.setFill(Color.web(COLORS[0]));
        colorOnePlace = 0;


        colorTwo = new Rectangle();
        colorTwo.setWidth(30);
        colorTwo.setHeight(30);
        colorTwo.setFill(Color.web(COLORS[1]));
        colorTwoPlace = 1;

        colorGrp.setAlignment(Pos.CENTER);
        colorGrp.setSpacing(10);
        if (isSinglePlayer) {
            colorGrp.getChildren().addAll(leftColorOneBtn, colorOne, rightColorOneBtn);
        } else {
            colorGrp.getChildren().addAll(leftColorOneBtn, colorOne, rightColorOneBtn, new Label("         "), leftColorTwoBtn, colorTwo, rightColorTwoBtn);
        }


        Label mapLabel = new Label("Select Map");
        mapLabel.setFont(new Font(20));
        mapLabel.setTextFill(Color.WHITE);

        Label colorLabel = new Label();
        colorLabel.setFont(new Font(20));
        colorLabel.setTextFill(Color.WHITE);
        if (isSinglePlayer) {
            colorLabel.setText("Select " + firstUser.getUsername() + "'s color");
        } else {
            colorLabel.setText("Select " + firstUser.getUsername() + "'s color     Select " + secondUser.getUsername() + "'s color ");
        }


        gameInitGrp.setSpacing(40);
        gameInitGrp.setAlignment(Pos.CENTER);
        gameInitGrp.getChildren().addAll(mapLabel, mapGrp, colorLabel, colorGrp, playBtn);
        if (isSinglePlayer) {
            gameInitGrp.setDisable(true);
        }

        VBox statisticsGrpBase = new VBox();
        
        Label statTitleLabel = new Label("Statistics");
        statTitleLabel.setFont(new Font(40));
        statTitleLabel.setTextFill(Color.WHITE);
        
        HBox statisticsGrp = new HBox();
        
        VBox winStats=new VBox();
        winStats.setAlignment(Pos.CENTER);
        winStats.setSpacing(20);
        
        
        Label playerOneStatLabel = new Label(firstUser.getUsername());
        playerOneStatLabel.setFont(new Font(20));
        playerOneStatLabel.setTextFill(Color.WHITE);
        
        Label playerOneStatWinLabel = new Label();
        playerOneStatWinLabel.setFont(new Font(20));
        playerOneStatWinLabel.setTextFill(Color.WHITE);
        
        //playerOneStatWinLabel.setText("Games Played:"+(method to get games played)+" Wins:"+(method to get wins));
        playerOneStatWinLabel.setText("Games Played: 16\n     Wins: 8");
        
        if(isSinglePlayer){
            winStats.getChildren().addAll(playerOneStatLabel,playerOneStatWinLabel);
        }
        else 
        {
        Label playerTwoStatLabel = new Label(secondUser.getUsername());
        playerTwoStatLabel.setFont(new Font(20));
        playerTwoStatLabel.setTextFill(Color.WHITE);
        
         Label playerTwoStatWinLabel = new Label();
        playerTwoStatWinLabel.setFont(new Font(20));
        playerTwoStatWinLabel.setTextFill(Color.WHITE);
        
        //playerTwoStatWinLabel.setText("Games Played:"+(method to get games played)+" Wins:"+(method to get wins));
        playerTwoStatWinLabel.setText("Games Played: 30 \n     Wins:25");
        
        Label heaToHeadLabel = new Label("Head-To-Head");
        heaToHeadLabel.setFont(new Font(20));
        heaToHeadLabel.setTextFill(Color.WHITE);
        
        Label heaToHeadWinLabel = new Label();
        heaToHeadWinLabel.setFont(new Font(20));
        heaToHeadWinLabel.setTextFill(Color.WHITE);
        //heaToHeadWinLabel.setText(firstUser.getUsername()+" wons "+ Method to get the games won vs +" games vs "+ secondUser.getUsername());
        heaToHeadWinLabel.setText(firstUser.getUsername()+" wons 5 games vs "+ secondUser.getUsername());
        
        winStats.getChildren().addAll(playerOneStatLabel,playerOneStatWinLabel,playerTwoStatLabel,playerTwoStatWinLabel,heaToHeadLabel,heaToHeadWinLabel);
        
        }

        
        Label top10Label = new Label();
        top10Label.setFont(new Font(20));
        top10Label.setTextFill(Color.WHITE);
        top10Label.setMinWidth(200);
        
        top10Label.setText("bob  170 \nviviexe 32 \nbill 16 \nsteve 13\nvic 11\njill 9\njen 5\ntest 4\ntest1 2 \ntest3 1");
        //top10Label.setText((call the method that would give me the top 10 players));
        
        statisticsGrp.setAlignment(Pos.CENTER);
        statisticsGrp.setSpacing(30);
        statisticsGrp.setMinHeight(400);
        statisticsGrp.getChildren().addAll(winStats,top10Label);
        
        statisticsGrpBase.setMinWidth(400);
        statisticsGrpBase.setAlignment(Pos.CENTER);
        statisticsGrpBase.getChildren().addAll(statTitleLabel,statisticsGrp);
        


        HBox logoutBtnGrp = new HBox();

        Button logoutplayer1Btn = new Button("Logout " + firstUser.getUsername());
        logoutplayer1Btn.setFont(new Font(16));
        logoutplayer1Btn.setMinSize(140, 50);


        Button player2Btn = new Button();
        player2Btn.setFont(new Font(16));
        player2Btn.setMinSize(140, 50);
        if (isSinglePlayer) {
            player2Btn.setText("2nd Player login");
        } else {
            player2Btn.setText("Logout " + secondUser.getUsername());
        }


        logoutBtnGrp.setSpacing(25);
        logoutBtnGrp.setAlignment(Pos.CENTER);
        logoutBtnGrp.getChildren().addAll(logoutplayer1Btn, player2Btn);



        options.setSpacing(30);
        options.setMinHeight(600);
        options.getChildren().addAll(gameInitGrp, statisticsGrpBase);
        options.setAlignment(Pos.CENTER);

        VBox base = new VBox();
        base.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("Light Racer");
        titleLabel.setFont(new Font(80));
        titleLabel.setTextFill(Color.WHITE);


        base.getChildren().addAll(titleLabel, options, logoutBtnGrp);
        getChildren().addAll(veiwer, base);


        playBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Display Gameplay = new Display(stage, veiwer);
                Scene Gameplayscene = new Scene(Gameplay);
                stage.setScene(Gameplayscene);
            }
        });

        logoutplayer1Btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (isSinglePlayer) {
                    LoginUI startScreen = new LoginUI(stage, veiwer, null);
                    Scene startScreenScene = new Scene(startScreen);
                    stage.setScene(startScreenScene);
                } else {
                    menuUI mainMenu;
                    mainMenu = new menuUI(stage, veiwer, null, secondUser);
                    Scene mainMenuscene = new Scene(mainMenu);
                    stage.setScene(mainMenuscene);
                }
            }
        });

        player2Btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (isSinglePlayer) {
                    LoginUI startScreen = new LoginUI(stage, veiwer, firstUser);
                    Scene startScreenScene = new Scene(startScreen);
                    stage.setScene(startScreenScene);
                } else {
                    menuUI mainMenu;
                    mainMenu = new menuUI(stage, veiwer, null, firstUser);
                    Scene mainMenuscene = new Scene(mainMenu);
                    stage.setScene(mainMenuscene);
                }
            }
        });
        leftColorOneBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (colorOnePlace == 0 || (colorTwoPlace == 0 && colorOnePlace == 1)) {
                    colorOnePlace = COLORS.length;
                }
                if ((colorOnePlace - 1) == colorTwoPlace) {
                    colorOnePlace -= 2;
                } else {
                    colorOnePlace--;
                }
                colorOne.setFill(Color.web(COLORS[colorOnePlace]));
            }
        });
        rightColorOneBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (colorOnePlace == COLORS.length - 1 || (colorTwoPlace == COLORS.length - 1 && colorOnePlace == COLORS.length - 2)) {
                    colorOnePlace = -1;
                }
                if ((colorOnePlace + 1) == colorTwoPlace) {
                    colorOnePlace += 2;
                } else {
                    colorOnePlace++;
                }
                colorOne.setFill(Color.web(COLORS[colorOnePlace]));
            }
        });
        leftColorTwoBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (colorTwoPlace == 0 || (colorOnePlace == 0 && colorTwoPlace == 1)) {
                    colorTwoPlace = COLORS.length;
                }
                if ((colorTwoPlace - 1) == colorOnePlace) {
                    colorTwoPlace -= 2;
                } else {
                    colorTwoPlace--;
                }
                colorTwo.setFill(Color.web(COLORS[colorTwoPlace]));
            }
        });

        rightColorTwoBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (colorTwoPlace == COLORS.length - 1 || (colorOnePlace == COLORS.length - 1 && colorTwoPlace == COLORS.length - 2)) {
                    colorTwoPlace = -1;
                }
                if ((colorTwoPlace + 1) == colorOnePlace) {
                    colorTwoPlace += 2;
                } else {
                    colorTwoPlace++;
                }
                colorTwo.setFill(Color.web(COLORS[colorTwoPlace]));
            }
        });
        leftMapBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (mapposition == 0) {
                    mapposition = maps.length - 1;
                } else {
                    mapposition--;
                }
                mapSelect.setImage(maps[mapposition]);
            }
        });

        rightMapBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (mapposition == maps.length - 1) {
                    mapposition = 0;
                } else {
                    mapposition++;
                }
                mapSelect.setImage(maps[mapposition]);
            }
        });


    }
}