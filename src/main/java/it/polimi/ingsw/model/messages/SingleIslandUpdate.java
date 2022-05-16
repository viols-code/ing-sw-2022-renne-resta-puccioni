package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;

public class SingleIslandUpdate extends TableUpdate {
    @Serial
    private static final long serialVersionUID = 7611475669820901831L;
    private final int groupIsland;
    private final int singleIsland;
    private final Colour colour;

    public SingleIslandUpdate(int groupIsland, int singleIsland, Colour colour) {
        this.groupIsland = groupIsland;
        this.singleIsland = singleIsland;
        this.colour = colour;
    }

    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateSingleIsland(groupIsland, singleIsland, colour);
    }
}
