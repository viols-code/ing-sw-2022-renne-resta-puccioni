package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.island.GroupIsland;
import it.polimi.ingsw.model.player.Player;

public class NoTower extends CharacterCard {

    /**
     * Constructor
     *
     * @param game the Game
     */
    public NoTower(Game game) {
        super(game);
        super.initialCost = 3;
        actualCost = initialCost;
    }


    /**
     * Calculate the influence that a player has on a group island
     *
     * @param player      the position of the player in the list players in game
     * @param groupIsland the position of the group island the list islands in game
     * @return the influence that the player has on the group island
     */
    @Override
    public int calculateInfluence(Player player, GroupIsland groupIsland) {
        int influence = 0;

        if (!groupIsland.isNoEntryTile()) {
            for (Colour colour : Colour.values()) {
                if (player.getSchoolBoard().hasProfessor(colour)) {
                    influence += groupIsland.getNumberStudent(colour);
                }
            }
        }
        game.setActiveCharacterCard(game.getBasicState());
        return influence;
    }

    /**
     * Activates the effect of the CharacterCard
     */
    @Override
    public void effect() {
    }

}