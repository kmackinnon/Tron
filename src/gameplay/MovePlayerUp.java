package gameplay;

import javafx.scene.input.KeyCode;

/**
 * Moves player in up direction
 * @author Gabriel
 */
public class MovePlayerUp extends Input {
	
	public MovePlayerUp(KeyCode key, Player player) {
		super(key, player);
	}
        
        /**
         * Moves player in upward direction
         */
        @Override
	public void command() {
		this.player.moveUp();
	}
}
