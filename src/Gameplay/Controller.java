package Gameplay;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import java.util.concurrent.ConcurrentHashMap;

public class Controller implements EventHandler<KeyEvent> {

    private static final Controller INSTANCE = new Controller();
    /**
     *
     * @element-type Input
     */
    static ConcurrentHashMap<KeyCode, Input> inputList = new ConcurrentHashMap<>();

    private Controller() {
    }

    public static Controller getInstance() {
        return INSTANCE;
    }

    public void clear() {
        inputList.clear();
    }

    public void addBinding(Input input) {
        inputList.put(input.checkKey(), input);
    }

    @Override
    public void handle(KeyEvent e) {
        Input input = inputList.get(e.getCode());
        
        // Need try-catch in case user types keys other than expected
        try {
            input.command(); 
        } catch (NullPointerException ex){
            // do nothing
        }
    }

}
