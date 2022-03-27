package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.game.Game;

public class MotherNatureMovement extends CharacterCard {

    /**
     * Constructor
     *
     * @param game the Game
     */
    public MotherNatureMovement(Game game) {
        super(game);
        actualCost = 1;
    }

    @Override
    public boolean checkMotherNatureMovement(int player, int movement) {
        return game.getPlayerByIndex(player).getCurrentAssistantCard().getMotherNatureMovement() + 2 >= movement;
    }
}
