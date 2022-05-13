package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

public class CoinsUpdate extends PlayerUpdate {
    @Serial
    private static final long serialVersionUID = 6699235658820901631L;
    private final String player;
    private final int coins;

    public CoinsUpdate(String player, int coins) {
        this.player = player;
        this.coins = coins;
    }


    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updatePlayerCoins(player,coins);
    }
}
