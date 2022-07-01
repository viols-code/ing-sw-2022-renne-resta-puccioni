package it.polimi.ingsw.view.beans;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.player.TowerColour;
import it.polimi.ingsw.model.player.Wizard;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.HashMap;

/**
 * Class that contains a local copy of the player
 */
public class MockPlayer {
    /**
     * A string that represents the nickname of the player
     */
    private final String nickname;

    /**
     * The wizard chosen by the player
     */
    private final Wizard wizard;

    /**
     * A boolean that states if the player is the localPlayer
     */
    private final boolean localPlayer;

    /**
     * The player's school board
     */
    private final MockSchoolBoard schoolBoard;

    /**
     * Number of coins
     */
    private final IntegerProperty coins;

    /**
     * The colour of the tower given to the player
     */
    private TowerColour towerColour;

    /**
     * A list representing the assistant card deck
     */
    private final ObservableMap<Integer, AssistantCard> cards;

    /**
     * The current assistant card
     */
    private final Property<AssistantCard> currentAssistantCard;

    /**
     * True if the assistantCard is set, false otherwise
     */
    private final BooleanProperty assistantCardValue;


    /**
     * Constructs the mock player
     *
     * @param nickname    the string containing the nickname
     * @param wizard      the wizard chosen by the player
     * @param hasCoins    true for expert game mode, false for basic game mode
     * @param localPlayer true if the player is the local player of this view
     */
    public MockPlayer(String nickname, Wizard wizard, boolean hasCoins, boolean localPlayer) {
        this.nickname = nickname;
        this.wizard = wizard;
        this.localPlayer = localPlayer;
        this.towerColour = null;
        schoolBoard = new MockSchoolBoard();
        cards = FXCollections.observableHashMap();
        currentAssistantCard = new SimpleObjectProperty<>();
        coins = new SimpleIntegerProperty();
        if (hasCoins) {
            coins.setValue(1);
        } else {
            coins.setValue(0);
        }
        AssistantCard card1 = new AssistantCard(1, 1);
        cards.put(0, card1);
        AssistantCard card2 = new AssistantCard(2, 1);
        cards.put(1, card2);
        AssistantCard card3 = new AssistantCard(3, 2);
        cards.put(2, card3);
        AssistantCard card4 = new AssistantCard(4, 2);
        cards.put(3, card4);
        AssistantCard card5 = new AssistantCard(5, 3);
        cards.put(4, card5);
        AssistantCard card6 = new AssistantCard(6, 3);
        cards.put(5, card6);
        AssistantCard card7 = new AssistantCard(7, 4);
        cards.put(6, card7);
        AssistantCard card8 = new AssistantCard(8, 4);
        cards.put(7, card8);
        AssistantCard card9 = new AssistantCard(9, 5);
        cards.put(8, card9);
        AssistantCard card10 = new AssistantCard(10, 5);
        cards.put(9, card10);

        assistantCardValue = new SimpleBooleanProperty();
        assistantCardValue.set(false);
    }

    /**
     * Gets the nickname of the mock player
     *
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Gets the school board of the player
     *
     * @return the school board
     */
    public MockSchoolBoard getSchoolBoard() {
        return schoolBoard;
    }

    /**
     * Sets the coins of the player
     *
     * @param coins the coins
     */
    public synchronized void setCoins(int coins) {
        this.coins.setValue(coins);
    }

    /**
     * Gets the coins available for this player
     *
     * @return the coins
     */
    public synchronized int getCoins() {
        return coins.getValue();
    }

    /**
     * Gets the colour of the towers
     *
     * @return the towers' colour
     */
    public synchronized TowerColour getTowerColour() {
        return towerColour;
    }

    /**
     * Sets the colour of the towers
     *
     * @param colour the colour of the towers
     */
    public synchronized void setTowerColour(TowerColour colour) {
        this.towerColour = colour;
    }

    /**
     * Gets the assistant cards in the player's deck
     *
     * @return the list of the assistant cards
     */
    public HashMap<Integer, AssistantCard> getCards() {
        return new HashMap<>(cards);
    }

    /**
     * Gets the assistant cards in the player's deck as an ObservableMap
     *
     * @return the list of the assistant cards as an ObservableMap
     */
    public ObservableMap<Integer, AssistantCard> getCardsProperty() {
        return cards;
    }

    /**
     * Sets the current assistant card
     *
     * @param currentAssistantCard the assistant card played by the player in the current round
     */
    public synchronized void setCurrentAssistantCard(int currentAssistantCard) {
        this.currentAssistantCard.setValue(cards.get(currentAssistantCard - 1));
        cards.remove(currentAssistantCard - 1);
    }

    /**
     * Gets the current assistant card
     *
     * @return the current assistant card
     */
    public synchronized AssistantCard getCurrentAssistantCard() {
        return currentAssistantCard.getValue();
    }

    /**
     * Gets the current assistant card as a Property
     *
     * @return the current assistant card as a Property
     */
    public Property<AssistantCard> getCurrentAssistantCardProperty() {
        return currentAssistantCard;
    }

    /**
     * Gets the wizard chosen by the player
     *
     * @return the wizard chosen by the player
     */
    public Wizard getWizard() {
        return wizard;
    }

    /**
     * Gets the owned by the player
     *
     * @return the number of coins owned by the player as an IntegerProperty
     */
    public IntegerProperty getCoinsProperty() {
        return coins;
    }

    /**
     * States whether the assistant card value has been set or not as a BooleanProperty
     *
     * @return true if the assistant card has been played so that the value has been set, false otherwise
     */
    public BooleanProperty isAssistantCardValueProperty() {
        return assistantCardValue;
    }

    /**
     * States whether the assistant card value has been set or not
     *
     * @return true if the assistant card has been played so that the value has been set, false otherwise
     */
    public synchronized boolean isAssistantCardValue() {
        return assistantCardValue.getValue();
    }

    /**
     * Sets assistantCardValue to true if the value of the assistant card has been set, false otherwise
     *
     * @param assistantCardValue true if the assistantCard has been played so that the value has been set
     */
    public synchronized void setAssistantCardValue(boolean assistantCardValue) {
        this.assistantCardValue.setValue(assistantCardValue);
    }

    /**
     * Removes the assistants cards from the list of assistant cards available
     *
     * @param i the value of the assistant card to be removed
     */
    public synchronized void removeAssistantCard(int i) {
        cards.remove(i);
    }
}