package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;

import java.io.Serial;

public class CurrentCharacterCardUpdate extends CardUpdate{
    @Serial
    private static final long serialVersionUID = 7611475659990901831L;
    private CharacterCardEnumeration characterCard;

    public CurrentCharacterCardUpdate(CharacterCardEnumeration characterCard){
        this.characterCard = characterCard;
    }

    @Override
    public void process(View view){

    }
}
