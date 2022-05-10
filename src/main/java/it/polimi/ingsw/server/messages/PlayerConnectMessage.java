package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * ServerMessage notifying all clients of a new client connection.
 */
public class PlayerConnectMessage extends ServerMessage {
    @Serial
    private static final long serialVersionUID = -8678594154824429984L;

    private final String playerName;
    private final Wizard wizard;
    private final int currentPlayers;
    private final Integer playersToStart;

    /**
     * Constructs a new PlayerConnectMessage for the player with the given name.
     *
     * @param playerName     the name of the player that just connected
     * @param currentPlayers the current number of players in the lobby
     * @param playersToStart the number of players required to start the game in the lobby
     */
    public PlayerConnectMessage(String playerName, Wizard wizard, int currentPlayers, Integer playersToStart) {
        this.playerName = playerName;
        this.wizard = wizard;
        this.currentPlayers = currentPlayers;
        this.playersToStart = playersToStart;
    }

    @Override
    public void process(View view) {
        view.handlePlayerConnect(playerName, wizard, currentPlayers, playersToStart);
    }
}
