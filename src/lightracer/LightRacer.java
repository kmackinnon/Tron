package lightracer;


import UserInterface.GameInitUI;
import UserInterface.Display;
import javafx.application.Application;
import javafx.scene.Scene;
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
        
        Display demo = new Display(stage);
        Scene demoScene = new Scene(demo);
        stage.setScene(demoScene);
        
        //GameInitUI startScreen = new GameInitUI(stage);
        //Scene startScreenScene = new Scene(startScreen);
        //stage.setScene(startScreenScene);
        stage.setTitle("Light Racer");
        stage.setWidth(1030);
        stage.setHeight(800);

        stage.show();
    }

}
