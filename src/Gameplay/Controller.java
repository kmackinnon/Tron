package Gameplay;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class Controller extends KeyAdapter {

	/**
	 * 
	 * @element-type Input
	 */
	static HashMap<int, Input> inputList = new HashMap<>();

	public Controller() {
	}

        public void clear() {
          inputList.clear();
        }

	public void addBinding(Input input) {
		inputList.put(input.checkKey(), input);
	}

	public void keyPressed(KeyEvent e) {
		Input input = inputList.get(e.getKeyCode());
		input.command();
	}

}
