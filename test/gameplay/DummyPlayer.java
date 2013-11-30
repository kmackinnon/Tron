/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameplay;

import database.GameInfo;
import database.User;

/**
 * Dummy version of Player class to help with unit testing
 *
 * @author Gabriel
 */
public class DummyPlayer extends gameplay.Player {

    public DummyPlayer(User user, String colour, GameInfo game) {
        super(user, colour, game);
    }
}
