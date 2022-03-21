package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;

public class TakeProfessor extends CharacterCard {
    /**
     * Constructor
     *
     * @param game
     */
    public TakeProfessor(Game game) {
        super(game);
    }

    /**
     * Take the professor of the colour given if possible
     *
     * @param colour
     */
    @Override
    public void checkProfessor(Colour colour){
        for(int i = 0; i<game.getNumberOfPlayer(); i++){
                if (game.getCurrentPlayer().getSchoolBoard().getDiningRoom(colour) >= game.getPlayerByIndex(i).getSchoolBoard().getDiningRoom(colour)) {
                    game.getCurrentPlayer().getSchoolBoard().hasProfessor(colour);
                }
        }

        game.setActiveCharacterCard(game.getBasicState());
    }
}
