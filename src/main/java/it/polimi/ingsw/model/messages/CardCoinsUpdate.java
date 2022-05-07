package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;

import java.io.Serial;

public class CardCoinsUpdate extends CardUpdate{
    @Serial
    private static final long serialVersionUID = 7611472659820901831L;
    private final int characterCard;
    private final int coins;
    public CardCoinsUpdate(int characterCard, int coins){
        this.characterCard = characterCard;
        this.coins = coins;
    }

    @Override
    public void process(View view){

    }
}
