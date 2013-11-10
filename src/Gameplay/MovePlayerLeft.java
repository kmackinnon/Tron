package Gameplay;

public class MovePlayerLeft extends Input {
	
	public MovePlayerLeft(Integer key, Player player) {
		super(key, player);
	}

	public void command() {
		this.player.moveLeft();
	}
}
