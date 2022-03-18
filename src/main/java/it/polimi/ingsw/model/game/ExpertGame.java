package it.polimi.ingsw.model.game;

import it.polimi.ingsw.controller.CharacterCard;

import java.util.ArrayList;
import java.util.List;

public class ExpertGame extends Game {
    /**
     * A List containing the character cards drawn for the match
     */
    private final List<CharacterCard> characterCards;

    private final CharacterCard basicState;

    private boolean hasProtectIsland;

    public ExpertGame() {
        super();
        characterCards = new ArrayList<>();
        basicState = super.getActiveCharacterCard();
        this.hasProtectIsland = false;
    }

    /**
     * Get the Character Card at the given index
     *
     * @return the Character Card at the given index
     */
    public CharacterCard getCharacterCardsByIndex(int index) {
        return characterCards.get(index);
    }

    /**
     * Add the character card given to the List
     *
     * @param card the Character Card to be added
     */
    public void addCharacterCard(CharacterCard card) {
        characterCards.add(card);
    }

    /**
     * Set the active Character Card
     *
     * @param card the active Character Card
     */
    public void setActiveCharacterCard(CharacterCard card) {
        super.activeCharacterCard = card;
    }

    public void setHasProtectIsland(){
        hasProtectIsland = true;
    }

}
