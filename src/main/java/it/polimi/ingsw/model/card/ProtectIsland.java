package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.messages.NoEntryTileUpdate;
import it.polimi.ingsw.model.messages.NoEntryTilesOnCardUpdate;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;

public class ProtectIsland extends CharacterCard {
    /**
     * The number of no entry tiles available
     */
    private int numberOfNoEntryTiles;
    /**
     * The chosen group island to be protected
     */
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
        type = CharacterCardEnumeration.PROTECT_ISLAND;
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
        notify(new NoEntryTileUpdate(groupIslandToProtect, game.getTable().getGroupIslandByIndex(groupIslandToProtect).getNumberOfNoEntryTile()));
        setNumberOfNoEntryTiles(numberOfNoEntryTiles - 1);
        game.setActiveCharacterCard(game.getBasicState());
    }

    /**
     * Gets the number of no entry tiles available
     *
     * @return the number of no entry tiles available
     */
    protected int getNumberOfNoEntryTiles() {
        return numberOfNoEntryTiles;
    }

    /**
     * Updates the number of no entry tiles available
     */
    @Override
    protected void setNumberOfNoEntryTiles(int numberOfNoEntryTiles) {
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
        if (groupIsland >= 0) {
            this.effect();
        }
    }

}
