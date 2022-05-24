package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;

import java.io.Serial;

/**
 * Message to notify the selected students to switch in the character card ExchangeEntranceDiningRoom
 */
public class SetColourDiningRoomEntrance extends ChangeCharacterCardState {

    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -695696551399539585L;

    /**
     * The nickname of the player
     */
    private String player;

    /**
     * The student in the dining room
     */
    private final Colour colourDiningRoom;

    /**
     * The student in the entrance
     */
    private final Colour colourEntrance;

    /**
     * Constructs the message
     *
     * @param player the nickname of the player
     * @param colourDiningRoom the student in the dining room
     * @param colourEntrance the student in the entrance
     */
    public SetColourDiningRoomEntrance(String player, Colour colourDiningRoom, Colour colourEntrance) {
        this.player = player;
        this.colourDiningRoom = colourDiningRoom;
        this.colourEntrance = colourEntrance;
    }

    /**
     * Notifies the game controller with the selected students to switch
     *
     * @param gameController the controller that will process the message
     */
    public void process(GameController gameController) {
        gameController.setColourDiningRoomEntrance(player, colourDiningRoom, colourEntrance);
    }
}
