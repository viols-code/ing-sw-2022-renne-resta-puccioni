package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;

import java.io.Serial;

public class MoveStudentToIsland extends PlayerAction{

    @Serial
    private static final long serialVersionUID = 9204228452736058886L;
    private final String player;
    private final Colour colour;
    private final int groupIsland;
    private final int singleIsland;

    public MoveStudentToIsland(String player, Colour colour, int groupIsland, int singleIsland){
        this.player = player;
        this.colour = colour;
        this.groupIsland = groupIsland;
        this.singleIsland = singleIsland;
    }


    @Override
    public void process(GameController gameController){
        gameController.moveStudentToIsland(player, colour, groupIsland, singleIsland);
    }
}
