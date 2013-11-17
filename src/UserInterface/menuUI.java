package UserInterface;

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
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class menuUI extends StackPane{
    
    Label desctiptionLabel;
    ImageView veiwer;

  public menuUI(final Stage stage,final ImageView veiwer) {
      
       
      this.veiwer=veiwer;
      
      HBox base = new HBox();
      
      VBox mainBtnGrp = new VBox();
      
      Button playBtn = new Button("Play Game");
      playBtn.setFont(new Font(16));
      playBtn.setMinSize(180, 50);

      Button statsBtn = new Button("Check Stats");
      statsBtn.setFont(new Font(16));
      statsBtn.setMinSize(180, 50);
      
      Button optionsBtn = new Button("Options");
      optionsBtn.setFont(new Font(16));
      optionsBtn.setMinSize(180, 50);
      
      mainBtnGrp.setSpacing(80);
      mainBtnGrp.setAlignment(Pos.CENTER);
      mainBtnGrp.getChildren().addAll(playBtn,statsBtn,optionsBtn);
      
      VBox restMainGrp = new VBox();
      desctiptionLabel = new Label();
      desctiptionLabel.setMinSize(400, 500);
      desctiptionLabel.setTextFill(Color.WHITE);
      desctiptionLabel.setFont(new Font(20));
      desctiptionLabel.setText("Go over each button to see what they do!");
      
      HBox logoutBtnGrp = new HBox();
      
      Button logoutplayer1Btn = new Button("Logout Player 1");
      logoutplayer1Btn.setFont(new Font(16));
      logoutplayer1Btn.setMinSize(140, 50);

      //TODO : check if 2 players if not then get rid of this 
    
      Button logoutplayer2Btn = new Button("Logout Player 2");
      logoutplayer2Btn.setFont(new Font(16));
      logoutplayer2Btn.setMinSize(140, 50);
      
      //TODO get rid of this somehow
      Label empty= new Label("                              ");
      
      logoutBtnGrp.setSpacing(25);
      logoutBtnGrp.getChildren().addAll(empty,logoutplayer1Btn,logoutplayer2Btn);
      
      restMainGrp.setAlignment(Pos.CENTER);
      restMainGrp.getChildren().addAll(desctiptionLabel,logoutBtnGrp);
              
      base.setSpacing(50);
      base.getChildren().addAll(mainBtnGrp,restMainGrp);
      base.setAlignment(Pos.CENTER);
      
      VBox title = new VBox();
      title.setAlignment(Pos.CENTER);
      Label titleLabel = new Label("Light Racer");
      titleLabel.setFont(new Font(80));
      titleLabel.setTextFill(Color.WHITE);
      
      
      title.getChildren().addAll(titleLabel,base);
      getChildren().addAll(veiwer,title);
      
      
      playBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                Display Gameplay = new Display(stage, veiwer);
                    Scene Gameplayscene = new Scene(Gameplay);
                    stage.setScene(Gameplayscene);
            }
        });
       statsBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                StatisticsUI Statistics = new StatisticsUI(stage, veiwer);
                    Scene Statisticsscene = new Scene(Statistics);
                    stage.setScene(Statisticsscene);
            }
        });
      
      
      
      
      playBtn.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        desctiptionLabel.setText("Play Light Racer \n - trick your friend into a wall to win! \n - but make sure he doesnt get you first !!");
                    };});
        
      statsBtn.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        desctiptionLabel.setText("Check statistics \n - how much you winor lose \n - the top 10 players in the game \n see head-to-head stats");
                    };});

      optionsBtn.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        desctiptionLabel.setText("Change Options");
                    };});
      
     logoutplayer1Btn.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        desctiptionLabel.setText("Logs outs player 1" );//change this to username
                    };});
 
     logoutplayer2Btn.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        desctiptionLabel.setText("Logs outs player 2" );//change this to username
                    };});
  }

}