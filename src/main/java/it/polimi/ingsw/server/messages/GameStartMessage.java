package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * ServerMessage notifying game start to clients
 */
public class GameStartMessage extends ServerMessage {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 6749380507234104334L;
    /**
     * The gameMode
     */
    private final boolean gameMode;

    /**
     * Constructs a new GameStartMessage
     */
    public GameStartMessage(boolean gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * Notifying game start to clients
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.handleGameStart(gameMode);
    }

}
