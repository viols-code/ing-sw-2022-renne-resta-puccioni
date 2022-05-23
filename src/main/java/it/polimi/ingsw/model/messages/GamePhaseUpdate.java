package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.game.GamePhase;
import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * Update: the phase of the game has changed
 */
public class GamePhaseUpdate extends GameUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 7187510051232342083L;
    /**
     * The phase of the game
     */
    private final GamePhase gamePhase;

    /**
     * Constructor
     *
     * @param gamePhase the phase of the game
     */
    public GamePhaseUpdate(GamePhase gamePhase) {
        this.gamePhase = gamePhase;
    }

    /**
     * Update the view with the new phase of the game
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateGamePhase(gamePhase);
    }
}
