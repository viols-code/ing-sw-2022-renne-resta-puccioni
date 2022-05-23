package it.polimi.ingsw.client.messages;

import it.polimi.ingsw.server.LobbyController;

import java.io.Serial;

/**
 * ClientMessage notifying the server of the chosen username
 */
public class PlayerNameMessage extends ClientMessage {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 7401855340201090917L;
    /**
     * The player's nickname
     */
    private final String playerName;

    /**
     * Constructs a new PlayerNameMessage.
     *
     * @param playerName the player name that the client has chosen
     */
    public PlayerNameMessage(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Gets the player's nickname
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Processes the packet
     *
     * @param lobbyController the lobbyController that will process the packet
     */
    @Override
    public void process(LobbyController lobbyController) {
        lobbyController.setPlayerName(getClientConnection(), playerName);
    }
}
