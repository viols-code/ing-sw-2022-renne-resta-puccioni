package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.game.Game;

public class MotherNatureMovement extends CharacterCard {

    int possibleMovement;

    public MotherNatureMovement(Game game) {
        super(game);
    }

    public void effect(){
         possibleMovement = game.getCurrentPlayer().getCurrentAssistantCard().getMotherNatureMovement() + 2;

         /*
         to do
          */
    }
}
