package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;

import java.io.Serial;
import java.util.HashMap;
import java.util.List;

public class CharacterCardUpdate extends DirectReconnectionMessage{
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -4626056893016277931L;
    /**
     * List of the available character cards
     */
    private final List<CharacterCardEnumeration> characterCards;
    /**
     * HashMap of the available character cards and their cost
     */
    private final HashMap<CharacterCardEnumeration, Integer> cost;
    /**
     * HashMap of the students in card with index 0
     */
    private final HashMap<Colour, Integer> student0;
    /**
     * HashMap of the students in card with index 1
     */
    private final HashMap<Colour, Integer> student1;
    /**
     * HashMap of the students in card with index 2
     */
    private final HashMap<Colour, Integer> student2;
    /**
     * coins in the bank
     */
    private final int coins;
    /**
     * Number of no entry tiles
     */
    private final int noEntryTile;

    /**
     * Constructs a new CharacterCardUpdate for the given recipient
     *
     * @param recipient the client connection that this message will be sent to
     * @param characterCards the character cards available
     * @param cost the cost of the character cards
     */
    public CharacterCardUpdate(SocketClientConnection recipient, List<CharacterCardEnumeration> characterCards, HashMap<CharacterCardEnumeration, Integer> cost, HashMap<Colour, Integer> student0, HashMap<Colour, Integer> student1, HashMap<Colour, Integer> student2, int coins, int noEntryTile) {
        super(recipient);
        this.characterCards = characterCards;
        this.cost = cost;
        this.student0 = student0;
        this.student1 = student1;
        this.student2 = student2;
        this.coins = coins;
        this.noEntryTile = noEntryTile;
    }


    /**
     * Update the view with the available character cards
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModel().setGameMode(true);
        view.handleGameMode(true);
        view.getModelUpdateHandler().updateCharacterCardsAvailable(characterCards);
        for (CharacterCardEnumeration card: characterCards) {
            view.getModelUpdateHandler().updateCardCoins(characterCards.indexOf(card), cost.get(card));
        }
        view.getModelUpdateHandler().updateCardStudents(student0, student1, student2);
        view.getModelUpdateHandler().updateTableCoins(coins);
        view.getModelUpdateHandler().noEntryTile(noEntryTile);
    }
}
