package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

public class IslandInfluenceUpdate extends CardUpdate {
    @Serial
    private static final long serialVersionUID = -1023219829671352198L;
    private final int groupIsland;

    public IslandInfluenceUpdate(int groupIsland) {
        this.groupIsland = groupIsland;
    }

    @Override
    public void process(View view) {
        view.getRenderer().showGameMessage("Island chosen for an extra influence computation: " + groupIsland);
    }
}
