package it.polimi.ingsw.model.table;

import it.polimi.ingsw.model.table.island.GroupIsland;

import java.util.ArrayList;
import java.util.List;

public class Table {
    /**
     * the bag containing all the remaining students
     */
    private final Bag bag;

    /**
     * A List containing the Groups of island in the match
     */
    private final List<GroupIsland> islands;
    /**
     * A List containing the cloudTiles
     */
    private final List<CloudTile> cloudTiles;
    /**
     * Position corresponding to the index in the List named islands
     */
    private int motherNaturePosition;

    /**
     * Constructor: creates a new game
     */
    public Table() {
        bag = new Bag();
        islands = new ArrayList<>();
        motherNaturePosition = 0;
        cloudTiles = new ArrayList<>();
    }

      /*
    BAG
     */

    /**
     * Get the bag of the game
     *
     * @return the bag of the game
     */
    public Bag getBag() {
        return bag;
    }

     /*
    ISLANDS
     */

    /**
     * Return the GroupIsland at the given index
     *
     * @param index the position of the GroupIsland to be returned
     * @return the GroupIsland at the given index
     */
    public GroupIsland getGroupIslandByIndex(int index) {
        return islands.get(index);
    }

    /**
     * Get the number of GroupIsland
     *
     * @return the number of GroupIsland
     */
    public int getNumberOfGroupIsland() {
        return islands.size();
    }

    /**
     * Add a group of islands from the list
     * to
     *
     * @param groupIsland the groupIsland to be added
     */
    public void addGroupIsland(GroupIsland groupIsland) {
        islands.add(groupIsland);
    }

    /**
     * Remove a group of islands from the list
     *
     * @param groupIsland the groupIsland to be removed
     */
    public void removeGroupIsland(GroupIsland groupIsland) {
        islands.remove(groupIsland);
    }

    /**
     * Gets the next island where mother nature has to move
     *
     * @return the next island where mother nature will pass
     */
    public int getNextGroupIslandClockWise() {
        return (getMotherNaturePosition() + 1) % islands.size();
    }

    /**
     * Get the GroupIsland before clockwise
     *
     * @param groupIsland the reference GroupIsland
     * @return the GroupIsland before clockwise
     */
    public GroupIsland getIslandAfter(int groupIsland) {
        return getGroupIslandByIndex((groupIsland + 1) % islands.size());
    }

    /**
     * Get the GroupIsland after clockwise
     *
     * @param groupIsland the reference GroupIsland
     * @return the GroupIsland after clockwise
     */
    public GroupIsland getIslandBefore(int groupIsland) {
        groupIsland = (groupIsland - 1) % islands.size();
        if (groupIsland == -1) {
            groupIsland = 11;
        }
        return getGroupIslandByIndex(groupIsland);
    }

       /*
    MOTHER NATURE
     */

    /**
     * Get the position of mother nature
     *
     * @return mother nature position
     */
    public int getMotherNaturePosition() {
        return motherNaturePosition;
    }

    /**
     * Set the position of mother nature
     *
     * @param motherNaturePosition the position to be set
     */
    public void setMotherNaturePosition(int motherNaturePosition) {
        getGroupIslandByIndex(this.motherNaturePosition).removeMotherNature();
        this.motherNaturePosition = motherNaturePosition;
        getGroupIslandByIndex(this.motherNaturePosition).placeMotherNature();
    }

     /*
    CLOUD TILE
     */

    /**
     * Get the cloud tile at the given index
     *
     * @return the Cloud Tile at the given index
     */
    public CloudTile getCloudTilesByIndex(int index) {
        return cloudTiles.get(index);
    }

    /**
     * Get the number of cloud tiles
     *
     * @return the number of cloud tiles
     */
    public int getNumberOfCloudTile() {
        return cloudTiles.size();
    }

    /**
     * Add a Cloud Tile, this is done at the beginning of a round
     *
     * @param cloudTile the cloud to be added to the game
     */
    public void addCLoudTile(CloudTile cloudTile) {
        cloudTiles.add(cloudTile);
    }

    /**
     * Remove a Cloud Tile because a player chose it
     *
     * @param cloudTile the cloud tile chosen by one player
     */
    public void removeCLoudTile(CloudTile cloudTile) {
        cloudTiles.remove(cloudTile);
    }

}
