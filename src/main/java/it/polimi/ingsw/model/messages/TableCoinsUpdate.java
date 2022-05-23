package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * Update: the coins on the table have changed
 */
public class TableCoinsUpdate extends GameUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 8378015021532342071L;
    /**
     * The coins on the table
     */
    private final int tableCoins;

    /**
     * Constructor
     *
     * @param tableCoins the coins on the table
     */
    public TableCoinsUpdate(int tableCoins) {
        this.tableCoins = tableCoins;
    }

    /**
     * Update the view with the coins on the table
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateTableCoins(tableCoins);
    }
}
