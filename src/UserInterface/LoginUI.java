/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author vmorel7
 */
public class LoginUI extends VBox {
      
    public LoginUI (final Stage stage,int players){
        Label titleLabel = new Label("Light Racer");
        
        TextField usernameInput = new TextField();
        PasswordField passwordInput =new PasswordField();
        
        Button LoginBtn = new Button("Login");
        Button createaccountBtn = new Button("Create Account");
        
        HBox BtnGroup = new HBox();
        //BtnGroup.setPadding(new Insets(10, 10, 10, 10));
        BtnGroup.setSpacing(30);
        BtnGroup.getChildren().addAll(LoginBtn, createaccountBtn);
        
        getChildren().addAll(titleLabel, new Label("Username:"),usernameInput,
                new Label("Password:"),passwordInput,
                BtnGroup);
        
        LoginBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                
                //Will check if valid or not and display accordingly 
                
                
                Display gameplay = new Display();
                Scene Gameplayscene = new Scene(gameplay);
                stage.setScene(Gameplayscene);
                

            }
        });
    
}
    
}
