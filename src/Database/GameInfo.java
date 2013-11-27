package Database;

import java.util.ArrayList;
import java.util.Iterator;
import Gameplay.Map;
import Gameplay.Controller;
import Gameplay.MovePlayerDown;
import Gameplay.MovePlayerLeft;
import Gameplay.MovePlayerRight;
import Gameplay.MovePlayerUp;
import Gameplay.Player;
import UserInterface.Display;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;

public class GameInfo {

    private int winner;

    private Map current;
    private MapLoader baseMap;
    private Display display;

    private final ArrayList<Player> playerList;
    private static final DatabaseInterface db = new SQLiteInterface(".lightracer.db");
    private static final Controller controller = Controller.getInstance();
    private final int speed;

    public void setDisplay(Display display) {
        this.display = display;
    }

    public GameInfo(User playerOne, String playerOneColour, User playerTwo, String playerTwoColour, int speed, MapLoader map) {
        winner = -1; // inital value when no one has won yet
        this.speed = speed;
        playerList = new ArrayList(2);
        this.baseMap = map;
        playerList.add(new Player(playerOne, playerOneColour, this));
        playerList.add(new Player(playerTwo, playerTwoColour, this));
        controller.clear();
        
    }

    /**
     * Loads each round of the game and sets starting position and direction.
     */
    public void startRound() {
        current = baseMap.loadMap(display, this);
        current.setSpeed(speed);
        current.addPlayer(playerList.get(0), Player.Direction.RIGHT, 0, 49);
        current.addPlayer(playerList.get(1), Player.Direction.LEFT, 74, 0);
        controller.addBinding(new MovePlayerDown(KeyCode.S, current.getPlayer(0)));
        controller.addBinding(new MovePlayerLeft(KeyCode.A, current.getPlayer(0)));
        controller.addBinding(new MovePlayerUp(KeyCode.W, current.getPlayer(0)));
        controller.addBinding(new MovePlayerRight(KeyCode.D, current.getPlayer(0)));
        controller.addBinding(new MovePlayerDown(KeyCode.K, current.getPlayer(1)));
        controller.addBinding(new MovePlayerLeft(KeyCode.J, current.getPlayer(1)));
        controller.addBinding(new MovePlayerUp(KeyCode.I, current.getPlayer(1)));
        controller.addBinding(new MovePlayerRight(KeyCode.L, current.getPlayer(1)));
        current.run();
    }

    /**
     * Ends the round and keeps track of result and round winner. Sets the game
     * winner.
     *
     * @param draw
     * @param victor
     */
    public void endRound(boolean draw, Player roundWinner) {
        if (draw) {
            display.roundover("Draw", getPlayerWins(0), getPlayerWins(1));
            //this.startRound();
        } else {
            roundWinner.winRound();
            Iterator<Player> it;
            Player p;
            int i = 0;
            for (it = playerList.iterator(); it.hasNext();) {
                p = it.next();
                if (p.getNumRoundsWon() >= 2) {
                    winner = i;
                    display.gameover(p.getUsername());
                    this.save();
                    return;
                }
                i++;
            }
           display.roundover(roundWinner.getUsername(), getPlayerWins(0), getPlayerWins(1));
        }
    }

    /**
     * Determines the end of a game.
     *
     * @return true or false
     */
    public boolean endGame() {
        if (winner < 0) {
            return false; // keep playing
        } else {
            Player player = playerList.get(winner);
            player.winGame();
            return true;
        }
    }

    /**
     * A quick way to get a player's username from his index in the playerList.
     *
     * @param index
     * @return the player's username
     */
    public String getPlayerName(int index) {
        Player player = playerList.get(index);
        return player.getUsername();
    }

    /**
     * Get the game winner's username.
     *
     * @return the winning player's username
     */
    public String getWinnerName() {
        if (winner < 0) {
            return null;
        } else {
            return getPlayerName(winner);
        }
    }
    
    public int getPlayerID(int index) {
        Player player = playerList.get(index);
        return player.getID();
    }
    
    public int getWinnerID() {
      return getPlayerID(winner);
    }
    
    public int getPlayerWins(int index) {
        Player player = playerList.get(index);
        return player.getNumRoundsWon();
    }
    
    private void save(){
        int i = 0;
        Iterator<Player> it;
        for (it = playerList.iterator(); it.hasNext();) {
            Player p = it.next();
            if (i==this.winner) {
                p.winGame();
            } else {
                p.loseGame();
            }
            p.saveStats();
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        db.addGame(this);
    }
    
    public User getFirstUser(){
        return playerList.get(0).getUser();
    }
    public User getSecondUser(){
        return playerList.get(0).getUser();
    }

}
