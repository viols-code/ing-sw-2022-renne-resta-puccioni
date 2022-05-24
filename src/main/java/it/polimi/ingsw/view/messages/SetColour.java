package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;

import java.io.Serial;

/**
 * Message to notify the colour chosen
 */
public class SetColour extends ChangeCharacterCardState {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -595694550449639585L;

    /**
     * The nickname of the player
     */
    private String player;

    /**
     * The colour chosen
     */
    private final Colour colour;

    /**
     * Constructs the message
     *
     * @param player the nickname of the player
     * @param colour the colour chosen
     */
    public SetColour(String player, Colour colour) {
        this.player = player;
        this.colour = colour;
    }

    /**
     * Notifies the game controller with the selected colour
     *
     * @param controller the controller that will process the packet
     */
    @Override
    public void process(GameController controller) {
        controller.setColour(player, colour);
    }
}
