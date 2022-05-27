package it.polimi.ingsw.view.beans;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.player.TowerColour;
import it.polimi.ingsw.model.player.Wizard;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.HashMap;
import java.util.stream.Collectors;

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
     * The current assistant card
     */
    private final boolean localPlayer;

    /**
     * The player's school board
     */
    private final MockSchoolBoard schoolBoard;

    /**
     * An attribute that indicates if the player has coins or not
     */
    private final boolean hasCoins;

    /**
     * Number of coins
     */
    private IntegerProperty coins;

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
    private Property<AssistantCard> currentAssistantCard;


    /**
     * Constructs the mock player
     *
     * @param nickname the string containing the nickname
     * @param hasCoins true for expert game mode, false for basic game mode
     */
    public MockPlayer(String nickname, Wizard wizard, boolean hasCoins, boolean localPlayer) {
        this.nickname = nickname;
        this.wizard = wizard;
        this.localPlayer = localPlayer;
        this.hasCoins = hasCoins;
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
     * Gets the variable hasCoins
     *
     * @return true for expert game mode, false for basic game mode
     */
    public boolean isHasCoins() {
        return hasCoins;
    }

    /**
     * Sets the coins of the player
     *
     * @param coins the coins
     */
    public void setCoins(int coins) {
        this.coins.setValue(coins);
    }

    /**
     * Gets the coins available for this player
     *
     * @return the coins
     */
    public int getCoins() {
        return coins.getValue();
    }

    /**
     * Gets the colour of the tower
     *
     * @return the tower colour
     */
    public TowerColour getTowerColour() {
        return towerColour;
    }

    /**
     * Sets the colour of the tower
     *
     * @param colour the colour of the tower
     */
    public void setTowerColour(TowerColour colour) {
        this.towerColour = colour;
    }

    /**
     * Gets the assistant card in the player's deck
     *
     * @return the list of the assistant card
     */
    public HashMap<Integer, AssistantCard> getCards() {
        return new HashMap<>(cards);
    }

    /**
     * Sets the current assistant card
     *
     * @param currentAssistantCard the assistant card played
     */
    public void setCurrentAssistantCard(int currentAssistantCard) {
        this.currentAssistantCard.setValue(cards.get(currentAssistantCard - 1));
        cards.remove(currentAssistantCard - 1);
    }

    /**
     * Gets the current assistant card
     *
     * @return the current assistant card
     */
    public AssistantCard getCurrentAssistantCard() {
        return currentAssistantCard.getValue();
    }


}