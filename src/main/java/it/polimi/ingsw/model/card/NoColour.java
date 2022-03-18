package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.island.GroupIsland;
import it.polimi.ingsw.model.player.Player;

public class NoColour extends CharacterCard {
    private Colour colour;

    /**
     * Constructor
     *
     * @param game the Game
     */
    public NoColour(Game game) {
        super(game);
        initialCost = 3;
        actualCost = initialCost;
    }

    /**
     * Set the colour not to be considered
     *
     * @param colour the colour to be set
     */
    @Override
    public void setColour(Colour colour) {
        this.colour = colour;
    }

    /**
     * Calculates the influence of the given Player in the given GroupIsland without considering a colour
     *
     * @param player the Player
     * @param groupIsland the GroupIsland
     * @return the influence of the given Player in the given GroupIsland without considering a colour
     */
    @Override
    public int calculateInfluence(Player player, GroupIsland groupIsland) {
        int influence = 0;

        if (!groupIsland.isNoEntryTile()) {
            for (Colour colour : Colour.values()) {
                if (player.getSchoolBoard().hasProfessor(colour) && colour != this.colour) {
                    influence += groupIsland.getNumberStudent(colour);
                }
            }

            if (player.equals(groupIsland.getInfluence())) {
                influence += groupIsland.getNumberOfSingleIsland();
            }
        }

        return influence;
    }

}
