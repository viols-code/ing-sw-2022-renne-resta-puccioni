package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;

import java.io.Serial;

public class SetColourCardEntrance extends ChangeCharacterCardState {
    @Serial
    private static final long serialVersionUID = -595694550449639585L;
    private String player;
    private final Colour colourCard;
    private final Colour colourEntrance;

    public SetColourCardEntrance(String player, Colour colourCard, Colour colourEntrance) {
        this.player = player;
        this.colourCard = colourCard;
        this.colourEntrance = colourEntrance;
    }

    @Override
    public void process(GameController controller) {
        controller.setColourCardEntrance(player, colourCard, colourEntrance);
    }
}
