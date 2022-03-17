package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.CharacterCard;

import java.util.ArrayList;
import java.util.List;

public class ExpertGame extends Game {
    /**
     * A List containing the character cards drawn for the match
     */
    private final List<CharacterCard> characterCards;

    /**
     * the active character card
     */
    private CharacterCard activeCharacterCard;

    public ExpertGame(){
        super();
        characterCards = new ArrayList<>();
        activeCharacterCard = null;
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
     * Get the active Character Card
     *
     * @return the active Character Card
     */
    public CharacterCard getActiveCharacterCard(){
        return activeCharacterCard;
    }

    /**
     * Set the active Character Card
     *
     * @param card the active Character Card
     */
    public void setActiveCharacterCard(CharacterCard card){
        this.activeCharacterCard = card;
    }

}
