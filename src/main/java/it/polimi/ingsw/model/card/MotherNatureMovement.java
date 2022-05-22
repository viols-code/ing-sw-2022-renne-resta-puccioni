package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;

public class MotherNatureMovement extends CharacterCard {

    /**
     * Constructor
     *
     * @param game the Game
     */
    public MotherNatureMovement(Game game) {
        super(game);
        actualCost = 1;
        type = CharacterCardEnumeration.MOTHER_NATURE_MOVEMENT;
    }

    /**
     * Return true if the movement is smaller than the constraints, false otherwise
     *
     * @param player   the player who wants to move mother nature
     * @param movement the steps required for mother nature
     * @return true if the movement is smaller than the constraints, false otherwise
     */
    @Override
    public boolean checkMotherNatureMovement(int player, int movement) {
        return game.getPlayerByIndex(player).getCurrentAssistantCard().getMotherNatureMovement() + 2 >= movement;
    }
}
