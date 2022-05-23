package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * DirectServerMessage notifying to all the clients the game mode chosen
 */
public class GameModeSetMessage extends DirectServerMessage {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 6337210376928226127L;

    /**
     * Constructs a new GameModeSetMessage for the given recipient
     *
     * @param recipient the client connection that this message will be sent to
     */
    public GameModeSetMessage(SocketClientConnection recipient) {
        super(recipient);
    }

    /**
     * Notifying to all the clients the game mode chosen
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.handleSetGameMode();
    }
}
