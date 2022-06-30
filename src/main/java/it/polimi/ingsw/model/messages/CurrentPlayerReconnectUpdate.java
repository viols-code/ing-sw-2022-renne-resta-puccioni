package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.view.View;

import java.io.Serial;

public class CurrentPlayerReconnectUpdate extends DirectReconnectionMessage{
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -3412945893111111111L;
    /**
     * The current player
     */
    private final String nickname;

    /**
     * Constructs a new DirectReconnectionMessage for the given recipient
     *
     * @param recipient the client connection that this message will be sent to
     */
    public CurrentPlayerReconnectUpdate(SocketClientConnection recipient, String nickname) {
        super(recipient);
        this.nickname = nickname;
    }

    /**
     * Update the view with the current player information
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateCurrentPlayerReconnection(nickname);
    }

}
