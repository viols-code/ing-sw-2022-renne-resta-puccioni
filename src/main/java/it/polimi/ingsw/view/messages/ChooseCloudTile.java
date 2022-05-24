package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;

import java.io.Serial;

/**
 * Message to the server notifying the chosen CloudTile
 */
public class ChooseCloudTile extends PlayerAction {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -695696550349539585L;
    /**
     * The chosen CloudTile's position
     */
    private final int cloudTile;

    /**
     * Constructor
     *
     * @param player the player's nickname
     * @param cloudTile the chosen CloudTile's position
     */
    public ChooseCloudTile(String player, int cloudTile) {
        super(player);
        this.cloudTile = cloudTile;
    }

    /**
     * Process the message by calling a method in the gameController
     *
     * @param gameController the controller that will process the packet
     */
    public void process(GameController gameController) {
        gameController.chooseCloudTile(getPlayer(), cloudTile);
    }
}
