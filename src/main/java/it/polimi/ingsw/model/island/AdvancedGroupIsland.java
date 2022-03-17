package it.polimi.ingsw.model.island;

public class AdvancedGroupIsland extends GroupIsland{
    /**
     * true if there is a no entry tile, false otherwise
     */
    private boolean noEntryTile;

    /**
     * number of no entry tiles
     */
    private int numberOfNoEntryTile;

    public AdvancedGroupIsland(){
        super();
        noEntryTile = false;
        numberOfNoEntryTile = 0;
    }

    /**
     * Get the no entry tile
     *
     * @return true if there are no entry tiles on the island, false otherwise
     */
    public boolean isNoEntryTile(){
        return noEntryTile;
    }

    /**
     * Get the number of no entry tile
     *
     * @return the number of no entry tiles
     */
    public int getNumberOfNoEntryTile(){
       return numberOfNoEntryTile;
    }

    /**
     * Add one no entry tile
     */
    public void addNoEntryTile(){
       numberOfNoEntryTile += 1;
    }

    /**
     * Remove one no entry tile
     */
    public void removeNoEntryTile() throws IllegalAccessError{
        numberOfNoEntryTile -= 1;

        if(numberOfNoEntryTile == 0){
            this.noEntryTile = false;
        }
    }
}
