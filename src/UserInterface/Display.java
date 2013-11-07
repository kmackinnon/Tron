/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Victorio
 */
public class Display extends StackPane {
    
    final int gridSize= 50;
   
    private Rectangle grid[][] ; 
    
    
    public Display() {
    
        
        final Image background = new Image("file:/F:/Display/src/images/background.png");
        ImageView veiwer = new ImageView();
        veiwer.setImage(background);
        
        grid = new Rectangle[gridSize][gridSize]; 
        GridPane gridpanel = new GridPane();
        gridpanel.setAlignment(Pos.CENTER);
        gridpanel.setHgap(0);
        gridpanel.setVgap(0);
        
        makeSquares(gridpanel);

        
        getChildren().add(veiwer);
        getChildren().add(gridpanel);
        
        displaywall(5,5,Color.RED);
    }

    public void makeSquares(GridPane panel )
    {
 
        for(int i = 0; i< gridSize ; i++)
        {
            for(int j = 0; j< gridSize ; j++)
            {
                grid[i][j]= new Rectangle();
                grid[i][j].setWidth(10);
                grid[i][j].setHeight(10);
                grid[i][j].setFill(Color.GREY);
                panel.add(grid[i][j],i, j);
            }
        }
    }

    public void displaywall(int xpos,int ypos, Color color){
         grid[xpos][ypos].setFill(color);
    }
    
    
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
}
