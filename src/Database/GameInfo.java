package Database;

import java.util.ArrayList;
import java.util.Iterator;
import Gameplay.Map;
import Gameplay.Player;
import UserInterface.Display;

public class GameInfo {

    private int winner;

    private Map current;
    private MapLoader baseMap;
    private Display display;

    /**
     *
     * @element-type Player
     */
    private final ArrayList<Player> playerList;
    /**
     *
     * @element-type MapLoader
     */
    private static final DatabaseInterface myDatabaseInterface = new SQLiteInterface(".lightracer.db");
    private int speed;

    public void setDisplay(Display display) {
        this.display = display;
    }

    public GameInfo(User playerOne, String playerOneColour, User playerTwo, String playerTwoColour, int speed, MapLoader map) {
        winner = -1;
        this.speed = speed;
        playerList = new ArrayList(2);
        this.baseMap = map;
        playerList.add(new Player(playerOne, playerOneColour));
        playerList.add(new Player(playerTwo, playerTwoColour));
    }

    /**
     * Loads each round of the game and sets starting position and direction.
     */
    public void startRound() {
        current = baseMap.loadMap(display, this);
        current.addPlayer(playerList.get(0), Player.Direction.RIGHT, 0, 49);
        current.addPlayer(playerList.get(1), Player.Direction.LEFT, 74, 0);
        current.run();
    }

    public void endRound(boolean draw, Player victor) {
        if (draw) {
            startRound(); // restart the round
        } else {
            victor.winRound();
            Iterator<Player> it;
            Player p;
            int i = 0;
            for (it = playerList.iterator(); it.hasNext();) {
                p = it.next();
                if (p.getNumRoundsWon() >= 2) {
                    winner = i;
                    return;
                }
                i++;
            }
            this.startRound();
        }
    }

    public boolean endGame() {
        if (winner < 0) {
            return false;
        } else {
            Player player = playerList.get(winner);
            player.winGame();
            return true;
        }
    }

    public String getPlayerName(int index) {
        Player player = playerList.get(index);
        return player.getUsername();
    }

    public String getWinnerName() {
        if (winner < 0) {
            return null;
        } else {
            return getPlayerName(winner);
        }
    }

}
