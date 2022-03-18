package it.polimi.ingsw.model.island;

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
    public int getNumberOfNoEntryTile() {
        return numberOfNoEntryTile;
    }

    /**
     * Add one no entry tile
     */
    public void addNoEntryTile() {
        numberOfNoEntryTile += 1;
    }

    /**
     * Remove one no entry tile
     */
    public void removeNoEntryTile() throws IllegalAccessError {
        numberOfNoEntryTile -= 1;

        if (numberOfNoEntryTile == 0) {
            this.noEntryTile = false;
        }
    }
}
