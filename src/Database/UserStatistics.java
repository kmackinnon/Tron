package Database;

public class UserStatistics {

  private int wins;

  private int games;

  private int losses;

  private User myUser;
  
  public void addWin(){
    wins++;
    games++;
  }
  
  public void addLoss(){
    losses++;
    games++;
  }

  public int getWins(){
    return wins;
  }

  public int getLosses(){
    return losses;
  }

  public int getGames(){
    return games;
  }

  public UserStatistics(int wins, int losses, int games){
    this.wins = wins;
    this.losses = losses;
    this.games = games;
  }
}
