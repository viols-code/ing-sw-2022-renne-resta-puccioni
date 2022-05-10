package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

public class GameCanStartMessage extends ServerMessage{
    @Serial
    private static final long serialVersionUID = 4690775844763528403L;

    /**
     * Constructs a new GameStartMessage.
     */
    public GameCanStartMessage() {

    }

    @Override
    public void process(View view) {
        view.handleGameCanStartMessage();
    }
}
