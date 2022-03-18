package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.island.GroupIsland;
import it.polimi.ingsw.model.player.Player;

public abstract class CharacterCard {
    protected Game game;
    protected int initialCost;
    protected int actualCost;

    public CharacterCard(Game game) {
        this.game = game;
    }

    public int getCost(){
        return actualCost;
    }

    public void incrementCost(){
        actualCost += 1;
    }

    public void effect(){
    }

    public int calculateInfluence(Player player, GroupIsland groupIsland) {
        int influence = 0;

        if (!groupIsland.isNoEntryTile()) {
            for (Colour colour : Colour.values()) {
                if (player.getSchoolBoard().hasProfessor(colour)) {
                    influence += groupIsland.getNumberStudent(colour);
                }
            }

            if (player.equals(groupIsland.getInfluence())) {
                influence += groupIsland.getNumberOfSingleIsland();
            }
        }

        return influence;
    }

    public void checkProfessor(Colour colour){

    }

    public void checkMotherNatureMovement(){

    }

    public void setColour(Colour colour) throws IllegalAccessError {
        throw new IllegalAccessError("The card doesn't have this method");
    }

}
