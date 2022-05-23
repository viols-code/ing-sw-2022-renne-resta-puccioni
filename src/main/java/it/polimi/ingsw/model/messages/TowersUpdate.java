package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * Update: the player's number of towers of the
 */
public class TowersUpdate extends PlayerUpdate {
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
     * The number of towers
     */
    private final int towers;

    /**
     * Constructor
     *
     * @param playerName the player's nickname
     * @param towers     the number of towers
     */
    public TowersUpdate(String playerName, int towers) {
        this.playerName = playerName;
        this.towers = towers;
    }

    /**
     * Gets the player's nickname
     *
     * @return the player's nickname
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Update the view with the number of towers of the player
     *
     * @param view the view
     */
    public void process(View view) {
        view.getModelUpdateHandler().updateTowers(playerName, towers);
    }
}
