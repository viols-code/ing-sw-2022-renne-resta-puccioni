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
        initialCost = 2;
        actualCost = initialCost;
    }

    /**
     * Checks if the current player can take the control of the professor selected
     *
     * @param colour the colour of the professor selected
     */
    @Override
    public void checkProfessor(Colour colour) {
        boolean control = true;
        for (int i = 0; i < game.getNumberOfPlayer(); i++) {
            if(game.getPlayerByIndex(i).getSchoolBoard().hasProfessor(colour) && !game.getPlayerByIndex(i).equals(game.getCurrentPlayer())){
                control = false;
                if (game.getCurrentPlayer().getSchoolBoard().getDiningRoom(colour) >= game.getPlayerByIndex(i).getSchoolBoard().getDiningRoom(colour)){
                    game.getCurrentPlayer().getSchoolBoard().addProfessor(colour);
                    game.getPlayerByIndex(i).getSchoolBoard().removeProfessor(colour);
                }
            }
        }

        if(control){
            boolean check = true;
            for (int i = 0; i < game.getNumberOfPlayer(); i++) {
                if (game.getCurrentPlayer().getSchoolBoard().getDiningRoom(colour) < game.getPlayerByIndex(i).getSchoolBoard().getDiningRoom(colour)
                        && !game.getPlayerByIndex(i).equals(game.getCurrentPlayer())){
                    check = false;
                }
            }
            if(check){
                game.getCurrentPlayer().getSchoolBoard().addProfessor(colour);
            }
        }
    }

    /**
     * Checks if the current player can take the control of the professors
     */
    @Override
    public void professor() {
        for(Colour colour: Colour.values()){
            this.checkProfessor(colour);
        }
    }
}
