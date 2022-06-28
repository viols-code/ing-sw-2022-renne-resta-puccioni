package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * DirectServerMessage notifying a client of its successful connection
 */
public class CorrectReconnection extends DirectServerMessage {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -795696530449639554L;

    /**
     * Constructs a new CorrectConnectionMessage for the given client connection
     *
     * @param recipient the client connection to send this message to
     */
    public CorrectReconnection(SocketClientConnection recipient) {
        super(recipient);
    }

    /**
     * Notifying a client of its successful connection
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.correctReconnection();
    }
}