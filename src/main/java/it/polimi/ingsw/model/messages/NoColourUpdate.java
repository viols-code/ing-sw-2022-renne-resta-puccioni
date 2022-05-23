package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * Update: the colour chosen not to be included in the influence computation
 */
public class NoColourUpdate extends CardUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 9111746659820901831L;
    /**
     * The colour chosen not to be included in the influence computation
     */
    private final Colour colour;

    /**
     * Constructor
     *
     * @param colour the colour chosen not to be included in the influence computation
     */
    public NoColourUpdate(Colour colour) {
        this.colour = colour;
    }

    /**
     * Update the view with the colour chosen not to be included in the influence computation
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getRenderer().showGameMessage("In this turn colour " + colour + " will not be considered in influence computation");
    }
}
