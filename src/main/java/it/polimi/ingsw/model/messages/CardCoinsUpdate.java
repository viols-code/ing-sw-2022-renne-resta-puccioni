package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * Update: a CharacterCard's cost has changed
 */
public class CardCoinsUpdate extends CardUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 7611472659820901831L;
    /**
     * The CharacterCard
     */
    private final int characterCard;
    /**
     * The cost of the CharacterCard
     */
    private final int coins;

    /**
     * Constructor
     *
     * @param characterCard the character card
     * @param coins         the new cost of the character card
     */
    public CardCoinsUpdate(int characterCard, int coins) {
        this.characterCard = characterCard;
        this.coins = coins;
    }

    /**
     * Update the view with the new cost of the character card
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateCardCoins(characterCard, coins);
    }
}
