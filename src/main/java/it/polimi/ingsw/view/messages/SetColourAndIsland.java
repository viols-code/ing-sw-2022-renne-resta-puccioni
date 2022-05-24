package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;

import java.io.Serial;

/**
 * Message to notify the selected colour and the selected island to activate the effect of the character card StudentToIsland
 */
public class SetColourAndIsland extends ChangeCharacterCardState {

    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -695696551349539585L;

    /**
     * The nickname of the player
     */
    private String player;

    /**
     * The selected colour
     */
    private final Colour colour;

    /**
     * The index of the group island selected
     */
    private final int groupIsland;

    /**
     * The index of the single island selected
     */
    private final int singleIsland;

    /**
     * Constructs the message
     *
     * @param player the nickname of the player
     * @param colour the colour selected
     * @param groupIsland the index of the group island selected
     * @param singleIsland the index of the single island selected
     */
    public SetColourAndIsland(String player, Colour colour, int groupIsland, int singleIsland) {
        this.player = player;
        this.colour = colour;
        this.groupIsland = groupIsland;
        this.singleIsland = singleIsland;
    }

    /**
     * Notifies the game controller with the selected student and island
     *
     * @param gameController the controller that will process the message
     */
    public void process(GameController gameController) {
        gameController.setColourAndIsland(player, colour, groupIsland, singleIsland);
    }
}
