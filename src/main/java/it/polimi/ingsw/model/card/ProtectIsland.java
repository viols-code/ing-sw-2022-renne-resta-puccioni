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
        numberOfNoEntryTiles=4;
    }

    /**
     * Activates the effect of the CharacterCard
     *
     * @throws IllegalAccessError if there are no more "no entry tiles" available
     */
    @Override
    public void effect(){
        if(getNumberOfNoEntryTiles()==0) throw new IllegalAccessError("There are no more no entry tiles");
        game.getTable().getGroupIslandByIndex(groupIslandToProtect).addNoEntryTile();
        setNumberOfNoEntryTiles(numberOfNoEntryTiles-1);
    }

    /**
     * Get the number of no entry tiles available
     *
     * @return the number of no entry tiles available
     */
    private int getNumberOfNoEntryTiles(){
        return numberOfNoEntryTiles;
    }

    /**
     * Update the number of no entry tiles available
     */
    private void setNumberOfNoEntryTiles(int numberOfNoEntryTiles){
        this.numberOfNoEntryTiles=numberOfNoEntryTiles;
    }

}
