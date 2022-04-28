package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;

import java.io.Serial;

public class MoveStudentToDiningRoom extends PlayerAction{

    @Serial
    private static final long serialVersionUID = 9204228452736058886L;
    private int player;
    private final Colour colour;

    public MoveStudentToDiningRoom(int player, Colour colour){
        this.player = player;
        this.colour = colour;
    }

    public void process(GameController gameController){
        gameController.moveStudentToDiningRoom(player, colour);
    }


}
