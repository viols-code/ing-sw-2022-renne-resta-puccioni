package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * GroupIsland
 *
 * @version 1.0
 */

public class GroupIsland {

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
    public SingleIsland getIslands(int island) throws IllegalArgumentException{
        if(island < 0 || island > getNumberOfSingleIsland()){
            throw new IllegalArgumentException("The index is out of range");
        }
        return islandsContained.get(island);
    }

    /**
     * Get the number of SingleIslands in the GroupIsland
     *
     * @return the number of SingleIslands in the GroupIsland
     */
    public int getNumberOfSingleIsland(){ return islandsContained.size(); }

    /**
     * Unifies the considered GroupIsland with the given GroupIsland
     *
     * @param island the groupIsland to be unified
     */
    public void unifyIsland(GroupIsland island) {
        this.islandsContained.addAll(island.islandsContained);

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
     * Calculates the influence of the given player on the island
     *
     * @param player the player of whom we want to calculate the influence
     * @return an int which states the influence of the given player on the island
     */
    public int calculateInfluence(Player player) {
        int influence = 0;
        for (SingleIsland island : islandsContained) {
            for (Colour colour : Colour.values()) {
                if (player.getSchoolBoard().hasProfessor(colour)) {
                    influence += island.getStudents(colour);
                }
            }
        }
        //if the player already has the influence, it means that he has a tower in every singleIsland on the GroupIsland
        if (player.equals(influencePlayer)) {
            influence += getNumberOfSingleIsland();
        }

        return influence;
    }

    /**
     * Calculates the influence of the given player on the island ignoring the towers
     *
     * @param player the player of whom we want to calculate the influence
     * @return an int which states the influence of the given player on the island ignoring the towers
     */
    public int calculateInfluenceWithoutTowers(Player player) {
        int influence = 0;
        for (SingleIsland island : islandsContained) {
            for (Colour colour : Colour.values()) {
                if (player.getSchoolBoard().hasProfessor(colour)) {
                    influence += island.getStudents(colour);
                }
            }
        }

        return influence;
    }

    /**
     * Calculates the influence of the given player ignoring the colour given in the calculation
     *
     * @param player the player of whom we want to calculate the influence
     * @param colour the colour to be ignored
     * @return an int which states the influence of the given player on the island ignoring the given colour
     */
    public int calculateInfluenceWithoutColour(Player player, Colour colour) {
        int influence = 0;
        for (SingleIsland island : islandsContained) {
            for (Colour colour1 : Colour.values()) {
                if (player.getSchoolBoard().hasProfessor(colour1) && colour1 != colour) {
                    influence += island.getStudents(colour1);
                }
            }
        }

        if (player.equals(influencePlayer)) {
            influence += getNumberOfSingleIsland();
        }

        return influence;
    }


}