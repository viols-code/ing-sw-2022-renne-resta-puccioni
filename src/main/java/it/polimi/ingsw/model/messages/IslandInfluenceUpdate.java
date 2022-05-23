package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * Update: the group island chosen for an extra influence computation
 */
public class IslandInfluenceUpdate extends CardUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -1023219829671352198L;
    /**
     * The position of the group island on the list
     */
    private final int groupIsland;

    /**
     * Constructor
     *
     * @param groupIsland the group island chosen for an extra influence computation
     */
    public IslandInfluenceUpdate(int groupIsland) {
        this.groupIsland = groupIsland;
    }

    /**
     * Update the view with the group island chosen
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getRenderer().showGameMessage("Island chosen for an extra influence computation: " + groupIsland);
    }
}
