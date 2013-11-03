package Gameplay;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

//TODO need to define an explicit KeyEvent constructor
public class Input extends KeyEvent {

	private int key; 
	Player player;
	
	public static ArrayList<Integer> inputPlayer1 = new ArrayList<Integer>(
			Arrays.asList(VK_UP, VK_DOWN, VK_LEFT, VK_RIGHT));
	public static ArrayList<Integer> inputPlayer2 = new ArrayList<Integer>(
			Arrays.asList(VK_W, VK_S, VK_A, VK_D));

	public void keyPressed(KeyEvent e) {
		int clicked = e.getKeyCode(); // key the user has clicked

		if (player.getPlayerNumber == 1) {
			if (inputPlayer1.contains(clicked)){
				key = clicked;	
			}

		} else {
			if (inputPlayer2.contains(clicked)){
				key = clicked;
			}
		}
	}
	
	// returns a valid key
	public int getKey(){
		return key;
	}

}