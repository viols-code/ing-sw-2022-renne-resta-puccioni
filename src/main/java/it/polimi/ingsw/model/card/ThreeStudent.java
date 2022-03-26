package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;

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
    }

    /**
     * Activates the effect of the CharacterCard
     */
    @Override
    public void effect() {
        int n;
        for(int i=0; i< game.getNumberOfPlayer();i++) {
            n = game.getPlayerByIndex(i).getSchoolBoard().getDiningRoom(colour);
            for (int j = 0; j < n && j < 3; j++) {
                game.getPlayerByIndex(i).getSchoolBoard().removeStudentFromDiningRoom(colour);
            }
        }




            /*if (game.getPlayerByIndex(i).getSchoolBoard().getDiningRoom(colour) < 3) {
                game.getPlayerByIndex(i).getSchoolBoard().removeAllStudentFromDiningRoom();
            } else {
                for (int j = 0; j < 3; j++) {
                    game.getPlayerByIndex(i).getSchoolBoard().removeStudentFromDiningRoom(colour);
                }
            }
        }*/

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
