package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;

import java.io.Serial;

/**
 * Message to notify the chosen cloud tile
 */
public class ChooseCloudTile extends PlayerAction {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -695696550349539585L;

    /**
     * The nickname of the player
     */
    private String player;

    /**
     * The index of the cloud tile chosen
     */
    private final int cloudTile;

    /**
     * Constructs the message
     *
     * @param player the nickname of the player
     * @param cloudTile the index of the cloud tile chosen
     */
    public ChooseCloudTile(String player, int cloudTile) {
        this.player = player;
        this.cloudTile = cloudTile;
    }

    /**
     * Notifies the game controller with the chosen cloud tile
     *
     * @param gameController the game controller which will process the message
     */
    public void process(GameController gameController) {
        gameController.chooseCloudTile(player, cloudTile);
    }
}
