package Gameplay;

import javafx.scene.input.KeyCode;

public class MovePlayerRight extends Input {
	
	public MovePlayerRight(KeyCode key, Player player) {
		super(key, player);
	}

	public void command() {
		this.player.moveRight();
	}
}
