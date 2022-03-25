package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.game.Game;

public class ProtectIsland extends CharacterCard {
    /**
     * The number of no entry tiles available
     */
    private int numberOfNoEntryTiles;

    private int groupIslandToProtect;

    /**
     * Constructor
     *
     * @param game the Game
     */
    public ProtectIsland(Game game) {
        super(game);
        initialCost = 2;
        actualCost = initialCost;
        numberOfNoEntryTiles = 4;
    }

    /**
     * Places the no entry tile on a group island
     *
     * @throws IllegalAccessError if there are no more "no entry tiles" available
     */
    @Override
    public void effect() {
        if (getNumberOfNoEntryTiles() == 0) throw new IllegalAccessError("There are no more no entry tiles");
        game.getTable().getGroupIslandByIndex(groupIslandToProtect).addNoEntryTile();
        setNumberOfNoEntryTiles(numberOfNoEntryTiles - 1);
        game.setActiveCharacterCard(game.getBasicState());
    }

    /**
     * Gets the number of no entry tiles available
     *
     * @return the number of no entry tiles available
     */
    private int getNumberOfNoEntryTiles() {
        return numberOfNoEntryTiles;
    }

    /**
     * Updates the number of no entry tiles available
     */
    private void setNumberOfNoEntryTiles(int numberOfNoEntryTiles) {
        this.numberOfNoEntryTiles = numberOfNoEntryTiles;
    }

    /**
     * Set the island to choose
     *
     * @param groupIsland the GroupIsland chosen
     */
    @Override
    public void setGroupIsland(int groupIsland) {
        this.groupIslandToProtect = groupIsland;
        this.effect();
    }

}
