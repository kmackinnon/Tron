package gameplay;

import javafx.scene.input.KeyCode;

public class MovePlayerUp extends Input {
	
	public MovePlayerUp(KeyCode key, Player player) {
		super(key, player);
	}

	public void command() {
		this.player.moveUp();
	}
}
