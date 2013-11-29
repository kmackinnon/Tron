package gameplay;

import javafx.scene.input.KeyCode;

/**
 * Moves player in right direction
 *
 * @author Gabriel
 */
public class MovePlayerRight extends Input {

    public MovePlayerRight(KeyCode key, Player player) {
        super(key, player);
    }

    /**
     * Moves player in right direction
     */
    @Override
    public void command() {
        this.player.moveRight();
    }
}
