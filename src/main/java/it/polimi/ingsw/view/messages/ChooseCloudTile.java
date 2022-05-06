package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;

import java.io.Serial;

public class ChooseCloudTile extends PlayerAction{
    @Serial
    private static final long serialVersionUID = -695696550349539585L;
    private String player;
    private final int cloudTile;

    public ChooseCloudTile(String player, int cloudTile){
        this.player = player;
        this.cloudTile = cloudTile;
    }

    public void process(GameController gameController){
        gameController.chooseCloudTile(player,cloudTile);
    }
}
