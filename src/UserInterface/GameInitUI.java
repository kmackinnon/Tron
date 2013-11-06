/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author vmorel7
 */
public class GameInitUI extends VBox {

    public GameInitUI(final Stage stage) {
        
        Label titleLabel = new Label("Light Racer");
        titleLabel.setMinSize(50,50);
        Button singlePlayerBtn = new Button("1 Player");
        Button multiPlayerBtn = new Button("2 Player");
        
        HBox BtnGroup = new HBox();
        //BtnGroup.setPadding(new Insets(10, 10, 10, 10));
        BtnGroup.setSpacing(30);
        BtnGroup.getChildren().addAll(singlePlayerBtn, multiPlayerBtn);
         
        setSpacing(70);
        getChildren().addAll(titleLabel, BtnGroup);
        
        
        
        singlePlayerBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                LoginUI login = new LoginUI(stage,1);
                Scene Loginscene = new Scene(login);
                stage.setScene(Loginscene);
                

            }
        });
        
        multiPlayerBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                LoginUI login = new LoginUI(stage,2);
                Scene Loginscene = new Scene(login);
                stage.setScene(Loginscene);
                

            }
        });
        
    }
}
