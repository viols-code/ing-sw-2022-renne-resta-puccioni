package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

public class CardCoinsUpdate extends CardUpdate{
    @Serial
    private static final long serialVersionUID = 7611472659820901831L;
    private final int coins;
    public CardCoinsUpdate(int coins){
        this.coins = coins;
    }

    @Override
    public void process(View view){

    }
}
