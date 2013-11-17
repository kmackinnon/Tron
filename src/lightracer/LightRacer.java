package lightracer;


import UserInterface.LoginUI;
import UserInterface.menuUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Victorio
 */
public class LightRacer extends Application {
    public static void main(String[] args) {


        launch(args);
    }
    public void start(final Stage stage) {
        
        final Image background = new Image("/image/background.png");
        ImageView veiwer = new ImageView();
        veiwer.setImage(background);

        LoginUI startScreen = new LoginUI(stage, veiwer);
        Scene startScreenScene = new Scene(startScreen);
        stage.setScene(startScreenScene);
        stage.setTitle("Light Racer");
        stage.setWidth(1030);
        stage.setHeight(800);

        stage.show();
    }

}
