package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;

public class TakeProfessor extends CharacterCard {
    /**
     * Constructor
     *
     * @param game the Game
     */
    public TakeProfessor(Game game) {
        super(game);
    }

    /**
     * Checks if the current player can take the control of the professor selected
     *
     * @param colour the colour of the professor selected
     */
    @Override
    public void checkProfessor(Colour colour) {
        for (int i = 0; i < game.getNumberOfPlayer(); i++) {
            if (game.getPlayerByIndex(i).getSchoolBoard().hasProfessor(colour) && game.getCurrentPlayer().getSchoolBoard().getDiningRoom(colour) >= game.getPlayerByIndex(i).getSchoolBoard().getDiningRoom(colour)){
                game.getCurrentPlayer().getSchoolBoard().addProfessor(colour);
                game.getPlayerByIndex(i).getSchoolBoard().removeProfessor(colour);
            }
        }
    }
}
