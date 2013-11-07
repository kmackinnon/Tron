/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Gameplay;

/**
 *
 * @author mwilli40
 */
public class MovePlayerLeft extends Input {
    public void command(){
        this.player.moveLeft();
    }
}
