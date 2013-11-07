package Gameplay;

import java.awt.event.KeyEvent;

public class MovePlayerLeft extends Input {
	
	public MovePlayerLeft(KeyEvent key, Player player) {
		super(key, player);
	}

	public void command() {
		this.player.moveLeft();
	}
}
