package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;

import java.io.Serial;

/**
 * Message to the Server for setting ColourDiningRoom and ColourEntrance as attributes of the CharacterCard
 */
public class SetColourDiningRoomEntrance extends ChangeCharacterCardState {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -695696551399539585L;
    /**
     * The chosen colour from the dining room
     */
    private final Colour colourDiningRoom;
    /**
     * The chosen colour from the entrance
     */
    private final Colour colourEntrance;

    /**
     * Constructor
     *
     * @param player           the player's nickname
     * @param colourDiningRoom the chosen colour from the dining room
     * @param colourEntrance   the chosen colour from the entrance
     */
    public SetColourDiningRoomEntrance(String player, Colour colourDiningRoom, Colour colourEntrance) {
        super(player);
        this.colourDiningRoom = colourDiningRoom;
        this.colourEntrance = colourEntrance;
    }

    /**
     * Process the message by calling a method in the gameController
     *
     * @param gameController the gameController
     */
    @Override
    public void process(GameController gameController) {
        gameController.setColourDiningRoomEntrance(getPlayer(), colourDiningRoom, colourEntrance);
    }
}
