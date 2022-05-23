package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * Update: the winner
 */
public class WinnerUpdate extends GameUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 2122435023892342341L;
    /**
     * The winner's nickname
     */
    private final String winner;

    /**
     * Constructor
     *
     * @param winner winner's nickname
     */
    public WinnerUpdate(String winner) {
        this.winner = winner;
    }

    /**
     * Update the view with the winner
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateWinner(winner);
    }
}
