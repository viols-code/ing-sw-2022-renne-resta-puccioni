package it.polimi.ingsw.view.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which represents the local copy of the group island in the game
 */
public class MockGroupIsland {
    /**
     * A list containing the single islands of the group island
     */
    private final List<MockSingleIsland> islands;

    /**
     * A boolean that states if the group island is basic or advanced
     */
    private final boolean isBasic;

    /**
     * An integer that indicates the number of no entry tiles on this group island
     */
    private int noEntryTile;

    /**
     * A boolean that states if mother nature is on this island
     */
    private boolean motherNature;

    /**
     * Constructs the group island
     *
     * @param isBasic true if the group island is basic, false if the group island is advanced
     */
    public MockGroupIsland(boolean isBasic) {
        this.isBasic = isBasic;
        islands = new ArrayList<>();
        noEntryTile = 0;
    }

    /**
     * Gets the island contained in this group island
     *
     * @return the ArrayList of single islands of this group island
     */
    public List<MockSingleIsland> getIslands() {
        return islands;
    }

    /**
     * Gets the single island at the given position
     *
     * @param index the position of the single island in the list
     * @return the single island
     */
    public MockSingleIsland getSingleIslandByIndex(int index){
        return islands.get(index);
    }

    /**
     * Adds a single island to this group island
     *
     * @param singleIsland the single island
     */
    public void addMockSingleIsland(MockSingleIsland singleIsland) {
        islands.add(singleIsland);
    }

    /**
     * Gets the value of the attribute is basic
     *
     * @return true if the group island is basic, false if the group island is advanced
     */
    public boolean getIsBasic() {
        return isBasic;
    }

    /**
     * Gets the number of no entry tile on this group island
     *
     * @return the number of no entry tile
     */
    public int getNoEntryTile() {
        return noEntryTile;
    }

    /**
     * Sets the number of no entry tile
     *
     * @param noEntryTile the number of no entry tile
     */
    public void setNoEntryTile(int noEntryTile) {
        this.noEntryTile = noEntryTile;
    }

    public boolean isMotherNature() {
        return motherNature;
    }

    public void setMotherNature(boolean motherNature) {
        this.motherNature = motherNature;
    }
}
