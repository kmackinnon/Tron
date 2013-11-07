package Gameplay;

import java.awt.event.KeyEvent;

public class MovePlayerUp extends Input {
	
	public MovePlayerUp(KeyEvent key, Player player) {
		super(key, player);
	}

	public void command() {
		this.player.moveUp();
	}
}
