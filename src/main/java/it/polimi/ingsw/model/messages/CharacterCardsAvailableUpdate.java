package it.polimi.ingsw.model.messages;


import it.polimi.ingsw.model.card.CharacterCard;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.List;

public class CharacterCardsAvailableUpdate extends GameUpdate {
    @Serial
    private static final long serialVersionUID = 1922435028352342341L;
    private final List<CharacterCard> characterCards;

    public CharacterCardsAvailableUpdate(List<CharacterCard> characterCards){
        this.characterCards = characterCards;
    }


    @Override
    public void process(View view) {

    }
}
