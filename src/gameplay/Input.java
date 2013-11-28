package gameplay;

import javafx.scene.input.KeyCode;

public abstract class Input {

    private final KeyCode key;
    protected Player player;

    public KeyCode checkKey() {
        return key;
    }

    public boolean isKey(KeyCode key) {
        return key == this.key;
    }

    public abstract void command();

    public Input(KeyCode key, Player player) {
        this.key = key;
        this.player = player;
    }
}
