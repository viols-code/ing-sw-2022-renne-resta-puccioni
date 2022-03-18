package it.polimi.ingsw.model.island;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * GroupIsland
 *
 * @version 1.0
 */
public abstract class GroupIsland {

    /**
     * A list containing all the islands that form a GroupIsland
     */
    private final List<SingleIsland> islandsContained;

    /**
     * A boolean that states if motherNature is present on the island (true) or not (false)
     */
    private boolean motherNature;

    /**
     * Identifies the player who has the influence on the considered GroupIsland
     */
    private Player influencePlayer;

    /**
     * Constructor: creates a new empty GroupIsland
     */
    public GroupIsland() {
        influencePlayer = null;
        islandsContained = new ArrayList<>();
        islandsContained.add(new SingleIsland());
        motherNature = false;
    }

    /*
    ISLANDS
     */

    /**
     * Get the list of SingleIslands that are contained in the GroupIsland
     *
     * @return the list of SingleIsland of which the GroupIsland is formed
     */
    public SingleIsland getIslands(int island) throws IllegalArgumentException {
        if (island < 0 || island > getNumberOfSingleIsland()) {
            throw new IllegalArgumentException("The index is out of range");
        }
        return islandsContained.get(island);
    }

    /**
     * Get the number of student of the given colour
     *
     * @param colour the given colour
     * @return the number of student of the given colour
     */
    public int getNumberStudent(Colour colour) {
        int count = 0;

        for (SingleIsland island : islandsContained) {
            count += island.getStudents(colour);
        }

        return count;
    }

    /**
     * Add a SingleIsland to the GroupIsland
     *
     * @param singleIsland the SingleIsland to be added
     */
    public void addSingleIsland(SingleIsland singleIsland) {
        islandsContained.add(singleIsland);
    }

    /**
     * Get the number of SingleIslands in the GroupIsland
     *
     * @return the number of SingleIslands in the GroupIsland
     */
    public int getNumberOfSingleIsland() {
        return islandsContained.size();
    }

    /*
    MOTHER NATURE
     */

    /**
     * States if motherNature is present on the island considered or not
     *
     * @return a boolean which states the presence of motherNature
     */
    public boolean getMotherNature() {
        return motherNature;
    }

    /**
     * Removes motherNature from the GroupIsland considered
     */
    public void removeMotherNature() {
        motherNature = false;
    }

    /**
     * Places motherNature on the GroupIsland considered
     */
    public void placeMotherNature() {
        motherNature = true;
    }

    /*
    INFLUENCE
     */

    /**
     * Get the player who has the influence on the GroupIsland considered
     *
     * @return the Player who has the influence
     */
    public Player getInfluence() {
        return influencePlayer;
    }

    /**
     * Changes the influence from the player who has it to the given player
     *
     * @param newInfluencePlayer the player who has now the influence on the GroupIsland
     */
    public void changeInfluence(Player newInfluencePlayer) {
        influencePlayer = newInfluencePlayer;
    }

    /**
     * Get the no entry tile
     *
     * @return true if there are no entry tiles on the island, false otherwise
     */
    public boolean isNoEntryTile() {
        return false;
    }

    /**
     * Get the number of no entry tile
     *
     * @return the number of no entry tiles
     * @throws IllegalAccessError if the mode is basic
     */
    public int getNumberOfNoEntryTile() throws IllegalAccessError {
        throw new IllegalAccessError("This is for the Expert Mode");
    }

    /**
     * Add one no entry tile
     *
     * @throws IllegalAccessError if the mode is basic
     */
    public void addNoEntryTile() throws IllegalAccessError {
        throw new IllegalAccessError("This is for the Expert Mode");
    }

    /**
     * Remove one no entry tile
     *
     * @throws IllegalAccessError if the mode is basic
     */
    public void removeNoEntryTile() throws IllegalAccessError {
        throw new IllegalAccessError("This is for the Expert Mode");
    }

}