package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * Update: a new student on a single island
 */
public class SingleIslandUpdate extends TableUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 7611475669820901831L;
    /**
     * The group island
     */
    private final int groupIsland;
    /**
     * The single island
     */
    private final int singleIsland;
    /**
     * The colour of the student
     */
    private final Colour colour;

    /**
     * Constructor
     *
     * @param groupIsland the group island
     * @param singleIsland the single island
     * @param colour the colour
     */
    public SingleIslandUpdate(int groupIsland, int singleIsland, Colour colour) {
        this.groupIsland = groupIsland;
        this.singleIsland = singleIsland;
        this.colour = colour;
    }

    /**
     * Update the view with the student on the single island
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateSingleIsland(groupIsland, singleIsland, colour);
    }
}
