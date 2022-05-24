package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;

import java.io.Serial;

/**
 * Message to notify the chosen steps for mother nature
 */
public class MoveMotherNature extends PlayerAction {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -695696550449539585L;

    /**
     * The nickname of the player
     */
    private String player;

    /**
     * The steps chosen for mother nature
     */
    private final int movement;

    /**
     * Constructs the message
     *
     * @param player the nickname of the player
     * @param movement the steps mother nature has to do
     */
    public MoveMotherNature(String player, int movement) {
        this.player = player;
        this.movement = movement;
    }

    /**
     * Notifies the game controller with the chosen steps for mother nature
     *
     * @param gameController the game controller that will process the message
     */
    public void process(GameController gameController) {
        gameController.moveMotherNature(player, movement);
    }
}
