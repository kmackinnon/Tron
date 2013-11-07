package Gameplay;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class Controller extends KeyAdapter {

	/**
	 * 
	 * @element-type Input
	 */
	static HashMap<KeyEvent,Input> inputList = new HashMap();


	public Controller() {
	}

        public void addBinding(Input input){
            inputList.put(input.checkKey(),input);
        }
        
	public void keyPressed(KeyEvent e) {
		Input input = inputList.get(e);
                input.command();
	}

	public void keyReleased(KeyEvent e) {
		//input.keyReleased(e);
	}

}