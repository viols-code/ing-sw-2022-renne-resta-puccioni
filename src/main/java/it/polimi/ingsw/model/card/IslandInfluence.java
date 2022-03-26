package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.game.Game;

public class IslandInfluence extends CharacterCard {

    /**
     * The groupIsland chosen
     */
    int islandChosen;

    /**
     * Constructor
     *
     * @param game the Game
     */
    public IslandInfluence(Game game) {
        super(game);
        actualCost = 3;
    }

    /**
     * Set the island to choose
     *
     * @param groupIsland the GroupIsland chosen
     */
    @Override
    public void setGroupIsland(int groupIsland) {
        this.islandChosen = groupIsland;
        this.effect();
    }

    /**
     * Activates the effect of the characterCard
     */
    @Override
    protected void effect() {
        calculateInfluence(islandChosen);
        game.setActiveCharacterCard(game.getBasicState());
    }
}
