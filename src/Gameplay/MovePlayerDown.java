package Gameplay;

import java.awt.event.KeyEvent;

public class MovePlayerDown extends Input {
	
	public MovePlayerDown(KeyEvent key, Player player) {
		super(key, player);
	}

	public void command() {
		this.player.moveDown();
	}
}
