package Gameplay;

import Database.GameInfo;
import Database.User;

public class Player {

  private int xPos;
  private int yPos;
  private int currentDirection; //1=left, 2=up, 3=down, 4=right

  private final String colour;

  private GameInfo myGame;
  //public Controller myController;
  private Input myInput;
  private final Map myMap;
  private final User myUser;
  private boolean moved;
  
  //used to give starting coords for new game 
  public Player(int xStart, int yStart, String colour, User user, Map map, int startingDirection){
      this.xPos = xStart;
      this.yPos = yStart;
      this.colour = colour;
      this.myUser = user;
      this.myMap = map;
      this.currentDirection = startingDirection;
      moved = false;
  }
  public int getX(){
      return this.xPos;
  }
  public int getY(){
      return this.yPos;
  }
  
  public void setX(int x){
      this.xPos = x;
  }
  
  public void setY(int y){
      this.yPos = y;
  }
  
  public String getColour(){
      return this.colour;
  }

  public void moveUp() {
      //needs to be currently moving left or right
      if(!moved&&(this.currentDirection == 1 || this.currentDirection == 4)) {
          //update new direction to up
          this.currentDirection = 2;
          moved = true;
      }
  }
  
  public void moveDown() {
      //player needs to be moving left or right
      if(!moved&&(this.currentDirection == 1 || this.currentDirection == 4)) {
          //update new direction to down
          this.currentDirection = 3;
          moved = true;
      }
  }

  public void moveRight() {
      //player needs to be moving up or down
      if(!moved&&(this.currentDirection == 2 || this.currentDirection == 3)) {
          //update new direction to right
          this.currentDirection = 4;
          moved = true;
      }
  }

  public void moveLeft() {
      //player needs to be moving up or down
      if(!moved&& (this.currentDirection == 2 || this.currentDirection == 3)) {
          //update new direction to left
          this.currentDirection = 1;
          moved = true;
      }
  }

  public void moveCurrent() {
      //add wall, to current position and color
      myMap.addWall(this.xPos, this.yPos, this.colour);
      //increment in same direction and check for collision (map) at new position
      switch(this.currentDirection){
          case 1:
              this.xPos--;
              break;
          case 2:
              this.yPos--;// Up is negative
              break;
          case 3:
              this.yPos++;// Down is Positive
              break;
          case 4:
              this.xPos++;
              break;
          default:
              break;
      }
      moved = false;
      
  }

  //these send the updated end of game stats to the DB.
  public void winGame() {
  }

  public void loseGame() {
  }

  public String getUsername() {
      return this.myUser.getUsername();
  }

}