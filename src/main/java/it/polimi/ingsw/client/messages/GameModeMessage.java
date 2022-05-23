package it.polimi.ingsw.client.messages;

import it.polimi.ingsw.server.LobbyController;

import java.io.Serial;

/**
 * ClientMessage notifying the server of the chosen GameMode
 */
public class GameModeMessage extends ClientMessage {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -564375582074907221L;
    /**
     * The game mode
     */
    private final boolean gameMode;

    /**
     * Constructs a new GameModeMessage.
     *
     * @param gameMode the game mode
     */
    public GameModeMessage(boolean gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * Get the game mode
     *
     * @return the game mode
     */
    public boolean getGameMode() {
        return gameMode;
    }

    /**
     * Processes the packet
     *
     * @param controller the controller that will process the packet
     */
    @Override
    public void process(LobbyController controller) {
        controller.setGameMode(getClientConnection(), gameMode);
    }
}
