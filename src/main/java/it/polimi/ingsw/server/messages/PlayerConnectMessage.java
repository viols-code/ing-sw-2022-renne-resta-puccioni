package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.List;

/**
 * ServerMessage notifying all clients of a new client connection.
 */
public class PlayerConnectMessage extends ServerMessage {
    @Serial
    private static final long serialVersionUID = -8678594154824429984L;

    private final String playerName;
    private final int currentPlayers;
    private final int playersToStart;
    private final List<String> otherConnectedPlayers;

    /**
     * Constructs a new PlayerConnectMessage for the player with the given name.
     *
     * @param playerName     the name of the player that just connected
     * @param currentPlayers the current number of players in the lobby
     * @param playersToStart the number of players required to start the game in the lobby
     */
    public PlayerConnectMessage(String playerName, int currentPlayers, int playersToStart, List<String> otherConnectedPlayers) {
        this.playerName = playerName;
        this.currentPlayers = currentPlayers;
        this.playersToStart = playersToStart;
        this.otherConnectedPlayers = otherConnectedPlayers;
    }

    @Override
    public void process(View view) {
        view.handlePlayerConnect(playerName, currentPlayers, playersToStart, otherConnectedPlayers);
    }
}
