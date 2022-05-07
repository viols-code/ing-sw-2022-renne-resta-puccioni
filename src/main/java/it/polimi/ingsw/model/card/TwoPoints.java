package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.island.GroupIsland;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;

public class TwoPoints extends CharacterCard {
    /**
     * Constructor
     *
     * @param game the Game
     */
    public TwoPoints(Game game) {
        super(game);
        actualCost = 2;
        type = CharacterCardEnumeration.TWO_POINTS;
    }

    /**
     * Calculate the influence that a player has on a group island
     *
     * @param player      the position of the player in the list players in game
     * @param groupIsland the position of the group island the list islands in game
     * @return the influence that the player has on the group island
     */
    @Override
    public int calculateInfluencePlayer(Player player, GroupIsland groupIsland) {
        if (player.equals(game.getCurrentPlayer()))
            return super.calculateInfluencePlayer(player, groupIsland) + 2;
        else
            return super.calculateInfluencePlayer(player, groupIsland);
    }
}
