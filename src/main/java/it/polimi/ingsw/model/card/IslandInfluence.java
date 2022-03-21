package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.table.island.GroupIsland;

public class IslandInfluence extends CharacterCard {

    /**
     * The groupIsland chosen
     */
    GroupIsland islandChosen;

    /**
     * Constructor
     *
     * @param game
     */
    public IslandInfluence(Game game) {
        super(game);
    }

    /**
     * Set the island to choose
     *
     * @param groupIsland
     */
    @Override
    public void setGroupIsland(GroupIsland groupIsland){
        this.islandChosen = groupIsland;
        this.effect();
    }

    /**
     * Activates the effect of the characterCard
     */
    @Override
    public void effect(){

        calculateInfluence(game.getCurrentPlayer(), islandChosen);

        game.setActiveCharacterCard(game.getBasicState());
    }
}
