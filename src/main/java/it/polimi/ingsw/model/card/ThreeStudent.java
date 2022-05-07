package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;

public class ThreeStudent extends CharacterCard {
    private Colour colour;

    /**
     * Constructor
     *
     * @param game the Game
     */
    public ThreeStudent(Game game) {
        super(game);
        actualCost = 3;
        type = CharacterCardEnumeration.THREE_STUDENT;
    }

    /**
     * Activates the effect of the CharacterCard
     */
    @Override
    protected void effect() {
        for (int i = 0; i < game.getNumberOfPlayer(); i++) {
            if (game.getPlayerByIndex(i).getSchoolBoard().getDiningRoom(colour) < 3) {
                game.getPlayerByIndex(i).getSchoolBoard().removeAllStudentFromDiningRoom(colour);
            } else {
                for (int j = 0; j < 3; j++) {
                    game.getPlayerByIndex(i).getSchoolBoard().removeStudentFromDiningRoom(colour);
                }
            }
        }

        game.setActiveCharacterCard(game.getBasicState());
    }

    /**
     * Set the colour
     *
     * @param colour the colour to be set
     */
    @Override
    public void setColour(Colour colour) {
        this.colour = colour;
        this.effect();
    }
}
