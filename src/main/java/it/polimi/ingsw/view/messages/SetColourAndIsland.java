package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;

import java.io.Serial;

/**
 * Message to the Server for setting Colour and SingleIsland as attributes of the CharacterCard
 */
public class SetColourAndIsland extends ChangeCharacterCardState {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -695696551349539585L;
    /**
     * The chosen colour of the student
     */
    private final Colour colour;
    /**
     * The chosen GroupIsland's position
     */
    private final int groupIsland;
    /**
     * The chosen SingleIsland's position
     */
    private final int singleIsland;

    /**
     * Constructor
     *
     * @param player       the player's nickname
     * @param colour       the chosen colour of the student
     * @param groupIsland  the chosen GroupIsland's position
     * @param singleIsland the chosen SingleIsland's position
     */
    public SetColourAndIsland(String player, Colour colour, int groupIsland, int singleIsland) {
        super(player);
        this.colour = colour;
        this.groupIsland = groupIsland;
        this.singleIsland = singleIsland;
    }

    /**
     * Process the message by calling a method in the gameController
     *
     * @param gameController the gameController
     */
    public void process(GameController gameController) {
        gameController.setColourAndIsland(getPlayer(), colour, groupIsland, singleIsland);
    }
}
