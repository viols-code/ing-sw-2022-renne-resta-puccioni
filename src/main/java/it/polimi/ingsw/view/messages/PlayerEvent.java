package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.IProcessablePacket;
import it.polimi.ingsw.controller.GameController;

import java.io.Serial;
import java.io.Serializable;

/**
 * Abstract class representing a Player Event
 */
public abstract class PlayerEvent implements Serializable, IProcessablePacket<GameController> {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -695696550449639585L;
    /**
     * The player's nickname
     */
    private final String player;

    /**
     * Constructor
     *
     * @param player the player's nickname
     */
    public PlayerEvent(String player){
        this.player = player;
    }

    /**
     * Gets the player's nickname
     *
     * @return the player's nickname
     */
    protected String getPlayer() {
        return player;
    }
}
