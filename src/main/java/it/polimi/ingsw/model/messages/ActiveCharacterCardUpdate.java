package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

public class ActiveCharacterCardUpdate extends GameUpdate{
    @Serial
    private static final long serialVersionUID = -1243219829671352198L;
    private final int characterCard;

    public ActiveCharacterCardUpdate(int characterCard){
        this.characterCard = characterCard;
    }


    @Override
    public void process(View view) {

    }
}
