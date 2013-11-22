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
    private int wins;

    private final String colour;

    private GameInfo myGame;
    //public Controller myController;
    private Input myInput;
    private final Map myMap;
    private final User myUser;
    private boolean moved;

    //used to give starting coords for new game 
    public Player(int xStart, int yStart, String colour, User user, Map map, Direction startingDirection) {
        this.xPos = xStart;
        this.yPos = yStart;
        this.colour = colour;
        this.myUser = user;
        this.myMap = map;
        this.currentDirection = startingDirection;
        moved = false;
        isAlive = true; // player starts round alive
    }
    
    public Player(User user, String colour){
      this.colour = colour;
      this.myUser = user;
      this.myMap = null;
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

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
    
    public void init(int xStart, int yStart, Direction startingDirection){
      this.xPos = xStart;
      this.yPos = yStart;
      this.currentDirection = startingDirection;
    }

    public void moveUp() {
        //needs to be currently moving left or right
        if (!moved && (this.currentDirection == Direction.LEFT || this.currentDirection == Direction.RIGHT)) {
            //update new direction to up
            this.currentDirection = Direction.UP;
            moved = true;
        }
    }

    public void moveDown() {
        //player needs to be moving left or right
        if (!moved && (this.currentDirection == Direction.LEFT || this.currentDirection == Direction.RIGHT)) {
            //update new direction to down
            this.currentDirection = Direction.DOWN;
            moved = true;
        }
    }

    public void moveRight() {
        //player needs to be moving up or down
        if (!moved && (this.currentDirection == Direction.UP || this.currentDirection == Direction.DOWN)) {
            //update new direction to right
            this.currentDirection = Direction.RIGHT;
            moved = true;
        }
    }

    public void moveLeft() {
        //player needs to be moving up or down
        if (!moved && (this.currentDirection == Direction.UP || this.currentDirection == Direction.DOWN)) {
            //update new direction to left
            this.currentDirection = Direction.LEFT;
            moved = true;
        }
    }

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

    public void winRound() {
      wins++;
    }
    public int getWins(){
      return wins;
    }
    //these send the updated end of game stats to the DB.
    public void winGame() {
    }

    public void loseGame() {
    }

}
