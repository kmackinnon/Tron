package lightracer;

import userinterface.LoginUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * This class launches the game.
 * @author Victorio
 */
public class LightRacer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Sets the background image and loads the login screen.
     * @param stage
     */
    @Override
    public void start(final Stage stage) {

        final Image background = new Image("/image/background.png");
        ImageView viewer = new ImageView();
        viewer.setImage(background);

        LoginUI startScreen = new LoginUI(stage, viewer, null);
        Scene startScreenScene = new Scene(startScreen);
        stage.setScene(startScreenScene);
        stage.setTitle("Light Racer");
        stage.setWidth(1030);
        stage.setHeight(800);

        stage.show();
    }

}
