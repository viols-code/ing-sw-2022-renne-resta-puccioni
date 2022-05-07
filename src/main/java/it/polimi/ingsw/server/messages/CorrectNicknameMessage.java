package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.List;

/**
 * ServerMessage notifying all clients of a new client connection.
 */
public class CorrectNicknameMessage extends ServerMessage {
    @Serial
    private static final long serialVersionUID = -8678594154824429984L;

    private final String playerName;
    private final List<String> takenNicknames;

    /**
     * Constructs a new PlayerConnectMessage for the player with the given name.
     *
     * @param playerName     the name of the player that just connected
     * @param takenNicknames the list of nicknames already taken
     */
    public CorrectNicknameMessage(String playerName, List<String> takenNicknames) {
        this.playerName = playerName;
        this.takenNicknames = takenNicknames;
    }

    @Override
    public void process(View view) {
        // view.handleCorrectNickname(playerName, takenNicknames);
        System.out.println("Ti sei connesso correttamente");
    }
}