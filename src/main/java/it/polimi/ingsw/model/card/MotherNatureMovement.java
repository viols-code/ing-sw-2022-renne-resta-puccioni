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
     * Contructor
     * @param game
     */
    public MotherNatureMovement(Game game) {
        super(game);
    }

    /**
     * Activates the effect of the CharacterCard
     */
    public void effect(){

        possibleMovement = game.getCurrentPlayer().getCurrentAssistantCard().getMotherNatureMovement() + 2;
        newCard = new AssistantCard(game.getCurrentPlayer().getCurrentAssistantCard().getValue(), possibleMovement);
        game.getCurrentPlayer().setCurrentAssistantCard(newCard);

        game.setActiveCharacterCard(game.getBasicState());

    }
}
