package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.messages.IslandInfluenceUpdate;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;

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
        type = CharacterCardEnumeration.ISLAND_INFLUENCE;
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
        notify(new IslandInfluenceUpdate(groupIsland));
    }

    /**
     * Activates the effect of the characterCard
     */
    @Override
    protected void effect() {
        this.calculateInfluence(islandChosen);
        game.setActiveCharacterCard(game.getBasicState());
    }
}
