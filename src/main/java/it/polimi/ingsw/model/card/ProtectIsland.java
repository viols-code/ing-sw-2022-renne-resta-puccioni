package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.messages.NoEntryTilesOnCardUpdate;

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
        actualCost = 2;
        numberOfNoEntryTiles = 4;
    }

    /**
     * Places the no entry tile on a group island
     *
     * @throws IllegalAccessError if there are no more "no entry tiles" available
     */
    @Override
    protected void effect() throws IllegalAccessError {
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
        notify(new NoEntryTilesOnCardUpdate(this.numberOfNoEntryTiles));
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
