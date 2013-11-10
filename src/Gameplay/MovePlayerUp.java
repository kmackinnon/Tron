package Gameplay;

public class MovePlayerUp extends Input {
	
	public MovePlayerUp(Integer key, Player player) {
		super(key, player);
	}

	public void command() {
		this.player.moveUp();
	}
}
