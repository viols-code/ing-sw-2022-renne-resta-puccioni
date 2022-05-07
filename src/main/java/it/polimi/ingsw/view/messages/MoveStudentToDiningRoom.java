package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;

import java.io.Serial;

public class MoveStudentToDiningRoom extends PlayerAction {

    @Serial
    private static final long serialVersionUID = 9204228452737758886L;
    private String player;
    private final Colour colour;

    public MoveStudentToDiningRoom(String player, Colour colour) {
        this.player = player;
        this.colour = colour;
    }

    public void process(GameController gameController) {
        gameController.moveStudentToDiningRoom(player, colour);
    }


}
