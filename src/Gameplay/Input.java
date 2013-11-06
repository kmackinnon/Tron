package Gameplay;
import java.awt.event.KeyEvent; 

public abstract class Input {
    private KeyEvent key;
    private Player player;
    
    public KeyEvent checkKey(){
        return key;
    }
    
    public boolean isKey(KeyEvent key) {
        return key == this.key;
    }
    
    public abstract void command();
    
    public Input(int key, Controller control){
        
    }
}