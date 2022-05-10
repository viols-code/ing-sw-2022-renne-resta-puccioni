package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

public class GameModeMessage extends ServerMessage{
    @Serial
    private static final long serialVersionUID = 3467105844763528403L;
    private final boolean gameMode;

    /**
     * Constructs a new GameStartMessage.
     */
    public GameModeMessage(boolean gameMode) {
        this.gameMode = gameMode;
    }

    @Override
    public void process(View view) {
        view.handleGameMode(gameMode);
    }
}
