package Gameplay;

public abstract class Input {
    private int key;
    private Player player;
    
    public int checkKey(){
        return key;
    }
    
    public boolean isKey(int key) {
        return key == this.key;
    }
    
    public abstract void command();
}