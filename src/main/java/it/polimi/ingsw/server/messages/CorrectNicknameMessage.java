package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.List;

/**
 * DirectServerMessage notifying a client that they set successfully the nickname
 */
public class CorrectNicknameMessage extends DirectServerMessage {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -8678594154824429984L;
    /**
     * The nickname of the player
     */
    private final String playerName;
    /**
     * The list of nicknames already taken
     */
    private final List<String> takenNicknames;

    /**
     * Constructs a new PlayerConnectMessage for the player with the given name
     *
     * @param recipient      the client connection to send this message to
     * @param playerName     the name of the player that just connected
     * @param takenNicknames the list of nicknames already taken
     */
    public CorrectNicknameMessage(SocketClientConnection recipient, String playerName, List<String> takenNicknames) {
        super(recipient);
        this.playerName = playerName;
        this.takenNicknames = takenNicknames;
    }

    /**
     * Notifying a client that they set successfully the nickname
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.handleCorrectNickname(playerName, takenNicknames);
    }
}