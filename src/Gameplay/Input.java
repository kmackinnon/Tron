package Gameplay;

import java.awt.event.KeyEvent;

public abstract class Input {
	private KeyEvent key;
	protected Player player;

	public KeyEvent checkKey() {
		return key;
	}

	public boolean isKey(KeyEvent key) {
		return key == this.key;
	}

	public abstract void command();

	public Input(KeyEvent key, Player player) {
		this.key = key;
		this.player = player;
	}
}