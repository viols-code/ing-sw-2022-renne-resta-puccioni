package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;

import java.io.Serial;

/**
 * Message to the Server for setting ColourCard and ColourEntrance as attributes of the CharacterCard
 */
public class SetColourCardEntrance extends ChangeCharacterCardState {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -595694550449639585L;
    /**
     * The chosen colour from the card
     */
    private final Colour colourCard;
    /**
     * The chosen colour from entrance
     */
    private final Colour colourEntrance;

    /**
     * Constructor
     *
     * @param player         the player's nickname
     * @param colourCard     the chosen colour from the card
     * @param colourEntrance the chosen colour from entrance
     */
    public SetColourCardEntrance(String player, Colour colourCard, Colour colourEntrance) {
        super(player);
        this.colourCard = colourCard;
        this.colourEntrance = colourEntrance;
    }

    /**
     * Process the message by calling a method in the gameController
     *
     * @param controller the gameController
     */
    @Override
    public void process(GameController controller) {
        controller.setColourCardEntrance(getPlayer(), colourCard, colourEntrance);
    }
}
