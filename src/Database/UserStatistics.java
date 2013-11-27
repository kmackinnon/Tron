package Database;

public class UserStatistics {

  private int wins;

  private int gamesPlayed;

  private int losses;

  private User myUser;
  
  /**
   * Increments number of wins and games played
   */
  public void addWin(){
    wins++;
    gamesPlayed++;
  }
  
  /**
   * Increments number of losses and games played
   */
  public void addLoss(){
    losses++;
    gamesPlayed++;
  }

  /**
   * Retrieves number of wins
   * @return 
   */
  public int getWins(){
    return wins;
  }

  /**
   * Retrieves number of losses
   * @return 
   */
  public int getLosses(){
    return losses;
  }

  /**
   * Retrieves total number of games played
   * @return 
   */
  public int getGames(){
    return gamesPlayed;
  }

  /**
   * Creates new set of statistics for a User
   * @param wins
   * @param losses
   * @param games 
   */
  public UserStatistics(int wins, int losses, int games){
    this.wins = wins;
    this.losses = losses;
    this.gamesPlayed = games;
  }
}
