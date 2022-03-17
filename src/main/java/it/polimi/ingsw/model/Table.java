package it.polimi.ingsw.model;

import it.polimi.ingsw.model.island.BasicGroupIsland;
import it.polimi.ingsw.model.island.GroupIsland;

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
     * Position corresponding to the index in the List named islands
     */
    private int motherNaturePosition;

    /**
     * A List containing the cloudTiles
     */
    private final List<CloudTile> cloudTiles;

    /**
     * Constructor: creates a new game
     */
    public Table(){
        bag = new Bag();

        islands = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            islands.add(new BasicGroupIsland());
        }
        islands.get(0).placeMotherNature();

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
    public Bag getBag(){ return bag; }

     /*
    ISLANDS
     */

    /**
     * Get the List of GroupIsland
     *
     * @return a List containing the GroupIsland
     */
    public List<GroupIsland> getIslands() {
        return islands;
    }

    /**
     * Remove a group of islands from the list
     *
     * @param groupIsland the groupIsland to be removed
     */
    public void removeGroupIsland(GroupIsland groupIsland) {
        islands.remove(groupIsland);
    }

       /*
    MOTHER NATURE
     */

    /**
     * Move mother nature
     *
     * @param movement the movement of mother nature
     */
    public void moveMotherNaturePosition(int movement){
        islands.get(motherNaturePosition).removeMotherNature();
        motherNaturePosition = (motherNaturePosition + movement) % islands.size();
        islands.get(motherNaturePosition).placeMotherNature();
    }

    /**
     * Get the position of mother nature
     *
     * @return mother nature position
     */
    public int getMotherNaturePosition(){
        return motherNaturePosition;
    }

     /*
    CLOUD TILE
     */

    /**
     * Get the List of Cloud Tile
     *
     * @return a List containing the Cloud Tile remaining in the turn
     */
    public List<CloudTile> getCloudTiles() {
        return cloudTiles;
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
