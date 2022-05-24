package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;

import java.io.Serial;

/**
 * Message to set the students to switch for character card StudentToENtrance
 */
public class SetColourCardEntrance extends ChangeCharacterCardState {

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
     * The student on the card
     */
    private final Colour colourCard;

    /**
     * The student in the entrance
     */
    private final Colour colourEntrance;

    /**
     * Constructs the message
     *
     * @param player the nickname of the player
     * @param colourCard the student on the card
     * @param colourEntrance the student in the entrance
     */
    public SetColourCardEntrance(String player, Colour colourCard, Colour colourEntrance) {
        this.player = player;
        this.colourCard = colourCard;
        this.colourEntrance = colourEntrance;
    }

    /**
     * Notifies the game controller with the selected students to switch
     *
     * @param controller the controller that will process the packet
     */
    @Override
    public void process(GameController controller) {
        controller.setColourCardEntrance(player, colourCard, colourEntrance);
    }
}
