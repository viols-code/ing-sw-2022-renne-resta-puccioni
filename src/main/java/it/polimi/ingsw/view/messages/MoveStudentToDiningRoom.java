package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;

import java.io.Serial;

/**
 * Message to the server notifying the chosen student's Colour
 */
public class MoveStudentToDiningRoom extends PlayerAction {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 9204228452737758886L;
    /**
     * The chosen student's Colour
     */
    private final Colour colour;

    /**
     * Constructor
     *
     * @param player the player's nickname
     * @param colour the chosen student's Colour
     */
    public MoveStudentToDiningRoom(String player, Colour colour) {
        super(player);
        this.colour = colour;
    }

    /**
     * Process the message by calling a method in the gameController
     *
     * @param gameController the controller that will process the packet
     */
    public void process(GameController gameController) {
        gameController.moveStudentToDiningRoom(getPlayer(), colour);
    }


}
