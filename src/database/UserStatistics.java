package database;

/**
 * Contains user statistics necessary for tracking wins, losses, and games
 * played.
 *
 * @author Michael Williams
 */
public class UserStatistics {

    private int wins;
    private int losses;
    private int gamesPlayed;

    /**
     * Increments number of wins and games played
     */
    public void addWin() {
        wins++;
        gamesPlayed++;
    }

    /**
     * Increments number of losses and games played
     */
    public void addLoss() {
        losses++;
        gamesPlayed++;
    }

    /**
     * @return the number of wins
     */
    public int getWins() {
        return wins;
    }

    /**
     * @return the number of losses
     */
    public int getLosses() {
        return losses;
    }

    /**
     * @return the total number of games played
     */
    public int getGames() {
        return gamesPlayed;
    }

    /**
     * Passes in new statistics for a User.
     *
     * @param wins
     * @param losses
     * @param games
     */
    public UserStatistics(int wins, int losses, int games) {
        this.wins = wins;
        this.losses = losses;
        this.gamesPlayed = games;
    }
}
