package Gameplay;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Controller extends KeyAdapter {

	/**
	 * 
	 * @element-type Input
	 */
	Input input;

	/**
	 * 
	 * @element-type Player
	 */
	public ArrayList<Player> myPlayer;

	public Controller(Input input) {
		this.input = input;
	}

	public void keyPressed(KeyEvent e) {
		input.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		input.keyReleased(e);
	}

}