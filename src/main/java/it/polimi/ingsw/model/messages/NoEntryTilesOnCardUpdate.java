package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * Update: the no entry tiles on the character card have changed
 */
public class NoEntryTilesOnCardUpdate extends CardUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 7611479958820901821L;
    /**
     * Number of no entry tiles on the card
     */
    private final int numberOfNoEntryTiles;

    /**
     * Constructor
     *
     * @param numberOfNoEntryTiles number of no entry tiles on the card
     */
    public NoEntryTilesOnCardUpdate(int numberOfNoEntryTiles) {
        this.numberOfNoEntryTiles = numberOfNoEntryTiles;
    }

    /**
     * Update the view with the number of no entry tiles on the card
     *
     * @param view the view
     */
    public void process(View view) {
        view.getModelUpdateHandler().updateNoEntryTilesOnCard(numberOfNoEntryTiles);
    }
}
