package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * ServerMessage notifying game start to clients.
 */
public class GameStartMessage extends ServerMessage {
    @Serial
    private static final long serialVersionUID = 6749380507234104334L;

    //private final GameConfig gameConfig;

    /**
     * Constructs a new GameStartMessage.
     *
     */
    /*
    public GameStartMessage(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

     */

    @Override
    public void process(View view) {
       // view.handleGameStart(gameConfig);
        System.out.println("Il gioco inizia");
    }

}
