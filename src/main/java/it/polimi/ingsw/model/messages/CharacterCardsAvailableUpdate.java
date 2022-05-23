package it.polimi.ingsw.model.messages;


import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;

import java.io.Serial;
import java.util.List;

/**
 * Update: the CharacterCards available in the game
 */
public class CharacterCardsAvailableUpdate extends GameUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 1922435028352342341L;
    /**
     * List of the available character cards
     */
    private final List<CharacterCardEnumeration> characterCards;

    /**
     * Constructor
     *
     * @param characterCards the list of the available character cards
     */
    public CharacterCardsAvailableUpdate(List<CharacterCardEnumeration> characterCards) {
        this.characterCards = characterCards;
    }

    /**
     * Update the view with the available character cards
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateCharacterCardsAvailable(characterCards);
    }
}
