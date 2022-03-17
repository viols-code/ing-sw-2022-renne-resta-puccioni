package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Wizard;

public class BasicPlayer extends Player{

    /**
     * Constructor: creates a new BasicPlayer with the given nick and the given wizard
     *
     * @param nickname a string with the nickname
     * @param wizard the wizard chosen by the player
     */
    public BasicPlayer(String nickname, Wizard wizard) {
        super(nickname, wizard);
    }
}
