package it.polimi.ingsw;
import java.util.*;

/**
 * GroupIsland
 *
 * @author laurapuccioni
 * @version 1.0
 */

public class GroupIsland {

    /**
     * A list containing all the islands that form a GroupIsland
     */
    private List<SingleIsland> islandsContained;

    /**
     * A boolean that states if motherNature is present on the island (true) or not (false)
     */
    private boolean motherNature;

    /**
     * Identifies the player who has the influence on the considered GroupIsland
     */
    private Player influencePlayer;

    /**
     * An int which says how much the influence is
     */
    private int influence;

    /**
     * Constructor: creates a new empty GroupIsland
     */
    public GroupIsland(){

        influencePlayer = null;

        islandsContained = new ArrayList<SingleIsland>();
        islandsContained.add(new SingleIsland());

        motherNature = false;

    }

    /**
     * Get the list of SingleIslands that are contained in the GroupIsland
     *
     * @return the list of SingleIsland of which the GroupIsland is formed
     */
    public List<SingleIsland> getIslands(){
        return islandsContained;
    }

    /**
     * Get the player who has the influence on the GroupIsland considered
     *
     * @return the Player who has the influence
     */
    public Player getInfluence(){
        return influencePlayer;
    }

    /**
     * States if motherNature is present on the island considered or not
     *
     * @return a boolean which states the presence of motherNature
     */
    public boolean getMotherNature(){
        return motherNature;
    }

    /**
     * Unifies the considered GroupIsland with the given GroupIsland
     *
     * @param island
     */
    public void unifyIsland(GroupIsland island){

    }

    /**
     * Changes the influence from the player who has it to the given player
     *
     * @param newInfluencePlayer
     */

    public void changeInfluence(Player newInfluencePlayer){

    }

    /**
     * Removes motherNature from the GroupIsland considered
     */
    public void removeMotherNature(){

    }

    /**
     * Places motherNature on the GroupIsland considered
     */
    public void placeMotherNature(){

    }

    /**
     * Calculates the influcence of the given player on the island
     *
     * @param player
     * @return the influence of the given player on the island
     */
    public int calculateInfluence (Player player){

        return influence;
    }



}