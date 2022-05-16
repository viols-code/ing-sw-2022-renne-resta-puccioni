package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;

public class BasicState extends CharacterCard {
    /**
     * Constructor: creates the BasicState
     *
     * @param game the Game
     */
    public BasicState(Game game) {
        super(game);
        type = CharacterCardEnumeration.BASIC_STATE;
    }
}
