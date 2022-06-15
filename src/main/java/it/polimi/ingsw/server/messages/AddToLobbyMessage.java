package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.List;

/**
 * DirectServerMessage notifying a client of its successful connection
 */
public class AddToLobbyMessage extends DirectServerMessage {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -695696550449639574L;
    /**
     * True if the player is the first to be connected to the lobby
     */
    private final boolean firstConnection;
    /**
     * The list of wizard already taken
     */
    private final List<Wizard> takenWizard;

    /**
     * Constructs a new AddToLobbyMessage for the given client connection
     *
     * @param recipient       the client connection to send this message to
     * @param firstConnection true if the client is the first to connect to the lobby, false otherwise
     * @param takenWizard     the list of wizard already taken
     */
    public AddToLobbyMessage(SocketClientConnection recipient, boolean firstConnection, List<Wizard> takenWizard) {
        super(recipient);
        this.firstConnection = firstConnection;
        this.takenWizard = takenWizard;
    }

    /**
     * Notifying a client of its successful connection
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.addToLobby(firstConnection, takenWizard);
    }
}
