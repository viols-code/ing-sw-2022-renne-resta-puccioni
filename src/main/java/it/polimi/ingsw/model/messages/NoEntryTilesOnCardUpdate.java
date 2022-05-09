package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

public class NoEntryTilesOnCardUpdate extends CardUpdate {

    @Serial
    private static final long serialVersionUID = 7611479958820901821L;
    private int numberOfNoEntryTiles;

    public NoEntryTilesOnCardUpdate(int numberOfNoEntryTiles) {
        this.numberOfNoEntryTiles = numberOfNoEntryTiles;
    }

    public void process(View view) {
        view.getModelUpdateHandler().updateNoEntryTilesOnCard(numberOfNoEntryTiles);
    }
}
