package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;

import java.io.Serial;

public class SetColour extends ChangeCharacterCardState {
    @Serial
    private static final long serialVersionUID = -595694550449639585L;
    private String player;
    private final Colour colour;

    public SetColour(String player, Colour colour) {
        this.player = player;
        this.colour = colour;
    }

    @Override
    public void process(GameController controller) {
        controller.setColour(player, colour);
    }
}
