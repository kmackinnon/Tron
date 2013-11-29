package userinterface;

import database.User;

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
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class consists of the Login panel from which users may login to play.
 *
 * @author Victorio
 */
public class LoginUI extends StackPane {

    HBox BtnGroup;
    VBox loginBase;
    int playerNumber;
    TextField usernameInput;
    Label errorLabel;
    PasswordField passwordInput;
    ImageView viewer;
    User user;

    /**
     *
     * The constructor which instantiates the login panel and is the first thing
     * launched when a user beings the game.
     *
     * @param stage
     * @param viewer
     * @param loggedInPlayer This determines where the program is coming from;
     * if null, then beginning of the game or else coming from the main menu
     */
    public LoginUI(final Stage stage, final ImageView viewer, User loggedInPlayer) {

        boolean cameFromMainUI;
        this.user = loggedInPlayer;
        if (user != null) {
            cameFromMainUI = true;
            playerNumber = 1;
        } else {
            cameFromMainUI = false;
        }

        //this is the background
        this.viewer = viewer;

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
        Button multiPlayerBtn = new Button("2 Players");
        multiPlayerBtn.setFont(new Font(16));
        multiPlayerBtn.setMinSize(180, 50);

        //finishing up the button group
        BtnGroup.setAlignment(Pos.CENTER);
        BtnGroup.setSpacing(70);
        BtnGroup.getChildren().addAll(singlePlayerBtn, multiPlayerBtn);
        if (cameFromMainUI) {
            BtnGroup.setDisable(true);
            BtnGroup.setOpacity(0);
        }

        //opening the group that conatins most of the login items (textboxs & buttons)
        loginBase = new VBox();

        //the username text box
        usernameInput = new TextField();
        usernameInput.setMaxWidth(200);

        //the password text box
        passwordInput = new PasswordField();
        passwordInput.setMaxWidth(200);

        //the label for the username
        Label usernameLabel = new Label("Username");
        usernameLabel.setTextFill(Color.WHITE);
        usernameLabel.setFont(new Font(16));

        //the label for the password 
        Label passwordLabel = new Label("Password");
        passwordLabel.setTextFill(Color.WHITE);
        passwordLabel.setFont(new Font(16));

        //opening the group that will contain the login button and the create account button
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

        //label that will give us warnings, if username is wrong or password doesn't work.
        errorLabel = new Label();
        errorLabel.setTextFill(Color.WHITE);
        errorLabel.setFont(new Font(16));
        errorLabel.setMinHeight(45);

        //finishing the group that contains most of thge login items  
        loginBase.setAlignment(Pos.CENTER);
        loginBase.getChildren().addAll(usernameLabel, usernameInput,
                passwordLabel, passwordInput,
                loginBtnGroup, errorLabel);
        loginBase.setSpacing(15);
        if (!cameFromMainUI) {
            loginBase.setDisable(true);
            loginBase.setOpacity(0);
        }

        // this is the swith group this is here to switch one out for the other 
        StackPane switchGrp = new StackPane();
        switchGrp.getChildren().addAll(BtnGroup, loginBase);

        //finishing off the group that holds everything 
        base.setSpacing(70);
        base.setAlignment(Pos.CENTER);
        base.getChildren().addAll(titleLabel, switchGrp);

        //adds the group that conatins everything and the background 
        getChildren().addAll(viewer, base);

        singlePlayerBtn.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * Defines a single player login. Changes state so program knows to
             * disable gameplay but still show statistics.
             *
             * @param e
             */
            @Override
            public void handle(ActionEvent e) {
                playerNumber = 1;
                loginBase.setDisable(false);
                BtnGroup.setDisable(true);
                FadeTransition buttonOut
                        = new FadeTransition(Duration.millis(1000), BtnGroup);
                buttonOut.setCycleCount(1);
                buttonOut.setAutoReverse(true);
                buttonOut.setFromValue(1.0);
                buttonOut.setToValue(0.0);

                FadeTransition loginIn
                        = new FadeTransition(Duration.millis(1000), loginBase);
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
            /**
             * Defines a 2-player login. Prepares to allow two users to login
             * sequentially.
             *
             * @param e
             */
            @Override
            public void handle(ActionEvent e) {
                playerNumber = 2;
                loginBase.setDisable(false);
                BtnGroup.setDisable(true);
                FadeTransition fadeTransitionout
                        = new FadeTransition(Duration.millis(1000), BtnGroup);
                fadeTransitionout.setCycleCount(1);
                fadeTransitionout.setAutoReverse(true);
                fadeTransitionout.setFromValue(1.0);
                fadeTransitionout.setToValue(0.0);

                FadeTransition fadeTransitionin
                        = new FadeTransition(Duration.millis(1000), loginBase);
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
            /**
             * Validates user login.
             *
             * @param e
             */
            @Override
            public void handle(ActionEvent e) {

                if (user != null && usernameInput.getText().equals(user.getUsername())) {
                    passwordInput.clear();
                    errorLabel.setText("That user is already logged in");
                    return;
                }

                User user = User.getUser(usernameInput.getText(), passwordInput.getText());

                if (user != null) {
                    if (playerNumber == 1) {
                        MenuUI mainMenu = new MenuUI(stage, viewer, LoginUI.this.user, user);
                        Scene mainMenuscene = new Scene(mainMenu);
                        stage.setScene(mainMenuscene);
                    } else {
                        playerNumber--;
                        usernameInput.clear();
                        passwordInput.clear();
                        errorLabel.setText("Logged in the first player, please enter second player ");
                        LoginUI.this.user = user;
                    }
                } else {
                    passwordInput.clear();
                    errorLabel.setText("Username or password was incorrect");
                }
            }
        });

        createAccountBtn.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * Processes account creation. Validates username and password and
             * handles invalid entries.
             *
             * @param e
             */
            @Override
            public void handle(ActionEvent e) {
                String password = passwordInput.getText();

                if (password.length() < 8 || password.matches("[A-Za-z0-9 ]*") || password.equals(password.toLowerCase()) || password.equals(password.toUpperCase()) || !password.matches(".*\\d.*")) {
                    passwordInput.clear();
                    errorLabel.setText("The password must be greater than 7 characters and contain at least: \n 1 lowercase letter, 1 uppercase letter, 1 number, and 1 special character ");
                    return;
                }

                if (usernameInput.getText().length() < 2) {
                    passwordInput.clear();
                    errorLabel.setText("The username length is too short - must be at least 2 characters");
                    return;
                }

                User user = User.createUser(usernameInput.getText(), passwordInput.getText());

                if (user != null) {
                    if (playerNumber == 1) {
                        MenuUI mainMenu = new MenuUI(stage, viewer, LoginUI.this.user, user);
                        Scene mainMenuscene = new Scene(mainMenu);
                        stage.setScene(mainMenuscene);
                    } else {
                        playerNumber--;
                        usernameInput.clear();
                        passwordInput.clear();
                        errorLabel.setText("Created the first player; please enter second player ");
                        LoginUI.this.user = user;
                    }
                } else {
                    passwordInput.clear();
                    errorLabel.setText("That username is already used; please try a different one ");
                }
            }
        });

    }
}
