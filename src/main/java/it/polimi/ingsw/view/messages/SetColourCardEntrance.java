package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;

public class SetColourCardEntrance extends ChangeCharacterCardState{
    private static final long serialVersionUID = -595694550449639585L;
    private int player;
    private final Colour colourCard;
    private final Colour colourEntrance;

    public SetColourCardEntrance(int player, Colour colourCard, Colour colourEntrance){
        this.player = player;
        this.colourCard = colourCard;
        this.colourEntrance = colourEntrance;
    }

    @Override
    public void process(GameController controller) {
        controller.setColourCardEntrance(player, colourCard, colourEntrance);
    }
}
