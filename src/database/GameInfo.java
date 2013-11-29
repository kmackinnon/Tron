package database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import gameplay.Map;
import gameplay.Controller;
import gameplay.MovePlayerDown;
import gameplay.MovePlayerLeft;
import gameplay.MovePlayerRight;
import gameplay.MovePlayerUp;
import gameplay.Player;
import userinterface.Display;
import javafx.scene.input.KeyCode;

/**
 * Contains all essential game information such as map, speed, game winner
 * @author Keith
 */
public class GameInfo {

    private int winner;

    private Map current;
    private final MapLoader baseMap;
    private Display display;

    private final ArrayList<Player> playerList;
    private static final DatabaseInterface db = new SQLiteInterface(".lightracer.db");
    private static final Controller controller = Controller.getInstance();
    private final int speed;

    /**
     * Sets the private display field equal to the display passed
     * @param display 
     */
    public void setDisplay(Display display) {
        this.display = display;
    }

    /**
     * 
     * @param playerOne
     * @param playerOneColour
     * @param playerTwo
     * @param playerTwoColour
     * @param speed
     * @param map 
     */
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
     * @param roundWinner
     */
    public void endRound(boolean draw, Player roundWinner) {
        if (draw) {
            display.roundover("Draw", getPlayerWins(0), getPlayerWins(1));
        } else {
            roundWinner.winRound();
            Iterator<Player> it;
            Player p;
            int i = 0;
            for (it = playerList.iterator(); it.hasNext();) {
                p = it.next();
                if (p.getNumRoundsWon() >= 2) {
                    winner = i;
                    this.save();
                    display.gameover(p.getUsername());
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
     * @param index
     * @return the player's username from his index in playerList
     */
    public String getPlayerName(int index) {
        Player player = playerList.get(index);
        return player.getUsername();
    }

    /**
     * @return the winning player's username
     */
    public String getWinnerName() {
        if (winner < 0) {
            return null;
        } else {
            return getPlayerName(winner);
        }
    }
    
    /**
     * @param index
     * @return the user id of the specified player
     */
    public int getPlayerID(int index) {
        Player player = playerList.get(index);
        return player.getID();
    }
    
    /**
     * @return the winning player's user id
     */
    public int getWinnerID() {
      return getPlayerID(winner);
    }
    
    /**
     * @param index
     * @return the number of rounds a player has won 
     */
    public int getPlayerWins(int index) {
        Player player = playerList.get(index);
        return player.getNumRoundsWon();
    }
    
    /**
     * Saves statistics after a game. 
     */
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
            i++;
            p.saveStats();
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        db.addGame(this);
    }
    
    /**
     * @return the first user in the playerList 
     */
    public User getFirstUser(){
        return playerList.get(0).getUser();
    }
    
    /**
     * @return the second user in the playerList
     */
    public User getSecondUser(){
        return playerList.get(1).getUser();
    }
    
    /**
     * @return the first player's color on the map
     */
    public String getFirstUserColour(){
        return playerList.get(0).getColour();
    }
    
    /**
     * @return the second player's color on the map 
     */
    public String getSecondUserColour(){
        return playerList.get(1).getColour();
    }
    
    /**
     * @return return the speed of the game in Hz (moves per second)
     */
    public int getSpeed(){
        return speed;
    }
    
    /**
     * @return the map being used
     */
    public MapLoader getMap(){
      return baseMap;  
    }

}
