package it.polimi.ingsw.view.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which represents the local copy of the game table
 */
public class MockTable {
    /**
     * A list containing the cloud tiles
     */
    private final List<MockCloudTile> cloudTiles;

    /**
     * A list containing the group islands
     */
    private final List<MockGroupIsland> groupIslands;

    /**
     * A boolean that indicates if the bag is empty
     */
    private boolean isBagEmpty;

    /**
     * Indicates the position of mother nature (index of the group island)
     */
    private int motherNaturePosition;

    /**
     * Constructs the table
     */
    public MockTable() {
        cloudTiles = new ArrayList<>();
        groupIslands = new ArrayList<>();
        isBagEmpty = false;
        motherNaturePosition = 0;
    }

    /**
     * Adds a cloud tile to the list
     *
     * @param cloudTile the cloud tile
     */
    public void addCloudTile(MockCloudTile cloudTile) {
        cloudTiles.add(cloudTile);
    }

    /**
     * Removes a cloud tile from the list
     *
     * @param cloudTile the index of the cloud tile in the list
     */
    public void removeCloudTileByIndex(int cloudTile) {
        cloudTiles.remove(cloudTile);
    }

    /**
     * Gets the cloud tile with a given index
     *
     * @param cloudTile the index of the cloud tile selected
     * @return the cloud tile
     */
    public MockCloudTile getCloudTileByIndex(int cloudTile) {
        return cloudTiles.get(cloudTile);
    }

    /**
     * Adds a group island to the list
     *
     * @param groupIsland the group island
     */
    public void addGroupIsland(MockGroupIsland groupIsland) {
        groupIslands.add(groupIsland);
    }

    /**
     * Gets the group island with the given index
     *
     * @param index the given index
     * @return the group island
     */
    public MockGroupIsland getGroupIslandByIndex(int index){
        return groupIslands.get(index);
    }

    /**
     * Removes a group island from the list
     *
     * @param groupIsland the position of the group island to remove
     */
    public void removeGroupIslandByIndex(int groupIsland) {
        groupIslands.remove(groupIsland);
    }

    /**
     * Unifies two group island
     *
     * @param groupIsland1 the first group island
     * @param groupIsland2 the second group island (it will be removed after unification)
     */
    public void unify(int groupIsland1, int groupIsland2) {
        for (MockSingleIsland singleIsland : groupIslands.get(groupIsland2).getIslands()) {
            groupIslands.get(groupIsland1).addMockSingleIsland(singleIsland);
        }
        groupIslands.remove(groupIsland2);
    }

    /**
     * Gets the variable isBagEmpty
     *
     * @return true if the bag is empty, false if the bag contains some students
     */
    public boolean getIsBagEmpty() {
        return isBagEmpty;
    }

    /**
     * Sets the variable isBagEmpty
     *
     * @param bagEmpty the new value of the variable
     */
    public void setBagEmpty(boolean bagEmpty) {
        isBagEmpty = bagEmpty;
    }

    /**
     * Gets the current position of mother nature
     *
     * @return an integer that indicates the current position of mother nature
     */
    public int getMotherNaturePosition() {
        return motherNaturePosition;
    }

    /**
     * Sets the position of mother nature
     *
     * @param motherNaturePosition the new position
     */
    public void setMotherNaturePosition(int motherNaturePosition) {
        this.motherNaturePosition = motherNaturePosition;
    }
}
