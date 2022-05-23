package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * Update: the professors on the table have changed
 */
public class ProfessorsUpdate extends TableUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 3311215655332702341L;
    /**
     * The colour of the professor
     */
    Colour colour;

    /**
     * Constructor
     *
     * @param colour the colour of the professor
     */
    public ProfessorsUpdate(Colour colour) {
        this.colour = colour;
    }

    /**
     * Update the view that the professor of the given colour is no longer on the table
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateProfessors(colour);
    }
}
