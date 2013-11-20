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
 * @author draringi
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
     * @return
     */
    public boolean collides(int x, int y) {
        return internal.collides(x, y);
    }

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

    public static Map makeDemo(Display display) {
        Map map = new Map(75, 50, null, display);

        map.addPlayer(null, "0xF00", Player.Direction.LEFT); //TODO: change direction to enum
        Player player1 = map.getPlayer(0);
        controller.addBinding(new MovePlayerDown(KeyCode.S, player1));
        controller.addBinding(new MovePlayerLeft(KeyCode.A, player1));
        controller.addBinding(new MovePlayerUp(KeyCode.W, player1));
        controller.addBinding(new MovePlayerRight(KeyCode.D, player1));
        player1.moveLeft();

        map.addPlayer(null, "0x00F", Player.Direction.RIGHT);
        Player player2 = map.getPlayer(1);
        controller.addBinding(new MovePlayerDown(KeyCode.DOWN, player2));
        controller.addBinding(new MovePlayerLeft(KeyCode.LEFT, player2));
        controller.addBinding(new MovePlayerUp(KeyCode.UP, player2));
        controller.addBinding(new MovePlayerRight(KeyCode.RIGHT, player2));
        player2.moveRight();

        map.setSpeed(5);
        return map;
    }

    public void run() {
        new Thread(internal).start();
    }

    public Player getPlayer(int i) {
        return internal.getPlayer(i);
    }

    public void addPlayer(User user, String colour, Player.Direction direction) { //TODO: change direction to enum
        internal.addPlayer(user, colour, direction);
    }

    public void displayPlayer(int x, int y, String colour) {
        myDisplay.displayWall(x, y, colour);
    }

    /**
     * Generates Map from string
     *
     * @param mapString string formated map
     * @param colour String containing colour of base walls in hexadecimal
     * format
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

    private class MapTask extends Task<Void> {

        private final BitSet map;
        private final int width;
        private final int height;
        private boolean running;
        private int sleep;
        private final Vector<Player> playerList;
        private final GameInfo myGame;
        private final Display myDisplay;
        private final Map parent;
        private final ConcurrentLinkedQueue<Move> moveQueue;

        public void addWall(int xPos, int yPos, String colour) {
            moveQueue.add(new Move(xPos, yPos, colour));
        }

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

        @Override
        public Void call() {
            running = true;
            while (running) {
                if (isCancelled()) {
                    break;
                }
                gameRound();
            }
            myDisplay.gameover();
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
            return playerList.get(0);
        }

        private void gameRound() {
            Iterator<Player> it;
            for (it = playerList.iterator(); it.hasNext();) {
                Player player = it.next();
                player.moveCurrent();
            }
            listenPlayers();
            for (it = playerList.iterator(); it.hasNext();) {
                Player player = it.next();
                if (collides(player.getX(), player.getY())) {
                    running = false;//TODO: add player dies code once Player has alive attribute...
                } else {
                    displayPlayer(player.getX(), player.getY(), player.getColour());
                }
            }
            //TODO: add checks for who is alive
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException ex) {
                if (isCancelled()) {

                }
            }
        }

        public void addPlayer(User user, String colour, Player.Direction direction) {//TODO: change direction to enum
            Player player = new Player(25, 25, colour, user, parent, direction);
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
