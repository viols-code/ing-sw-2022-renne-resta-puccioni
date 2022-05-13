package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;

import java.io.Serial;

public class ActiveCharacterCardUpdate extends GameUpdate {
    @Serial
    private static final long serialVersionUID = -1243219829671352198L;
    private final CharacterCardEnumeration characterCard;

    public ActiveCharacterCardUpdate(CharacterCardEnumeration characterCard) {
        this.characterCard = characterCard;
    }


    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateActiveCharacterCard(characterCard);
    }
}
