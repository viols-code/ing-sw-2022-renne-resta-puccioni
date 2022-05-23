package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * Update: the current Player has changed
 */
public class CurrentPlayerUpdate extends GameUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 1037513789242382023L;
    /**
     * The nickname of the current player
     */
    private final String currentPlayer;

    /**
     * Constructor
     *
     * @param currentPlayer the nickname of the current player
     */
    public CurrentPlayerUpdate(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Update the view with the new current player
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateCurrentPlayer(currentPlayer);
    }
}
