package it.polimi.ingsw.client.messages;

import it.polimi.ingsw.server.LobbyController;

import java.io.Serial;

/**
 * ClientMessage notifying the server of the chosen GameConfig.
 */
public class GameModeMessage extends ClientMessage {

    @Serial
    private static final long serialVersionUID = -564375582074907221L;

    private final boolean gameMode;

    /**
     * Constructs a new GameModeMessage.
     *
     * @param gameMode the game mode
     */
    public GameModeMessage(boolean gameMode) {
        this.gameMode = gameMode;
    }

    public boolean getGameMode() {
        return gameMode;
    }

    @Override
    public void process(LobbyController controller) {
        controller.setGameMode(getClientConnection(), gameMode);
    }
}
