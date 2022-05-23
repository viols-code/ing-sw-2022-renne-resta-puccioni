package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * ServerMessage notifying to all the clients the game mode chosen
 */
public class GameModeMessage extends ServerMessage {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 3467105844763528403L;
    /**
     * The game mode chosen
     */
    private final boolean gameMode;

    /**
     * Constructs a new GameModeMessage
     *
     * @param gameMode the game mode chosen
     */
    public GameModeMessage(boolean gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * Notifying to all the clients the game mode chosen
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.handleGameMode(gameMode);
    }
}
