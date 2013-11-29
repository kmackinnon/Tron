package gameplay;

import javafx.scene.input.KeyCode;

/**
 * Moves player in left direction
 * @author Gabriel
 */
public class MovePlayerLeft extends Input {
	
	public MovePlayerLeft(KeyCode key, Player player) {
		super(key, player);
	}

        /**
         * Moves player in left direction
         */
        @Override
	public void command() {
		this.player.moveLeft();
	}
}
