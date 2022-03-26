package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;

public class ExchangeEntranceDiningRoom extends CharacterCard {
    private Colour colourDiningRoom;
    private Colour colourEntrance;
    private int times;

    /**
     * Constructor
     *
     * @param game the Game
     */
    public ExchangeEntranceDiningRoom(Game game) {
        super(game);
        actualCost = 1;
    }

    /**
     * Activates the effect of the CharacterCard
     */
    @Override
    public void effect() {
        game.getCurrentPlayer().getSchoolBoard().addStudentToDiningRoom(colourDiningRoom);
        game.getCurrentPlayer().getSchoolBoard().removeStudentFromDiningRoom(colourEntrance);
        game.getCurrentPlayer().getSchoolBoard().addStudentToEntrance(colourEntrance);
        game.getCurrentPlayer().getSchoolBoard().removeStudentFromEntrance(colourDiningRoom);
        checkProfessor(colourDiningRoom);
        if (times == 2) {
            resetTimes();
            game.setActiveCharacterCard(game.getBasicState());
        } else {
            increaseTimes();
        }
    }

    /**
     * Set the colour of the student in the diningRoom and the colour of the student in the entrance to be switched
     *
     * @param colourDiningRoom the colour of the student in the diningRoom
     * @param colourEntrance   the colour of the student in the entrance
     */
    @Override
    public void setColourDiningRoomEntrance(Colour colourDiningRoom, Colour colourEntrance) {
        this.colourDiningRoom = colourDiningRoom;
        this.colourEntrance = colourEntrance;
        this.effect();
    }

    /**
     * Reset the times
     */
    private void resetTimes() {
        times = 0;
    }

    /**
     * Increase the times
     */
    private void increaseTimes() {
        times += 1;
    }
}
