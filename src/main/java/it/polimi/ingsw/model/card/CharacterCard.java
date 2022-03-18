package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.island.GroupIsland;
import it.polimi.ingsw.model.player.Player;

public abstract class CharacterCard {
    protected Game game;
    protected int initialCost;
    protected int actualCost;

    /**
     * Constructor: create a new CharacterCard
     *
     * @param game the Game
     */
    public CharacterCard(Game game) {
        this.game = game;
    }

    /**
     * Get the actualCost of the CharacterCard
     *
     * @return the actualCost of the CharacterCard
     */
    public int getCost() {
        return actualCost;
    }

    /**
     * Increments the actualCost by one
     */
    public void incrementCost() {
        actualCost += 1;
    }

    /**
     * Activates the effect of the CharacterCard
     */
    public void effect() {
    }

    /**
     * Calculates influence of the given Player in the given GroupIsland
     *
     * @param player the Player
     * @param groupIsland the GroupIsland
     * @return influence of the given Player in the given GroupIsland
     */
    public int calculateInfluence(Player player, GroupIsland groupIsland) {
        int influence = 0;

        if (!groupIsland.isNoEntryTile()) {
            for (Colour colour : Colour.values()) {
                if (player.getSchoolBoard().hasProfessor(colour)) {
                    influence += groupIsland.getNumberStudent(colour);
                }
            }

            if (player.equals(groupIsland.getInfluence())) {
                influence += groupIsland.getNumberOfSingleIsland();
            }
        }

        return influence;
    }


    public void checkProfessor(Colour colour) {

    }

    public void checkMotherNatureMovement() {

    }

    /**
     * Set the colour
     *
     * @param colour the colour to be set
     * @throws IllegalAccessError if the CharacterCard doesn't have this method
     */
    public void setColour(Colour colour) throws IllegalAccessError {
        throw new IllegalAccessError("The card doesn't have this method");
    }

    /**
     * Set the colour
     *
     * @param colourDiningRoom the colour of the student in the diningRoom
     * @param colourEntrance the colour of the student in the entrance
     * @throws IllegalAccessError if the CharacterCard doesn't have this method
     */
    public void setColourDiningRoom(Colour colourDiningRoom, Colour colourEntrance) throws IllegalAccessError {
        throw new IllegalAccessError("The card doesn't have this method");
    }

    /**
     * Set the colour
     *
     * @param colour the colour to be set
     * @throws IllegalAccessError if the CharacterCard doesn't have this method
     */
    public void setColourEntrance(Colour colour) throws IllegalAccessError {
        throw new IllegalAccessError("The card doesn't have this method");
    }

}
