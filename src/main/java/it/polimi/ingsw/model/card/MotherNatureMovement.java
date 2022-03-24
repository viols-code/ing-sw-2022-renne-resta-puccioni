package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.game.Game;

public class MotherNatureMovement extends CharacterCard {

    /**
     * New movement allowed for motherNatured
     */
    int possibleMovement;

    /**
     * New assistantCard with the updated motherNatureMovement
     */
    AssistantCard newCard;

    /**
     * Constructor
     *
     * @param game the Game
     */
    public MotherNatureMovement(Game game) {
        super(game);
        initialCost = 1;
        actualCost = initialCost;
    }

    @Override
    public boolean checkMotherNatureMovement(int player, int movement) {
        return game.getPlayerByIndex(player).getCurrentAssistantCard().getMotherNatureMovement() + 2 >= movement;
    }
}
