package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

public class TableCoinsUpdate extends GameUpdate {
    @Serial
    private static final long serialVersionUID = 8378015021532342071L;
    private final int tableCoins;

    public TableCoinsUpdate(int tableCoins) {
        this.tableCoins = tableCoins;
    }


    @Override
    public void process(View view) {

    }
}
