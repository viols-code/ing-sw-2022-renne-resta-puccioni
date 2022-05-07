package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.IProcessablePacket;
import it.polimi.ingsw.controller.GameController;

import java.io.Serial;
import java.io.Serializable;

public abstract class PlayerEvent implements Serializable, IProcessablePacket<GameController> {
    @Serial
    private static final long serialVersionUID = -695696550449639585L;
    private String player;

    protected String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
