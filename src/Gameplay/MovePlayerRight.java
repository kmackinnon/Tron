package Gameplay;

public class MovePlayerRight extends Input {
	
	public MovePlayerRight(Integer key, Player player) {
		super(key, player);
	}

	public void command() {
		this.player.moveRight();
	}
}
