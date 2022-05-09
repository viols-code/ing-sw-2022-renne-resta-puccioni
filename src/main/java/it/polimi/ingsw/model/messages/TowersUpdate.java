package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

public class TowersUpdate extends PlayerUpdate {
    @Serial
    private static final long serialVersionUID = 7600545658820901831L;
    private final String playerName;
    private final int towers;

    public TowersUpdate(String playerName, int towers) {
        this.playerName = playerName;
        this.towers = towers;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void process(View view) {
        view.getModelUpdateHandler().updateTowers(playerName,towers);
    }
}
