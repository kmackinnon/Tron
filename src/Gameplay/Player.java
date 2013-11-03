package Gameplay;

import Database.Game;
import Database.User;

public class Player {

  private int xPos;
  private int yPos;
  private int playerNumber; //used to distinguish between player 1 and player 2
  private int currentDirection; //1=left, 2=up, 3=down, 4=right

  public String colour;
  public int direction;

  public Game myGame;
  //public Controller myController;
  public Input myInput;
  public Map myMap;
  public User myUser;
  
  //used to give starting coords for new game 
  public Player(int xStart, int yStart, int num, User userName){
      this.xPos = xStart;
      this.yPos = yStart;
      this.playerNumber = num;
      this.myUser = userName;
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
  
  public int getPlayerNumber(){
      return this.playerNumber;
  }
  
  //set 1 for player 1 (Arrow keys), 2 for player 2 (WASD)
  public void setPlayerNumber(int number){
      this.playerNumber = number;
  }
  
  public void moveUp() {
      //needs to be currently moving left or right
      
      
//      while( ){
//          this.Ypos += 2;
//      }
  }
  
  public void moveDown() {
  }

  public void moveRight() {
  }

  public void moveLeft() {
  }

  public void moveCurrent() {
      //retrieves current direction
  }

  public void winGame() {
  }

  public void loseGame() {
  }

  public String getUsername() {
      
  return null;
  }

}