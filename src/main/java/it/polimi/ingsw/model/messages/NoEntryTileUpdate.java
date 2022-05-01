package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

public class NoEntryTileUpdate extends GroupIslandUpdate{
    @Serial
    private static final long serialVersionUID = 8811455658820901831L;
    private int numberOfNoEntryTiles;

    public NoEntryTileUpdate(int numberOfNoEntryTiles){
        this.numberOfNoEntryTiles = numberOfNoEntryTiles;
    }

    @Override
    public void process(View view){

    }
}
