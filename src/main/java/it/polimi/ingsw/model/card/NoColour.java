package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.island.GroupIsland;
import it.polimi.ingsw.model.player.Player;

public class NoColour extends CharacterCard {
    private Colour colour;

    public NoColour(Game game) {
        super(game);
        initialCost = 3;
        actualCost = initialCost;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }


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
