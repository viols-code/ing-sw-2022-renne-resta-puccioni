package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.messages.CoinsUpdate;

import java.util.Objects;

public class ExpertPlayer extends Player {
    /**
     * number of coins
     */
    private int coins;

    /**
     * Constructor: creates a new ExpertPlayer with the given nick and the given wizard
     *
     * @param nickname a string with the nickname
     * @param wizard   the wizard chosen by the player
     */
    public ExpertPlayer(String nickname, Wizard wizard, TowerColour towerColour) {
        super(nickname, wizard, towerColour);
        coins = 1;
    }


    /*
    COINS
     */

    /**
     * Get the number of coins
     *
     * @return the number of coins
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Add the given number of coins
     *
     * @param num the number of coins to add to the player
     */
    public void addCoins(int num) {
        coins += num;
        notify(new CoinsUpdate(coins));
    }

    /**
     * Remove the given number of coins
     *
     * @param num the number of coins to remove to the player
     */
    public void removeCoins(int num) {
        coins -= num;
        notify(new CoinsUpdate(coins));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ExpertPlayer that = (ExpertPlayer) o;
        return getCoins() == that.getCoins();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCoins());
    }
}
