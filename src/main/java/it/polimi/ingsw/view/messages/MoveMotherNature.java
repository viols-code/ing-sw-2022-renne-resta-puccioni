package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;

import java.io.Serial;

/**
 * Message to the server notifying the mother nature movement
 */
public class MoveMotherNature extends PlayerAction {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -695696550449539585L;
    /**
     * The mother nature movement chosen
     */
    private final int movement;

    /**
     * Constructor
     *
     * @param player the player's nickname
     * @param movement the mother nature movement chosen
     */
    public MoveMotherNature(String player, int movement) {
        super(player);
        this.movement = movement;
    }

    /**
     * Process the message by calling a method in the gameController
     *
     * @param gameController the controller that will process the packet
     */
    public void process(GameController gameController) {
        gameController.moveMotherNature(getPlayer(), movement);
    }
}
