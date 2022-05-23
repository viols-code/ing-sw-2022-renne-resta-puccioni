package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;

import java.io.Serial;

/**
 * Update: a CharacterCard has been activated
 */
public class ActiveCharacterCardUpdate extends GameUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -1243219829671352198L;
    /**
     * The type of character card activated
     */
    private final CharacterCardEnumeration characterCard;

    /**
     * Constructor
     *
     * @param characterCard the type of the character card activated
     */
    public ActiveCharacterCardUpdate(CharacterCardEnumeration characterCard) {
        this.characterCard = characterCard;
    }


    /**
     * Update the model with the character card activated
     *
     * @param view view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateActiveCharacterCard(characterCard);
    }
}
