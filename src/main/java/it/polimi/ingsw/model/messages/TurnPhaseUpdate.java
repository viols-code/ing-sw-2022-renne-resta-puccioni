package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * Update: the turn phase of the game has changed
 */
public class TurnPhaseUpdate extends GameUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 1437513789242382023L;
    /**
     * The turn phase of the game
     */
    private final TurnPhase turnPhase;

    /**
     * Constructor
     *
     * @param turnPhase the turn phase of the game
     */
    public TurnPhaseUpdate(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
    }

    /**
     * Update the view with the turn phase of the game
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateTurnPhase(turnPhase);
    }
}
