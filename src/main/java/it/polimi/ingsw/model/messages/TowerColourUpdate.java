package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.player.TowerColour;
import it.polimi.ingsw.view.View;

import java.io.Serial;

public class TowerColourUpdate extends PlayerUpdate{
    @Serial
    private static final long serialVersionUID = 7600545658820901831L;
    private final String playerName;
    private final TowerColour towerColour;

    public TowerColourUpdate(String playerName, TowerColour towerColour) {
        this.playerName = playerName;
        this.towerColour = towerColour;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void process(View view) {
        view.getModelUpdateHandler().updateTowerColour(playerName,towerColour);
    }
}
