package Gameplay;

import Database.GameInfo;
import Database.User;

public class Player {

    public enum Direction {

        LEFT, UP, DOWN, RIGHT
    };

    private int xPos;
    private int yPos;
    private Direction currentDirection;
    private boolean isAlive;
    private int roundsWon;

    private final String colour;

    private final GameInfo myGame;
    //public Controller myController;
    private Map myMap;
    private final User myUser;
    private boolean moved;

    /**
     * 
     * @param xStart
     * @param yStart
     * @param colour
     * @param user
     * @param map
     * @param startingDirection 
     * 
     * used to give starting coords for new game 
     */
    public Player(int xStart, int yStart, String colour, User user, Map map, Direction startingDirection, GameInfo game) {
        this.xPos = xStart;
        this.yPos = yStart;
        this.colour = colour;
        this.myUser = user;
        this.myMap = map;
        this.currentDirection = startingDirection;
        moved = false;
        isAlive = true; // player starts round alive
        this.myGame = game;
    }
    
    /**
     * 
     * @param user
     * @param colour 
     */
    public Player(User user, String colour, GameInfo game){
      this.colour = colour;
      this.myUser = user;
      this.myMap = null;
      this.myGame = game;
    }

    public int getID(){
        return myUser.getID();
    }
    public int getX() {
        return this.xPos;
    }

    public int getY() {
        return this.yPos;
    }

    public String getColour() {
        return this.colour;
    }

    public boolean getIsAlive() {
        return this.isAlive;
    }

    public String getUsername() {
        return this.myUser.getUsername();
    }

    public void setX(int x) {
        this.xPos = x;
    }

    public void setY(int y) {
        this.yPos = y;
    }

    /**
     *
     * @param isAlive 
     */
    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
    
    /**
     * 
     * @param xStart
     * @param yStart
     * @param startingDirection 
     * 
     * Set initial position and moving direction on the grid
     */
    public void init(int xStart, int yStart, Direction startingDirection, Map map){
      this.xPos = xStart;
      this.yPos = yStart;
      this.currentDirection = startingDirection;
      this.isAlive = true;
      this.moved = false;
      this.myMap = map;
    }

    /**
     * Change direction to move up
     */
    public void moveUp() {
        //needs to be currently moving left or right
        if (!moved && (this.currentDirection == Direction.LEFT || this.currentDirection == Direction.RIGHT)) {
            //update new direction to up
            this.currentDirection = Direction.UP;
            moved = true;
        }
    }

    /**
     * Change direction to move down
     */
    public void moveDown() {
        //player needs to be moving left or right
        if (!moved && (this.currentDirection == Direction.LEFT || this.currentDirection == Direction.RIGHT)) {
            //update new direction to down
            this.currentDirection = Direction.DOWN;
            moved = true;
        }
    }

    /**
     * Change direction to move right
     */
    public void moveRight() {
        //player needs to be moving up or down
        if (!moved && (this.currentDirection == Direction.UP || this.currentDirection == Direction.DOWN)) {
            //update new direction to right
            this.currentDirection = Direction.RIGHT;
            moved = true;
        }
    }

    /**
     * Change direction to move left
     */
    public void moveLeft() {
        //player needs to be moving up or down
        if (!moved && (this.currentDirection == Direction.UP || this.currentDirection == Direction.DOWN)) {
            //update new direction to left
            this.currentDirection = Direction.LEFT;
            moved = true;
        }
    }
    
    /**
     * add a wall to initial spot and update position (x or y coordinate) based on direction
     */
    public void moveCurrent() {
        //add wall, to current position and color
        myMap.addWall(this.xPos, this.yPos, this.colour);
        //increment in same direction and check for collision (map) at new position
        switch (this.currentDirection) {
            case LEFT:
                this.xPos--;
                break;
            case UP:
                this.yPos--;// Up is negative
                break;
            case DOWN:
                this.yPos++;// Down is Positive
                break;
            case RIGHT:
                this.xPos++;
                break;
            default:
                break;
        }
        moved = false;

    }

    /**
     * increments the number of rounds won
     */
    public void winRound() {
      roundsWon++;
    }
    
    /**
     * gets the number of rounds won
     * @return 
     */
    public int getNumRoundsWon(){
      return roundsWon;
    }
    //these send the updated end of game stats to the DB.
    public void winGame() {
        myUser.winGame();
    }

    public void loseGame() {
        myUser.loseGame();
    }
    
    public void saveStats(){
        myUser.saveStats();
    }

}
