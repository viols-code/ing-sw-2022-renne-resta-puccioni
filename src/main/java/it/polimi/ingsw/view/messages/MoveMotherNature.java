package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;

import java.io.Serial;

public class MoveMotherNature extends PlayerAction{
    @Serial
    private static final long serialVersionUID = -695696550449539585L;
    private String player;
    private final int movement;
    public MoveMotherNature(String player, int movement){
        this.player = player;
        this.movement = movement;
    }
    public void process(GameController gameController){
        gameController.moveMotherNature(player,movement);
    }
}
