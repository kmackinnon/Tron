package gameplay;

import javafx.scene.input.KeyCode;

import javafx.scene.input.KeyCode;

public class MovePlayerLeft extends Input {
	
	public MovePlayerLeft(KeyCode key, Player player) {
		super(key, player);
	}

	public void command() {
		this.player.moveLeft();
	}
}
