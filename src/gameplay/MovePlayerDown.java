package gameplay;

import javafx.scene.input.KeyCode;

public class MovePlayerDown extends Input {
	
	public MovePlayerDown(KeyCode key, Player player) {
		super(key, player);
	}
        
        @Override
	public void command() {
		this.player.moveDown();
	}
}
