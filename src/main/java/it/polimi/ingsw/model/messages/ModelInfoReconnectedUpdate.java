package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.game.GamePhase;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * Sends to the client the updates about the turn phase
 */
public class ModelInfoReconnectedUpdate extends DirectReconnectionMessage{

    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -795696530479639554L;

    /**
     * The turn phase
     */
    private final TurnPhase turnPhase;

    /**
     * The game phase
     */
    private final GamePhase gamePhase;

    /**
     * Constructor
     *
     * @param recipient the recipient client
     * @param turnPhase the turn phase
     * @param gamePhase the game pahse
     */
    public ModelInfoReconnectedUpdate(SocketClientConnection recipient, TurnPhase turnPhase, GamePhase gamePhase) {
        super(recipient);
        this.turnPhase = turnPhase;
        this.gamePhase = gamePhase;
    }

    /**
     * Notifying a client of its successful connection
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateTurnPhase(turnPhase);
        view.getModelUpdateHandler().updateGamePhase(gamePhase);
    }
}
