package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.island.GroupIsland;
import it.polimi.ingsw.model.player.Player;

public class NoColour extends State{
    public NoColour(GameController gameController){
        super(gameController);
    }


    public int calculateInfluence(Player player, GroupIsland groupIsland){
        int influence = 0;

        if(!groupIsland.isNoEntryTile()){
            for(Colour colour: Colour.values()){
                if(player.getSchoolBoard().hasProfessor(colour)){
                    /*
                    AGGIUNGERE && colour != gameController.getCard().getColour()
                     */
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
