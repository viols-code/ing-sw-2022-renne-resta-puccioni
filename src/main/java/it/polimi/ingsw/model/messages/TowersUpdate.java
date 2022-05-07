package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

public class TowersUpdate extends PlayerUpdate {
    @Serial
    private static final long serialVersionUID = 7600545658820901831L;
    private String playerName;
    private final int towers;

    public TowersUpdate(int towers) {
        this.towers = towers;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void process(View view) {

    }
}
