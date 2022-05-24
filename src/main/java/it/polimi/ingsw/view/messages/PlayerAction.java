package it.polimi.ingsw.view.messages;

import java.io.Serial;

/**
 * Abstract class representing a Player Action
 */
public abstract class PlayerAction extends PlayerEvent {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -695694550449639585L;

    /**
     * Constructor
     *
     * @param player the player's nickname
     */
    public PlayerAction(String player) {
        super(player);
    }
}
