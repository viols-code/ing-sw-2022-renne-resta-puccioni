package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.HashMap;

/**
 * DirectServerMessage notifying a client of its successful connection
 */
public class CorrectReconnectionMessage extends DirectServerMessage {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -795696530449639554L;
    /**
     * A HashMap containing the nicknames and the wizards of all players
     */
    private final HashMap<String, Wizard> players;
    /**
     * Constructs a new CorrectConnectionMessage for the given client connection
     *
     * @param recipient the client connection to send this message to
     */
    public CorrectReconnectionMessage(SocketClientConnection recipient, HashMap<String, Wizard> players) {
        super(recipient);
        this.players = players;
    }

    /**
     * Notifying a client of its successful connection
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.correctReconnection(players);
    }
}