package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;

import java.io.Serial;

/**
 * Message to the server notifying the chosen student's Colour and the chosen SingleIsland
 */
public class MoveStudentToIsland extends PlayerAction {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 9204228452736058886L;
    /**
     * The chosen student's Colour
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
     * @param colour       the chosen student's Colour
     * @param groupIsland  the chosen GroupIsland's position
     * @param singleIsland the chosen SingleIsland's position
     */
    public MoveStudentToIsland(String player, Colour colour, int groupIsland, int singleIsland) {
        super(player);
        this.colour = colour;
        this.groupIsland = groupIsland;
        this.singleIsland = singleIsland;
    }

    /**
     * Process the message by calling a method in the gameController
     *
     * @param gameController the controller that will process the packet
     */
    @Override
    public void process(GameController gameController) {
        gameController.moveStudentToIsland(getPlayer(), colour, groupIsland, singleIsland);
    }
}
