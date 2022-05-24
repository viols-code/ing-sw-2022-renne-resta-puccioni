package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.IProcessablePacket;
import it.polimi.ingsw.controller.GameController;

import java.io.Serial;
import java.io.Serializable;

/**
 * Abstract class that represents the player events
 */
public abstract class PlayerEvent implements Serializable, IProcessablePacket<GameController> {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -695696550449639585L;

    /**
     * The nickname of the player
     */
    private String player;

    /**
     * Gets the nickname of the player
     *
     * @return the nickname of the player
     */
    protected String getPlayer() {
        return player;
    }

    /**
     * Sets the nickname of the player
     *
     * @param player  the nickname of the player
     */
    public void setPlayer(String player) {
        this.player = player;
    }
}
