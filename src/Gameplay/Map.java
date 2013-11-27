package Gameplay;

import Database.GameInfo;
import Database.User;
import UserInterface.Display;

import java.util.BitSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;

/**
 * Contains details concerning the current map in play, and determines if a
 * collision has occurred.
 *
 * @author Michael Williams, Keith MacKinnon
 */
public class Map {

    private static final Pattern headerParser = Pattern.compile("\\dx\\d");
    private static final Pattern bodyParser = Pattern.compile("[01]+");

    private final MapTask internal;
    private int width, height;

    private final Display myDisplay;
    private final static Controller controller = Controller.getInstance();

    /**
     * Adds a wall at a given coordinate with a given colour.
     *
     * @param xPos
     * @param yPos
     * @param colour
     */
    public void addWall(int xPos, int yPos, String colour) {
        internal.addWall(xPos, yPos, colour);
    }

    /**
     * Sets the speed of the map to a frequency hz given in Hertz
     *
     * @param hz
     */
    public void setSpeed(int hz) {
        internal.setSpeed(hz);
    }

    /**
     * Checks if a collision would occur at a given coordinate.
     *
     * @param x
     * @param y
     * @return true or false if a collision occurred
     */
    public boolean collides(int x, int y) {
        return internal.collides(x, y);
    }

    /**
     *
     * @param mapString
     * @param colour
     * @return a BitSet which defines the map to be used
     */
    private BitSet mapParse(String mapString, String colour) {
        Matcher m = headerParser.matcher(mapString);
        String head[];
        if (m.find()) {
            head = m.group().split("x");
            width = Integer.parseInt(head[0]);
            height = Integer.parseInt(head[1]);
        } else {
          return null;
        }
        BitSet map = new BitSet();
        String main = mapString.substring(m.end());
        m = bodyParser.matcher(main);
        if (!m.matches()){
          return null;
        }
        char array[] = main.toCharArray();
        if (array.length != width * height) {
          return null;
        }
        for (int i = 0; i < array.length; i++) {
          if (array[i] == '1') {
            map.set(i);
          }
        }
        return map;
    }

    /**
     * Creates a map and associated keys to control both players and sets
     * initial positions and directions.
     *
     * @param display
     * @return map for the game board
     */
    public static Map makeDemo(Display display) {
        Map map = new Map(75, 50, null, display);

        map.addPlayer(null, "0xF00", Player.Direction.RIGHT, 0, 49);
        Player player1 = map.getPlayer(0);
        controller.addBinding(new MovePlayerDown(KeyCode.S, player1));
        controller.addBinding(new MovePlayerLeft(KeyCode.A, player1));
        controller.addBinding(new MovePlayerUp(KeyCode.W, player1));
        controller.addBinding(new MovePlayerRight(KeyCode.D, player1));
        player1.moveRight(); // starting direction

        map.addPlayer(null, "0x00F", Player.Direction.LEFT, 74, 0);
        Player player2 = map.getPlayer(1);
        controller.addBinding(new MovePlayerDown(KeyCode.K, player2));
        controller.addBinding(new MovePlayerLeft(KeyCode.J, player2));
        controller.addBinding(new MovePlayerUp(KeyCode.I, player2));
        controller.addBinding(new MovePlayerRight(KeyCode.L, player2));
        player2.moveLeft(); // starting direction

        map.setSpeed(5);
        return map;
    }

    /**
     * Starts the thread.
     */
    public void run() {
        new Thread(internal).start();
    }
    
    public int getPlayerWins(int index) {
      Player player = this.getPlayer(index);
      return player.getNumRoundsWon();
    }

    /**
     *
     * @param i the index of the player (0 or 1)
     * @return player 1 or player 2
     */
    public Player getPlayer(int i) {
        return internal.getPlayer(i);
    }

    /**
     * Adds a user with starting game information.
     *
     * @param user
     * @param colour
     * @param direction
     * @param x
     * @param y
     */
    public void addPlayer(User user, String colour, Player.Direction direction, int x, int y) {
        internal.addPlayer(user, colour, direction, x, y);
    }

    /**
     * Adds a player with starting game information.
     *
     * @param player
     * @param direction
     * @param x
     * @param y
     */
    public void addPlayer(Player player, Player.Direction direction, int x, int y) {
        internal.addPlayer(player, direction, x, y);
    }

    /**
     * Show a wall at current position of the player
     *
     * @param x
     * @param y
     * @param colour
     */
    public void displayPlayer(int x, int y, String colour) {
        myDisplay.displayWall(x, y, colour);
    }

    /**
     * Generates Map from String
     *
     * @param mapString string formatted map
     * @param colour String containing colour of base walls in hex format
     * @param game
     * @param display
     */
    public Map(String mapString, String colour, GameInfo game, Display display) {
        BitSet map = mapParse(mapString, colour);
        myDisplay = display;
        internal = new MapTask(width, height, game, display, map, this);
        controller.clear();
    }

    public byte[] getBinaryData() {
        BitSet map = internal.getMapData();
        return map.toByteArray();
    }
    
    /**
     * Generates blank Map of given width and height
     *
     * @param width
     * @param height
     * @param game
     * @param display
     */
    public Map(int width, int height, GameInfo game, Display display) {
        this.width = width;
        this.height = height;
        myDisplay = display;
        internal = new MapTask(width, height, game, display, new BitSet(width * height), this);
        controller.clear();
    }
    
    private BitSet loadMapDataFromBinary (byte data[]){
      if (data.length != (width * height)/8){//8 bits in a byte
        return null;
      }
      return BitSet.valueOf(data);
      
    }
    
    public Map(int width, int height, byte mapData[], GameInfo game, Display display){
        this.width = width;
        this.height = height;
        BitSet map = loadMapDataFromBinary(mapData);
        myDisplay = display;
        internal = new MapTask(width, height, game, display, map, this);
        controller.clear();
    }

    // inner class representing a thread
    private class MapTask extends Task<Void> {

        private final BitSet map;
        private final int width;
        private final int height;
        private boolean running;
        private int aliveCount = 0;
        private int sleep;
        private final LinkedList<Player> playerList;
        private final GameInfo myGame;
        private final Display myDisplay;
        private final Map parent;
        private final ConcurrentLinkedQueue<Move> moveQueue;

        public MapTask(int width, int height, GameInfo game, Display display, BitSet map, Map parent) {
            myGame = game;
            myDisplay = display;
            this.width = width;
            this.height = height;
            this.map = map;
            playerList = new LinkedList();
            this.parent = parent;
            moveQueue = new ConcurrentLinkedQueue();
        }

        public void addWall(int xPos, int yPos, String colour) {
            moveQueue.add(new Move(xPos, yPos, colour));
        }

        @Override
        public Void call() {
            running = true;
            while (running) {
                if (isCancelled()) {
                    break;
                }
                gameRound();
            }

//            if (aliveCount == 0){
//                // DRAW
//                myGame.endRound(true, null); // 2nd param is user's string
//            } else {
//                // One player wins
//                myGame.endRound(true, null); // 2nd param is winning user
//            }
            myDisplay.gameover();

            return null; // TODO is this necessary?
        }

        private void listenPlayers() {
            Move move;
            while (!moveQueue.isEmpty()) {
                move = moveQueue.poll();
                map.set(getCellIndex(move.getX(), move.getY()));
                myDisplay.displayWall(move.getX(), move.getY(), move.getColour());
            }
        }

        public void setSpeed(int hz) {
            sleep = 1000 / hz;
        }

        private int getCellIndex(int x, int y) {
            return y * width + x;
        }

        private boolean outside(int x, int y) {
            return (x >= width || x < 0 || y >= height || y < 0);
        }

        public Player getPlayer(int i) {
            return playerList.get(i);
        }
        
        private boolean sameSquare(Player player) {
          Iterator<Player> it;
          boolean dead = false;
          int x = player.getX();
          int y = player.getY();
          for (it = playerList.iterator(); it.hasNext();) {
            Player other = it.next();
            if (other != player && other.getIsAlive() && x == other.getX() && y == other.getY()) {
              other.setIsAlive(false);
              dead = true;
            }
          }
          return dead;
        }

        private void gameRound() {
            Iterator<Player> it;
            for (it = playerList.iterator(); it.hasNext();) {
                Player player = it.next();
                if (player.getIsAlive()){
                  player.moveCurrent();
                }
            }
            listenPlayers();
            for (it = playerList.iterator(); it.hasNext();) {
                Player player = it.next();
                if (!player.getIsAlive()){
                  continue;
                }
                if (collides(player.getX(), player.getY()) || sameSquare(player)) {
                    running = false;
                    player.setIsAlive(false);
                } else {
                    displayPlayer(player.getX(), player.getY(), player.getColour());
                }
            }

            // checks for who is alive
            for (it = playerList.iterator(); it.hasNext();) {
                Player player = it.next();
                if (player.getIsAlive()) {
                    aliveCount++;
                }
            }

            // at least one of the players is dead so game should end
            if (aliveCount < 2) {
                running = false;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException ex) {
                if (isCancelled()) {

                }
            }
        }
        
        public BitSet getMapData() {
            return map;
        }

        public void addPlayer(User user, String colour, Player.Direction direction, int x, int y) {
            Player player = new Player(x, y, colour, user, parent, direction);
            playerList.add(player);
        }

        public void addPlayer(Player player, Player.Direction direction, int x, int y) {
            player.init(x, y, direction);
            playerList.add(player);
        }

        public boolean collides(int x, int y) {
            return (outside(x, y) || map.get(getCellIndex(x, y)));
        }

        private class Move {

            private final int x, y;
            private final String colour;

            public Move(int x, int y, String colour) {
                this.x = x;
                this.y = y;
                this.colour = colour;
            }

            public final int getX() {
                return x;
            }

            public final int getY() {
                return y;
            }

            public final String getColour() {
                return colour;
            }
        }
    }
}
