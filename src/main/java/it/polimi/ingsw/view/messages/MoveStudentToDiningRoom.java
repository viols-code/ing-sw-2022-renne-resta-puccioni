package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;

import java.io.Serial;

/**
 * Message to notify the update of the dining room
 */
public class MoveStudentToDiningRoom extends PlayerAction {

    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 9204228452737758886L;

    /**
     * The nickname of the player
     */
    private String player;

    /**
     * The colour of the student moved to the dining room
     */
    private final Colour colour;

    /**
     * Constructs the message
     *
     * @param player the nickname of the player
     * @param colour the colour of the student moved to the dining room
     */
    public MoveStudentToDiningRoom(String player, Colour colour) {
        this.player = player;
        this.colour = colour;
    }

    /**
     * Notifies the game controller with the colour of the student moved to the dining room
     *
     * @param gameController the game controller that will process the message
     */
    public void process(GameController gameController) {
        gameController.moveStudentToDiningRoom(player, colour);
    }


}
