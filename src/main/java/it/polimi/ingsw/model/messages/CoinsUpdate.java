package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * Update: a Player's coins have changed
 */
public class CoinsUpdate extends PlayerUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 6699235658820901631L;
    /**
     * The nickname of the player
     */
    private final String player;
    /**
     * The coins of the player
     */
    private final int coins;

    /**
     * Constructor
     *
     * @param player the nickname of the player
     * @param coins the number of coins of the player
     */
    public CoinsUpdate(String player, int coins) {
        this.player = player;
        this.coins = coins;
    }

    /**
     * Update the view with the number of coins of the player
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updatePlayerCoins(player, coins);
    }
}
