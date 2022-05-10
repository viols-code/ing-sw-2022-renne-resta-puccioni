package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;

import java.io.Serial;

public class SetGroupIsland extends ChangeCharacterCardState {

    @Serial
    private static final long serialVersionUID = 9204228452747768886L;
    private final String player;
    private final int groupIsland;

    public SetGroupIsland(String player, int groupIsland) {
        this.player = player;
        this.groupIsland = groupIsland;
    }

    public void process(GameController gameController) {
        gameController.setGroupIsland(player, groupIsland);
    }
}
