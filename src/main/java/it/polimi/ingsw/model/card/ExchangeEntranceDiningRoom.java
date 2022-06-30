package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;

public class ExchangeEntranceDiningRoom extends CharacterCard {
    /**
     * The colour on the dining room to be switched
     */
    private Colour colourDiningRoom;
    /**
     * The colour on the entrance to be switched
     */
    private Colour colourEntrance;
    /**
     * The times the player has already used this card
     */
    private int times;

    /**
     * Constructor
     *
     * @param game the Game
     */
    public ExchangeEntranceDiningRoom(Game game) {
        super(game);
        actualCost = 1;
        type = CharacterCardEnumeration.EXCHANGE_ENTRANCE_DINING_ROOM;
    }

    /**
     * Activates the effect of the CharacterCard
     */
    @Override
    protected void effect() throws IllegalArgumentException {
        game.getCurrentPlayer().getSchoolBoard().removeStudentFromDiningRoom(colourDiningRoom);
        game.getCurrentPlayer().getSchoolBoard().removeStudentFromEntrance(colourEntrance);
        game.getCurrentPlayer().getSchoolBoard().addStudentToEntrance(colourDiningRoom);
        game.getCurrentPlayer().getSchoolBoard().addStudentToDiningRoom(colourEntrance);

        //If the player has the right to have a coin after the exchange, it is given
        if (((game.getCurrentPlayer().getSchoolBoard().getDiningRoom(colourEntrance)) % 3) == 0) {
            game.getCurrentPlayer().addCoins(1);
            game.setCoins(game.getCoins() - 1);
        }

        checkProfessor(colourEntrance);

        if (times == 1) {
            resetTimes();
            setColourDiningRoomEntrance(null, null);
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
    public void setColourDiningRoomEntrance(Colour colourDiningRoom, Colour colourEntrance) throws IllegalArgumentException {
        if (colourDiningRoom == null && colourEntrance == null) {
            this.colourDiningRoom = null;
            this.colourEntrance = null;
        } else {
            this.colourDiningRoom = colourDiningRoom;
            this.colourEntrance = colourEntrance;
            if (game.getCurrentPlayer().getSchoolBoard().getDiningRoom(colourDiningRoom) > 0) {
                if (game.getCurrentPlayer().getSchoolBoard().getEntrance(colourEntrance) > 0) {
                    if (game.getCurrentPlayer().getSchoolBoard().getDiningRoom(colourEntrance) < 10) {
                        this.effect();
                    } else {
                        throw new IllegalArgumentException("The dining room is full for this colour");
                    }
                } else {
                    throw new IllegalArgumentException("There is no this colour in the entrance");
                }
            } else {
                throw new IllegalArgumentException("There is no this colour in the dining room");
            }
        }
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

    /**
     * Gets the colour set in the colour entrance
     *
     * @return the colour set in the colour entrance
     */
    protected Colour getColourEntrance() {
        return colourEntrance;
    }

    /**
     * Gets the colour set in the colour dining room
     *
     * @return the colour set in the colour dining room
     */
    protected Colour getColourDiningRoom() {
        return colourDiningRoom;
    }
}
