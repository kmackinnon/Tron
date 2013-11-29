/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface;

/**
 * Dummy class for use in testing instead of Display
 *
 * @author draringi
 */
public class DummyDisplay extends Display {

    public DummyDisplay(database.GameInfo gameInfo) {
        super(null, null, gameInfo);
    }

    @Override
    public void displayWall(int xpos, int ypos, String color) {
        System.out.println("Place Wall Request at " + xpos + "," + ypos + "With colour " + color);
    }

    @Override
    public void roundover(String winner, int player1win, int player2win) {
        System.out.println("Round winner was: " + winner + "... Player 1 has " + player1win + " wins and Player 2 has " + player2win + " wins.");
    }

    @Override
    public void gameover(String winner) {
        System.out.println("Game Over: Winner was " + winner);
    }
}
