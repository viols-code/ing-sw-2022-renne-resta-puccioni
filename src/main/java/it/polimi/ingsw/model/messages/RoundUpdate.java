package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * Update: the round has changed
 */
public class RoundUpdate extends GameUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -1089569123871352198L;
    /**
     * The count of the round
     */
    private final int round;

    /**
     * Constructor
     *
     * @param round the number of the round
     */
    public RoundUpdate(int round) {
        this.round = round;
    }

    /**
     * Update the view with the new round
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateRound(round);
    }
}
