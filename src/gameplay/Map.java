package gameplay;

import database.GameInfo;
import database.User;
import userinterface.Display;

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
     * @return width of map
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return height of map
     */
    public int getHeight() {
        return height;
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
        if (!m.matches()) {
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
     * Starts the thread.
     */
    public void run() {
        new Thread(internal).start();
    }

    /**
     * Gets the number of rounds won for specific Player
     *
     * @param index
     * @return
     */
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
        this.drawBitset(map, display, colour);
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

    private BitSet loadMapDataFromBinary(byte data[]) {
        return BitSet.valueOf(data);

    }

    private void drawBitset(BitSet set, Display display, String colour) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (set.get(i + j * width)) {
                    display.displayWall(i, j, colour);
                }
            }
        }
    }

    /**
     * Creates a new Map with the following parameters:
     *
     * @param width
     * @param height
     * @param mapData
     * @param colour
     * @param game
     * @param display
     */
    public Map(int width, int height, byte mapData[], String colour, GameInfo game, Display display) {
        this.width = width;
        this.height = height;
        BitSet map = loadMapDataFromBinary(mapData);
        myDisplay = display;
        drawBitset(map, display, colour);
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

            String gameWinner = "";
            Iterator<Player> it;

            // DRAW
            if (aliveCount == 0) {
                myGame.endRound(true, null); // no one wins
            } else {
                // One player wins.
                for (it = playerList.iterator(); it.hasNext();) {
                    Player player = it.next();
                    // find the winning player
                    if (player.getIsAlive()) {
                        gameWinner = player.getUsername();
                        myGame.endRound(false, player);
                    }
                }
            }

           // myDisplay.gameover(gameWinner);
            return null;
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
                if (player.getIsAlive()) {
                    player.moveCurrent();
                }
            }
            listenPlayers();
            for (it = playerList.iterator(); it.hasNext();) {
                Player player = it.next();
                if (!player.getIsAlive()) {
                    continue;
                }
                if (collides(player.getX(), player.getY()) || sameSquare(player)) {
                    running = false;
                    player.setIsAlive(false);
                } else {
                    displayPlayer(player.getX(), player.getY(), player.getColour());
                }
            }
            aliveCount = 0;//Init to zero before counting
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
            Player player = new Player(x, y, colour, user, parent, direction, null);
            playerList.add(player);
        }

        public int addPlayer(Player player, Player.Direction direction, int x, int y) {
            player.init(x, y, direction, this.parent);
            playerList.add(player);
            return playerList.size() - 1;
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
