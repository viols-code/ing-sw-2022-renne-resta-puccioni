package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

public class NoEntryTileUpdate extends GroupIslandUpdate {
    @Serial
    private static final long serialVersionUID = 8811455658820901831L;
    private final int groupIsland;
    private final int numberOfNoEntryTiles;

    public NoEntryTileUpdate(int groupIsland, int numberOfNoEntryTiles) {
        this.groupIsland = groupIsland;
        this.numberOfNoEntryTiles = numberOfNoEntryTiles;
    }

    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateNoEntryTileOnGroupIsland(groupIsland,numberOfNoEntryTiles);
    }
}
