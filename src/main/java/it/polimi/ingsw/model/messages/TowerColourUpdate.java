package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.player.TowerColour;
import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * Update the tower colour of the player
 */
public class TowerColourUpdate extends PlayerUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 7600545658820901831L;
    /**
     * The player's nickname
     */
    private final String playerName;
    /**
     * The colour of the tower
     */
    private final TowerColour towerColour;

    /**
     * Constructor
     *
     * @param playerName  the player's nickname
     * @param towerColour the colour of the tower
     */
    public TowerColourUpdate(String playerName, TowerColour towerColour) {
        this.playerName = playerName;
        this.towerColour = towerColour;
    }

    /**
     * Get the player nickname
     *
     * @return the player nickname
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Update the tower colour of the player
     *
     * @param view the view
     */
    public void process(View view) {
        view.getModelUpdateHandler().updateTowerColour(playerName, towerColour);
    }
}
