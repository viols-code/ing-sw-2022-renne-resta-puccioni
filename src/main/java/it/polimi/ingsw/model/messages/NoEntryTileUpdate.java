package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * Update: the no entry tiles on a group island have changed
 */
public class NoEntryTileUpdate extends GroupIslandUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 8811455658820901831L;
    /**
     * The group island
     */
    private final int groupIsland;
    /**
     * The number of no entry tile
     */
    private final int numberOfNoEntryTiles;

    /**
     * Constructor
     *
     * @param groupIsland the group island
     * @param numberOfNoEntryTiles the number of no entry tiles
     */
    public NoEntryTileUpdate(int groupIsland, int numberOfNoEntryTiles) {
        this.groupIsland = groupIsland;
        this.numberOfNoEntryTiles = numberOfNoEntryTiles;
    }

    /**
     * Update the view with the number of no entry tiles on the group island
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateNoEntryTileOnGroupIsland(groupIsland, numberOfNoEntryTiles);
    }
}
