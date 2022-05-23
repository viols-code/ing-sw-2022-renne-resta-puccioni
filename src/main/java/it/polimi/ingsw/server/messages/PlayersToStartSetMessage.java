package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * DirectServerMessage notifying a client that the number of players to start has been set correctly.
 */
public class PlayersToStartSetMessage extends DirectServerMessage {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -4320604017996942648L;
    /**
     * The players needed to start a game
     */
    private final int playersToStart;

    /**
     * Constructs a new PlayersToStartSetMessage
     *
     * @param recipient      the client to send this message to
     * @param playersToStart number that has been set
     */
    public PlayersToStartSetMessage(SocketClientConnection recipient, int playersToStart) {
        super(recipient);
        this.playersToStart = playersToStart;
    }

    /**
     * Notifying a client that the number of players to start has been set correctly.
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.handleSetPlayersToStart(playersToStart);
    }
}
