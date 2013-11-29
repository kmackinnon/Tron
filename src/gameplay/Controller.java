package gameplay;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Handles user key input and binds it to the input list
 * @author Gabriel
 */
public class Controller implements EventHandler<KeyEvent> {

    private static final Controller INSTANCE = new Controller();
    /**
     *
     * @element-type Input
     */
    static ConcurrentHashMap<KeyCode, Input> inputList = new ConcurrentHashMap<>();

    // to implement a singleton pattern
    protected Controller() {  
    }

    /**
     *
     * @return the instance of Controller
     */
    public static Controller getInstance() {
        return INSTANCE;
    }

    /**
     * Resets the inputList.
     */
    public void clear() {
        inputList.clear();
    }

    /**
     * Check the user-entered key and add it to the inputList
     * @param input
     */
    public void addBinding(Input input) {
        inputList.put(input.checkKey(), input);
    }

    /**
     * Tries to process the user-entered keys.
     *
     * @param e the key pressed
     */
    @Override
    public void handle(KeyEvent e) {
        Input input = inputList.get(e.getCode());

        // Need try-catch in case user types keys other than expected
        try {
            input.command();
        } catch (NullPointerException ex) {
            // do nothing
        }
    }

}
