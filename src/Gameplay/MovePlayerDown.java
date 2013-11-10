package Gameplay;

public class MovePlayerDown extends Input {
	
	public MovePlayerDown(Integer key, Player player) {
		super(key, player);
	}
        
        @Override
	public void command() {
		this.player.moveDown();
	}
}
