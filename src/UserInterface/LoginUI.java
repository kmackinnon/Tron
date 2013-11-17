/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import Database.User;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author vmorel7
 */
public class LoginUI extends StackPane {

    HBox BtnGroup;
    VBox loginBase;
    int playerNumber;
    TextField usernameInput;
    Label errorLabel;
    PasswordField passwordInput;
    ImageView veiwer;

    public LoginUI(final Stage stage, final ImageView veiwer) {

        //this is the back
        this.veiwer = veiwer;

        //opening the group that contains everything but the background
        VBox base = new VBox();

        //Display Title
        Label titleLabel = new Label("Light Racer");
        titleLabel.setFont(new Font(120));
        titleLabel.setTextFill(Color.WHITE);
        
        // opening the group that contains Player 1 button & Player 2 button
        BtnGroup = new HBox();
        
        //Player 1 button
        Button singlePlayerBtn = new Button("1 Player");
        singlePlayerBtn.setFont(new Font(16));
        singlePlayerBtn.setMinSize(180, 50);
        
        //Player 2 button
        Button multiPlayerBtn = new Button("2 Player");
        multiPlayerBtn.setFont(new Font(16));
        multiPlayerBtn.setMinSize(180, 50);

        //finishing up the button group
        BtnGroup.setAlignment(Pos.CENTER);
        BtnGroup.setSpacing(70);
        BtnGroup.getChildren().addAll(singlePlayerBtn, multiPlayerBtn);

        //opening the group that conatins most of the log in items (textboxs & buttons)
        loginBase = new VBox();

        //the username text box
        usernameInput = new TextField();
        usernameInput.setMaxWidth(200);
        
        //the password text box
        passwordInput = new PasswordField();
        passwordInput.setMaxWidth(200);

        //the label for the username (pretty much just text) 
        Label usernameLabel = new Label("Username");
        usernameLabel.setTextFill(Color.WHITE);
        usernameLabel.setFont(new Font(16));

         //the label for the password (pretty much just text) 
        Label passwordLabel = new Label("Password");
        passwordLabel.setTextFill(Color.WHITE);
        passwordLabel.setFont(new Font(16));

        //opening the group that will contain the login button and the creat account button
        HBox loginBtnGroup = new HBox();
        
        //the login Button
        Button LoginBtn = new Button("Login");
        LoginBtn.setFont(new Font(16));
        LoginBtn.setMinSize(180, 50);
        
        //the create account button
        Button createAccountBtn = new Button("Create Account");
        createAccountBtn.setMinSize(180, 50);
        createAccountBtn.setFont(new Font(16));
        
        //finishing up the login buttons group
        loginBtnGroup.setSpacing(70);
        loginBtnGroup.setAlignment(Pos.CENTER);
        loginBtnGroup.getChildren().addAll(LoginBtn, createAccountBtn);

        //this is the label that will give us warnings, if username is wrong or password doesnt work.
        errorLabel = new Label();
        errorLabel.setTextFill(Color.WHITE);
        errorLabel.setFont(new Font(16));

        //finishing the group that contains most of thge login items  
        loginBase.setAlignment(Pos.CENTER);
        loginBase.getChildren().addAll(usernameLabel, usernameInput,
                passwordLabel, passwordInput,
                loginBtnGroup, errorLabel);
        loginBase.setSpacing(15);
        loginBase.setDisable(true);
        loginBase.setOpacity(0);

        // this is the swicth group this is here so that I would be able to switch one out for the other once I'm done with one set of buttons
        StackPane switchGrp = new StackPane();
        switchGrp.getChildren().addAll(BtnGroup, loginBase);

        //finishing off the groupd that hold everything 
        base.setSpacing(70);
        base.setAlignment(Pos.CENTER);
        base.getChildren().addAll(titleLabel, switchGrp);


        //adds the group that conatins everything and the background 
        getChildren().addAll(veiwer, base);


        // This is the action of when you press the single player button
        // it will switch the group and keep in mind there is only one player that will be logging in
        singlePlayerBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                playerNumber = 1;
                loginBase.setDisable(false);
                BtnGroup.setDisable(true);
                FadeTransition buttonOut =
                        new FadeTransition(Duration.millis(1000), BtnGroup);
                buttonOut.setCycleCount(1);
                buttonOut.setAutoReverse(true);
                buttonOut.setFromValue(1.0);
                buttonOut.setToValue(0.0);

                FadeTransition loginIn =
                        new FadeTransition(Duration.millis(1000), loginBase);
                loginIn.setCycleCount(1);
                loginIn.setAutoReverse(true);
                loginIn.setFromValue(0.0);
                loginIn.setToValue(1.0);

                SequentialTransition seqT = new SequentialTransition(buttonOut, loginIn);
                seqT.play();


                BtnGroup.setDisable(true);

            }
        });

        multiPlayerBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                playerNumber = 2;
                loginBase.setDisable(false);
                BtnGroup.setDisable(true);
                FadeTransition fadeTransitionout =
                        new FadeTransition(Duration.millis(1000), BtnGroup);
                fadeTransitionout.setCycleCount(1);
                fadeTransitionout.setAutoReverse(true);
                fadeTransitionout.setFromValue(1.0);
                fadeTransitionout.setToValue(0.0);

                FadeTransition fadeTransitionin =
                        new FadeTransition(Duration.millis(1000), loginBase);
                fadeTransitionin.setCycleCount(1);
                fadeTransitionin.setAutoReverse(true);
                fadeTransitionin.setFromValue(0.0);
                fadeTransitionin.setToValue(1.0);

                SequentialTransition seqT = new SequentialTransition(fadeTransitionout, fadeTransitionin);
                seqT.play();


                BtnGroup.setDisable(true);

            }
        });



        LoginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                User user = new User(usernameInput.getText(),passwordInput.getText(), false);

                if (user.getSuccess()) {
                    if (playerNumber == 1) {
                        menuUI mainMenu = new menuUI(stage, veiwer);
                        Scene mainMenuscene = new Scene(mainMenu);
                        stage.setScene(mainMenuscene);
                    } else {
                        playerNumber--;
                        usernameInput.clear();
                        passwordInput.clear();
                        errorLabel.setText("Logged in the first player, please enter second player ");
                   }
                } else {
                    passwordInput.clear();
                    errorLabel.setText("Username or password was incorrect");
                }
            }
        });

        createAccountBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {



                User user = new User(usernameInput.getText(), passwordInput.getText(), true);

                if (user.getSuccess()) {
                    if (playerNumber == 1) {
                        menuUI mainMenu = new menuUI(stage, veiwer);
                        Scene mainMenuscene = new Scene(mainMenu);
                        stage.setScene(mainMenuscene);
                    } else {
                        playerNumber--;
                        usernameInput.clear();
                        passwordInput.clear();
                        errorLabel.setText("Created the first player, please enter second player ");

                    }
                } else {
                    passwordInput.clear();
                    errorLabel.setText("That username is already used, please try a different one ");
                }
            }
        });

    }
}
