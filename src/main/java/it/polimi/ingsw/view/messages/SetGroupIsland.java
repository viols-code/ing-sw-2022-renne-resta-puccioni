package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;

import java.io.Serial;

/**
 * Message to the Server for setting GroupIsland as an attribute of the CharacterCard
 */
public class SetGroupIsland extends ChangeCharacterCardState {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 9204228452747768886L;
    /**
     * The chosen GroupIsland's position
     */
    private final int groupIsland;

    /**
     * Constructor
     *
     * @param player      the player's nickname
     * @param groupIsland the chosen GroupIsland's position
     */
    public SetGroupIsland(String player, int groupIsland) {
        super(player);
        this.groupIsland = groupIsland;
    }

    /**
     * Process the message by calling a method in the gameController
     *
     * @param gameController the gameController
     */
    @Override
    public void process(GameController gameController) {
        gameController.setGroupIsland(getPlayer(), groupIsland);
    }
}
