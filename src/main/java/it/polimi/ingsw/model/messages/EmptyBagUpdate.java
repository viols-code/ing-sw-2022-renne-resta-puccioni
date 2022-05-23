package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * Update: the bag is empty
 */
public class EmptyBagUpdate extends TableUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 7611475659820901831L;

    /**
     * Update the view that the bag is empty
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateEmptyBag();
    }
}
