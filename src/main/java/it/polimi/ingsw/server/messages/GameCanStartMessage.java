package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * ServerMessage notifying that the game can start
 */
public class GameCanStartMessage extends ServerMessage {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 4690775844763528403L;

    /**
     * Constructs a new GameCanStartMessage
     */
    public GameCanStartMessage() {
    }

    /**
     * Notifying that the game can start
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.handleGameCanStartMessage();
    }
}
