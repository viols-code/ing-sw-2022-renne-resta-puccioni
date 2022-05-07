package it.polimi.ingsw.model.table.island;

import it.polimi.ingsw.model.messages.NoEntryTileUpdate;

public class AdvancedGroupIsland extends GroupIsland {

    /**
     * number of no entry tiles
     */
    private int numberOfNoEntryTile;

    public AdvancedGroupIsland() {
        super();
        numberOfNoEntryTile = 0;
    }

    /**
     * Get the number of no entry tile
     *
     * @return the number of no entry tiles
     */
    @Override
    public int getNumberOfNoEntryTile() {
        return numberOfNoEntryTile;
    }

    /**
     * Add one no entry tile
     */
    @Override
    public void addNoEntryTile() {
        numberOfNoEntryTile += 1;
        if (!noEntryTile) {
            this.noEntryTile = true;
        }

    }

    /**
     * Remove one no entry tile
     */
    @Override
    public void removeNoEntryTile() {
        numberOfNoEntryTile -= 1;

        if (numberOfNoEntryTile == 0) {
            this.noEntryTile = false;
        }

    }
}
