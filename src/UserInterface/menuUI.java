package UserInterface;

import Database.DBMapLoader;
import Database.GameInfo;
import Database.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author vmorel7
 */
public class menuUI extends StackPane {

    Label desctiptionLabel, speedLabel;
    ImageView veiwer, mapSelect;
    User firstUser, secondUser;
    boolean isSinglePlayer;
    Rectangle colorOne, colorTwo;
    int colorOnePlace, colorTwoPlace,mapposition,speed=10;
    final String[] COLORS = {"0xF00", "0xFF0", "0x0F0", "0x0FF", "0x00F", "0xF0F"};
    Image[] maps = new Image[3];
    
    /**
     * This is the class that consists of the panel - main menu.
     * 
     * @param stage 
     * @param veiwer
     * @param userA this will be null if one the loginUI, the player picked 1 player, or else this represents the first player
     * @param userB this will never be null, will be the first player if single player or else it would be the second player
     */

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
       
        HBox speedStartGrp=new HBox();
        

        Button leftSpeedBtn = new Button("←");
        Button rightSpeedBtn = new Button("→");
        
        speedLabel= new Label("  Map Speed : "+String.valueOf(speed));
        speedLabel.setFont(new Font(20));
        speedLabel.setTextFill(Color.WHITE);
        speedLabel.setMinWidth(165);
        
        
        speedStartGrp.setAlignment(Pos.CENTER);
        speedStartGrp.getChildren().addAll(leftSpeedBtn,speedLabel,rightSpeedBtn,new Label("      "),playBtn);
        
        gameInitGrp.setSpacing(40);
        gameInitGrp.setAlignment(Pos.CENTER);
        gameInitGrp.getChildren().addAll(mapLabel, mapGrp, colorLabel, colorGrp, speedStartGrp);
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
        winStats.setSpacing(10);
        
        
        Label playerOneStatLabel = new Label(firstUser.getUsername());
        playerOneStatLabel.setFont(new Font(26));
        playerOneStatLabel.setTextFill(Color.WHITE);
        
        Label playerOneStatWinLabel = new Label();
        playerOneStatWinLabel.setFont(new Font(20));
        playerOneStatWinLabel.setTextFill(Color.WHITE);
        
        playerOneStatWinLabel.setText("Games Played: "+firstUser.getStats().getGames()+"\n        Wins: "+firstUser.getStats().getWins());
        
        if(isSinglePlayer){
            winStats.getChildren().addAll(playerOneStatLabel,playerOneStatWinLabel);
        }
        else 
        {
        Label playerTwoStatLabel = new Label(secondUser.getUsername());
        playerTwoStatLabel.setFont(new Font(26));
        playerTwoStatLabel.setTextFill(Color.WHITE);
        
         Label playerTwoStatWinLabel = new Label();
        playerTwoStatWinLabel.setFont(new Font(20));
        playerTwoStatWinLabel.setTextFill(Color.WHITE);
        
        playerTwoStatWinLabel.setText("Games Played: "+secondUser.getStats().getGames()+"\n        Wins: "+secondUser.getStats().getWins());
        
        Label headToHeadLabel = new Label("Head-To-Head");
        headToHeadLabel.setFont(new Font(26));
        headToHeadLabel.setTextFill(Color.WHITE);
        
        Label heaToHeadWinLabel = new Label();
        heaToHeadWinLabel.setFont(new Font(20));
        heaToHeadWinLabel.setTextFill(Color.WHITE);
        //heaToHeadWinLabel.setText(firstUser.getUsername()+" wons "+ Method to get the games won vs +" games vs "+ secondUser.getUsername());
        heaToHeadWinLabel.setText(firstUser.getUsername()+" vs "+ secondUser.getUsername()+"\n         4-2");
        
        winStats.getChildren().addAll(playerOneStatLabel,playerOneStatWinLabel,playerTwoStatLabel,playerTwoStatWinLabel,headToHeadLabel,heaToHeadWinLabel);
        
        }

        VBox top10Grp =new VBox();
        top10Grp.setMinWidth(200);
        
        String top10[][] =User.getTopTen();
        
        Label top10Label = new Label();
        top10Label.setFont(new Font(26));
        top10Label.setTextFill(Color.WHITE);
        top10Label.setText("Top 10 Players");
        
        top10Grp.getChildren().add(top10Label);
        
        HBox top10rows[] =new HBox[10];
        Label top10names[] =new Label[10];
        Label top10scores[] =new Label[10];
        
        for(int i = 0; i < 10;i++)
        {
            top10names[i]=new Label(top10[0][i]);
            top10names[i].setFont(new Font(20));
            top10names[i].setTextFill(Color.WHITE);
            
            top10scores[i]=new Label(top10[1][i]);
            top10scores[i].setFont(new Font(20));
            top10scores[i].setTextFill(Color.WHITE);
            
            top10rows[i]=new HBox();
            top10rows[i].setSpacing(15);
            top10rows[i].getChildren().addAll(top10names[i],top10scores[i]);
            top10rows[i].setAlignment(Pos.CENTER);
            
            top10Grp.getChildren().add(top10rows[i]);
        }
        
        
        statisticsGrp.setAlignment(Pos.CENTER);
        statisticsGrp.setSpacing(30);
        statisticsGrp.setMinHeight(400);
        statisticsGrp.getChildren().addAll(winStats,top10Grp);
        
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
                
               DBMapLoader map =new DBMapLoader("BasicMap"+String.valueOf(mapposition+1));
               
               GameInfo gameInfo =new GameInfo(firstUser,COLORS[colorOnePlace],secondUser,COLORS[colorTwoPlace],speed,map);
               
                
                
               Display Gameplay = new Display(stage, veiwer,gameInfo);
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
        leftSpeedBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if(speed!=5)
                speed--;
                 speedLabel.setText("  Map Speed : "+String.valueOf(speed));
            }
        });

        rightSpeedBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if(speed!=20)
                speed++;
                speedLabel.setText("  Map Speed : "+String.valueOf(speed));
            }
        });


    }
}