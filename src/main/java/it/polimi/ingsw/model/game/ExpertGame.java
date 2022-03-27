package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.card.CharacterCard;
import it.polimi.ingsw.model.card.ProtectIsland;

import java.util.ArrayList;
import java.util.List;

public class ExpertGame extends Game {
    /**
     * A List containing the character cards drawn for the match
     */
    private final List<CharacterCard> characterCards;

    /**
     * True if the player has already played a characterCard in their turn
     */
    private boolean hasPlayedCharacterCard;

    /**
     * Number of coins available
     */
    private int coins;

    public ExpertGame() {
        super();
        characterCards = new ArrayList<>();
        basicState = super.getActiveCharacterCard();
        hasPlayedCharacterCard = false;
        coins = 20;
    }

    /*
    CHARACTER CARD
     */

    /**
     * Get the Character Card at the given index
     *
     * @return the Character Card at the given index
     */
    @Override
    public CharacterCard getCharacterCardByIndex(int index) {
        return characterCards.get(index);
    }

    /**
     * Get the number of CharacterCard in the list
     *
     * @return the number of CharacterCard in the list
     */
    @Override
    public int getNumberOfCharacterCard() {
        return characterCards.size();
    }

    /**
     * Add the character card given to the List
     *
     * @param card the Character Card to be added
     */
    @Override
    public void addCharacterCard(CharacterCard card) {
        characterCards.add(card);
    }

    /**
     * Set the active Character Card
     *
     * @param card the active Character Card
     */
    @Override
    public void setActiveCharacterCard(CharacterCard card) {
        super.activeCharacterCard = card;
    }

        /*
    PROTECT ISLAND
     */

    /**
     * Return true if the game has the ProtectIsland card
     *
     * @return true if the game has the ProtectIsland card, false otherwise
     */
    public boolean hasProtectIslandCard() {
        for (int i = 0; i < characterCards.size(); i++) {
            if (this.getCharacterCardByIndex(i) instanceof ProtectIsland) {
                return true;
            }
        }
        return false;
    }

    /*
    HAS PLAYED CHARACTER CARD
     */

    /**
     * Get hasPlayedCharacterCard
     *
     * @return True if the player has already played a characterCard in their turn, false otherwise
     */
    @Override
    public boolean getHasPlayedCharacterCard() {
        return hasPlayedCharacterCard;
    }

    /**
     * Set hasPlayedCharacterCard
     *
     * @param hasPlayedCharacterCard true if the player has already played a characterCard in this turn, false otherwise
     */
    @Override
    public void setHasPlayedCharacterCard(boolean hasPlayedCharacterCard) {
        this.hasPlayedCharacterCard = hasPlayedCharacterCard;
    }

    /*
    COINS
     */

    /**
     * Gets the number of coins available on the table
     *
     * @return the number of coins available
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Sets the number of coins available on the table
     *
     * @param coins the coins
     */
    public void setCoins(int coins) {
        this.coins = coins;
    }

}
