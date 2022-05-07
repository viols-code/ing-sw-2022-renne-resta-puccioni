package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

public class UnifyIslandsUpdate extends GroupIslandUpdate {
    @Serial
    private static final long serialVersionUID = -5511475659820901831L;
    private int firstGroupIsland;
    private int secondGroupIsland;

    public UnifyIslandsUpdate(int firstGroupIsland, int secondGroupIsland) {
        this.firstGroupIsland = firstGroupIsland;
        this.secondGroupIsland = secondGroupIsland;
    }

    @Override
    public void process(View view) {

    }
}
