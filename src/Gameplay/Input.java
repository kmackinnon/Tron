package Gameplay;

public abstract class Input {
	private int key;
	protected Player player;

	public int checkKey() {
		return key;
	}

	public boolean isKey(int key) {
		return key == this.key;
	}

	public abstract void command();

	public Input(int key, Player player) {
		this.key = key;
		this.player = player;
	}
}
