package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;

import java.io.Serial;

/**
 * Message to the Server for setting Colour as an attribute of the CharacterCard
 */
public class SetColour extends ChangeCharacterCardState {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -595694550449639585L;
    /**
     * The chosen student's colour
     */
    private final Colour colour;

    /**
     * Constructor
     *
     * @param player the player's nickname
     * @param colour the chosen student's colour
     */
    public SetColour(String player, Colour colour) {
        super(player);
        this.colour = colour;
    }

    /**
     * Process the message by calling a method in the gameController
     *
     * @param controller the gameController
     */
    @Override
    public void process(GameController controller) {
        controller.setColour(getPlayer(), colour);
    }
}
