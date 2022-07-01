package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * ServerMessage that notify the client that a player reconnected
 */
public class PlayerReconnectedMessage extends ServerMessage {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -795698530449639554L;

    /**
     * The nickname of the player reconnected
     */
    public final String playerName;

    /**
     * Constructor of the message
     *
     * @param playerName the nickname of the player reconnected
     */
    public PlayerReconnectedMessage(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Command to be processed by the view
     *
     * @param view the view that has to process the command
     */
    public void process(View view) {
        view.handlePlayerReconnect(playerName);
    }
}
