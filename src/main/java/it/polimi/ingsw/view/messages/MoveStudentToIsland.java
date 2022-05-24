package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;

import java.io.Serial;

/**
 * Message to notify the update on a single island
 */
public class MoveStudentToIsland extends PlayerAction {

    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 9204228452736058886L;

    /**
     * The nickname of the player
     */
    private final String player;

    /**
     * The colour of the student moved to the island
     */
    private final Colour colour;
    private final int groupIsland;
    private final int singleIsland;

    /**
     * Constructs the message
     *
     * @param player the nickname of the player
     * @param colour the colour of the student moved to the island
     * @param groupIsland the index of the group island
     * @param singleIsland the index of the single island
     */
    public MoveStudentToIsland(String player, Colour colour, int groupIsland, int singleIsland) {
        this.player = player;
        this.colour = colour;
        this.groupIsland = groupIsland;
        this.singleIsland = singleIsland;
    }


    /**
     * Notifies the game controller with the student moved to the island
     *
     * @param gameController the game controller that will process the message
     */
    @Override
    public void process(GameController gameController) {
        gameController.moveStudentToIsland(player, colour, groupIsland, singleIsland);
    }
}
