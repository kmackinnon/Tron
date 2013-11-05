package Gameplay;

import Database.Game;
import Database.User;

public class Player {

  private int xPos;
  private int yPos;
  private int playerNumber; //used to distinguish between player 1 and player 2
  private int currentDirection; //1=left, 2=up, 3=down, 4=right

  private String colour;

  public Game myGame;
  //public Controller myController;
  public Input myInput;
  public Map myMap;
  public User myUser;
  
  //used to give starting coords for new game 
  public Player(int xStart, int yStart, int num, User user){
      this.xPos = xStart;
      this.yPos = yStart;
      this.playerNumber = num;
      this.myUser = user;
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
  public int getPlayerNumber(){
      return this.playerNumber;
  }
  
  //set 1 for player 1 (Arrow keys), 2 for player 2 (WASD)
  public void setPlayerNumber(int number){
      this.playerNumber = number;
  }
  
  public void moveUp() {
      //needs to be currently moving left or right
      if(this.currentDirection == 1 || this.currentDirection == 4)
      {
          //update new direction to up
          this.currentDirection = 2;
      }
  }
  
  public void moveDown() {
      //player needs to be moving left or right
      if(this.currentDirection == 1 || this.currentDirection == 4)
      {
          //update new direction to down
          this.currentDirection = 3;
      }
  }

  public void moveRight() {
      //player needs to be moving up or down
      if(this.currentDirection == 2 || this.currentDirection == 3)
      {
          //update new direction to right
          this.currentDirection = 4;
      }
  }

  public void moveLeft() {
      //player needs to be moving up or down
      if(this.currentDirection == 2 || this.currentDirection == 3)
      {
          //update new direction to left
          this.currentDirection = 1;
      }
  }

  public void moveCurrent() {
      //add wall, to current position and color
      Map.addWall(this.xPos, this.yPos, this.colour);
      //increment in same direction and check for collision (map) at new position
      switch(this.currentDirection){
          case 1:
              this.xPos--;
              break;
          case 2:
              this.yPos++;
              break;
          case 3:
              this.yPos--;
              break;
          case 4:
              this.xPos++;
              break;
          default:
              break;
      }
      //need to figure out specifics of collision and ending the round
      if(Map.collides(this.xPos, this.yPos)){
          loseGame();
      }
  }

  public void winGame() {
  }

  public void loseGame() {
  }

  public String getUsername() {
      return this.myUser.getUsername(); //need to add get method in User class
  }

}