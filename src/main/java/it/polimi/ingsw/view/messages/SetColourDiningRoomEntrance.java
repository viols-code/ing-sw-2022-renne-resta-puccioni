package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;

import java.io.Serial;

public class SetColourDiningRoomEntrance extends ChangeCharacterCardState {
    @Serial
    private static final long serialVersionUID = -695696551399539585L;
    private String player;
    private final Colour colourDiningRoom;
    private final Colour colourEntrance;

    public SetColourDiningRoomEntrance(String player, Colour colourDiningRoom, Colour colourEntrance) {
        this.player = player;
        this.colourDiningRoom = colourDiningRoom;
        this.colourEntrance = colourEntrance;
    }

    public void process(GameController gameController) {
        gameController.setColourDiningRoomEntrance(player, colourDiningRoom, colourEntrance);
    }
}
