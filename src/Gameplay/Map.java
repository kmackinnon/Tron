package Gameplay;

import Database.GameInfo;
import Database.User;
import UserInterface.Display;

import java.util.BitSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Vector;
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
            m.find();
            width = Integer.parseInt(head[0]);
            height = Integer.parseInt(head[1]);
        }
        BitSet map = new BitSet();
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

    /**
     * Inner class to perform related tasks. TODO Explain this better
     */
    private class MapTask extends Task<Void> {

        private final BitSet map;
        private final int width;
        private final int height;
        private boolean running;
        private int aliveCount = 0;
        private int sleep;
        private final Vector<Player> playerList;
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
            playerList = new Vector();
            this.parent = parent;
            moveQueue = new ConcurrentLinkedQueue();
        }

        /**
         * Adds a wall at given position.
         *
         * @param xPos
         * @param yPos
         * @param colour
         */
        public void addWall(int xPos, int yPos, String colour) {
            moveQueue.add(new Move(xPos, yPos, colour));
        }

        /**
         * Used to tell the display whether the round is still in progress.
         *
         * @return
         */
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

        /**
         * When moving into a cell on the grid, set the cell index as moved.
         * Also display the wall with appropriate colour.
         */
        private void listenPlayers() {
            Move move;
            while (!moveQueue.isEmpty()) {
                move = moveQueue.poll();
                map.set(getCellIndex(move.getX(), move.getY()));
                myDisplay.displayWall(move.getX(), move.getY(), move.getColour());
            }
        }

        /**
         * Sets the sleep interval based on the frequency
         * @param hz 
         */
        public void setSpeed(int hz) {
            sleep = 1000 / hz;
        }

        /** 
         * Used to get the cell index on the game map.
         * @param x
         * @param y
         * @return the index of the cell
         */
        private int getCellIndex(int x, int y) {
            return y * width + x;
        }

        /**
         * Defines the exterior of the grid.
         * 
         * @param x
         * @param y
         * @return true or false if player moves outside of grid
         */
        private boolean outside(int x, int y) {
            return (x >= width || x < 0 || y >= height || y < 0);
        }

        /** 
         * Gets the player from the playerList. 
         * @param i
         * @return the player at the specified index
         */
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

        /**
         * Defines a round occurring as part of a game. 
         * Determines characteristics such as who is still alive.
         */
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
