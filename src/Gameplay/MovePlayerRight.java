package Gameplay;

import java.awt.event.KeyEvent;

public class MovePlayerRight extends Input {
	
	public MovePlayerRight(KeyEvent key, Player player) {
		super(key, player);
	}

	public void command() {
		this.player.moveRight();
	}
}
