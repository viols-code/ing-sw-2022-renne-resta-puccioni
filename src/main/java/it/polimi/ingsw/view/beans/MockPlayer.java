package it.polimi.ingsw.view.beans;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.player.TowerColour;
import it.polimi.ingsw.model.player.Wizard;

import java.util.ArrayList;
import java.util.List;
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
     * An attibute that indicates if the player has coins or not
     */
    private final boolean hasCoins;

    /**
     * Number of coins
     */
    private int coins;

    /**
     * The colour of the tower given to the player
     */
    private TowerColour towerColour;

    /**
     * A list representing the assistant card deck
     */
    private final List<AssistantCard> cards;

    /**
     * The current assistant card
     */
    private AssistantCard currentAssistantCard;


    /**
     * Constructs the mock player
     *
     * @param nickname    the string containing the nickname
     * @param hasCoins    true for expert game mode, false for basic game mode
     */
    public MockPlayer(String nickname, Wizard wizard, boolean hasCoins, boolean localPlayer) {
        this.nickname = nickname;
        this.wizard = wizard;
        this.localPlayer = localPlayer;
        this.hasCoins = hasCoins;
        this.towerColour = null;
        schoolBoard = new MockSchoolBoard();
        cards = new ArrayList<>();
        currentAssistantCard = null;
        if (hasCoins) {
            coins = 1;
        } else {
            coins = 0;
        }

        AssistantCard card1 = new AssistantCard(1, 1);
        cards.add(card1);
        AssistantCard card2 = new AssistantCard(2, 1);
        cards.add(card2);
        AssistantCard card3 = new AssistantCard(3, 2);
        cards.add(card3);
        AssistantCard card4 = new AssistantCard(4, 2);
        cards.add(card4);
        AssistantCard card5 = new AssistantCard(5, 3);
        cards.add(card5);
        AssistantCard card6 = new AssistantCard(6, 3);
        cards.add(card6);
        AssistantCard card7 = new AssistantCard(7, 4);
        cards.add(card7);
        AssistantCard card8 = new AssistantCard(8, 4);
        cards.add(card8);
        AssistantCard card9 = new AssistantCard(9, 5);
        cards.add(card9);
        AssistantCard card10 = new AssistantCard(10, 5);
        cards.add(card10);
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
        this.coins = coins;
    }

    /**
     * Gets the coins available for this player
     *
     * @return the coins
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Gets the colour of the tower
     *
     * @return the tower colour
     */
    public TowerColour getTowerColour() {
        return towerColour;
    }

    public void setTowerColour(TowerColour colour){
        this.towerColour = colour;
    }

    /**
     * Gets the assistant card in the player's deck
     *
     * @return the list of the assistant card
     */
    public List<AssistantCard> getCards() {
        return cards;
    }

    /**
     * Sets the current assistant card
     *
     * @param currentAssistantCard the assistant card played
     */
    public void setCurrentAssistantCard(int currentAssistantCard) {
        this.currentAssistantCard = cards.stream().filter(card -> card.getValue() == currentAssistantCard).collect(Collectors.toList()).get(0);
    }

    /**
     * Gets the current assistant card
     *
     * @return the current assistant card
     */
    public AssistantCard getCurrentAssistantCard() {
        return currentAssistantCard;
    }


}
