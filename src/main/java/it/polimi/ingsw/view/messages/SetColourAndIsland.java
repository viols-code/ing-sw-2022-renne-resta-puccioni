package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;

import java.io.Serial;

public class SetColourAndIsland extends ChangeCharacterCardState {
    @Serial
    private static final long serialVersionUID = -695696551349539585L;
    private int player;
    private final Colour colour;
    private final int groupIsland;
    private final int singleIsland;

    public SetColourAndIsland(int player, Colour colour, int groupIsland, int singleIsland) {
        this.player = player;
        this.colour = colour;
        this.groupIsland = groupIsland;
        this.singleIsland = singleIsland;
    }

    public void process(GameController gameController) {
        gameController.setColourAndIsland(player, colour, groupIsland, singleIsland);
    }
}
