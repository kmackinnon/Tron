package gameplay;

import javafx.scene.input.KeyCode;

/**
 * Moves player in downward direction
 * @author Gabriel
 */
public class MovePlayerDown extends Input {
	
	public MovePlayerDown(KeyCode key, Player player) {
		super(key, player);
	}
        
        /**
         * Moves player in downward direction
         */
        @Override
	public void command() {
		this.player.moveDown();
	}
}
