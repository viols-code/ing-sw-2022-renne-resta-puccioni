package it.polimi.ingsw.model.messages;


import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;

import java.io.Serial;
import java.util.List;

public class CharacterCardsAvailableUpdate extends GameUpdate {
    @Serial
    private static final long serialVersionUID = 1922435028352342341L;
    private final List<CharacterCardEnumeration> characterCards;

    public CharacterCardsAvailableUpdate(List<CharacterCardEnumeration> characterCards) {
        this.characterCards = characterCards;
    }


    @Override
    public void process(View view) {

    }
}
