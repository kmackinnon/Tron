package UserInterface;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;



public class StatisticsUI extends StackPane {

    public StatisticsUI(final Stage stage,ImageView veiwer) {
        
        
        HBox base = new HBox();
        
        VBox player1Stats = new VBox();
        player1Stats.setMinWidth(200);
        player1Stats.setAlignment(Pos.CENTER);
        VBox player2Stats = new VBox();
        player2Stats.setMinWidth(200);
        player2Stats.setAlignment(Pos.CENTER);
        VBox headToHeadStats = new VBox();
        headToHeadStats.setMinWidth(200);
        headToHeadStats.setAlignment(Pos.CENTER);
        VBox top10Stats = new VBox();
        top10Stats.setMinWidth(200);
        top10Stats.setAlignment(Pos.CENTER);
        
        Label player1StatsTitle = new Label("Player 1 Stats:");
        player1StatsTitle.setFont(new Font(16));
        player1StatsTitle.setTextFill(Color.WHITE);
        
        Label player2StatsTitle = new Label("Player 2 Stats:");
         player2StatsTitle.setFont(new Font(16));
         player2StatsTitle.setTextFill(Color.WHITE);
         
        Label headToHeadStatsTitle = new Label("Head-to-Head Stats:");
         headToHeadStatsTitle.setFont(new Font(16));
         headToHeadStatsTitle.setTextFill(Color.WHITE);
         
        Label top10StatsTitle = new Label("Top 10 Players:");
         top10StatsTitle.setFont(new Font(16));
         top10StatsTitle.setTextFill(Color.WHITE);
         
         player1Stats.getChildren().addAll(player1StatsTitle);
         player2Stats.getChildren().addAll(player2StatsTitle);
         headToHeadStats.getChildren().addAll(headToHeadStatsTitle);
         top10Stats.getChildren().addAll(top10StatsTitle);
         
         base.getChildren().addAll(player1Stats,headToHeadStats,player2Stats,top10Stats);
         
         getChildren().addAll(veiwer,base);
         
         
        
         
         
        
    } 
}