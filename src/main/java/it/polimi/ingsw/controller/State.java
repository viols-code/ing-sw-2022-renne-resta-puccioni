package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.island.GroupIsland;
import it.polimi.ingsw.model.player.Player;

public abstract class State {
    private final GameController gameController;

    public State(GameController gameController){
        this.gameController = gameController;
    }

    public int calculateInfluence(Player player, GroupIsland groupIsland){
        int influence = 0;

        if(!groupIsland.isNoEntryTile()){
            for(Colour colour: Colour.values()){
                if(player.getSchoolBoard().hasProfessor(colour)){
                    influence += groupIsland.getNumberStudent(colour);
                }
            }

            if(player.equals(groupIsland.getInfluence())){
                influence += groupIsland.getNumberOfSingleIsland();
            }
        }

        return influence;
    }

}
