package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;

import java.io.Serial;

/**
 * Message to notify the island chosen to activate the effect of the character card IslandInfluence
 */
public class SetGroupIsland extends ChangeCharacterCardState {

    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 9204228452747768886L;

    /**
     * The nickname of the player
     */
    private final String player;

    /**
     * The index of the group island chosen
     */
    private final int groupIsland;

    /**
     * Constructs the message
     *
     * @param player the nickname of the player
     * @param groupIsland the index of the group island chosen
     */
    public SetGroupIsland(String player, int groupIsland) {
        this.player = player;
        this.groupIsland = groupIsland;
    }

    /**
     * Notifies the game controller with the group island chosen
     *
     * @param gameController the controller that will process the message
     */
    public void process(GameController gameController) {
        gameController.setGroupIsland(player, groupIsland);
    }
}
