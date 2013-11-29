package gameplay;

import javafx.scene.input.KeyCode;

/**
 * Abstract class to handle KeyCode inputs;
 * @author Gabriel
 */
public abstract class Input {

    private final KeyCode key;
    protected Player player;

    /**
     * Retrieves KeyCode
     * @return 
     */
    public KeyCode checkKey() {
        return key;
    }
    
    /**
     * Check for KeyCode match
     * @param key
     * @return 
     */
    public boolean isKey(KeyCode key) {
        return key == this.key;
    }

    public abstract void command();
    
    /**
     * Sets key for specific player
     * @param key
     * @param player 
     */
    public Input(KeyCode key, Player player) {
        this.key = key;
        this.player = player;
    }
}
