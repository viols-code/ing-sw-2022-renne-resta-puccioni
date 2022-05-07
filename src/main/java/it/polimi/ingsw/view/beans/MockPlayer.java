package it.polimi.ingsw.view.beans;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.player.TowerColour;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains a local copy of the player
 */
public class MockPlayer {
    /**
     * A string than represents the nickname of the player
     */
    private final String nickname;

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
    private final TowerColour towerColour;

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
     * @param towerColour the colour of the tower
     */
    public MockPlayer(String nickname, boolean hasCoins, TowerColour towerColour) {
        this.nickname = nickname;
        this.hasCoins = hasCoins;
        this.towerColour = towerColour;
        schoolBoard = new MockSchoolBoard();
        cards = new ArrayList<>();
        currentAssistantCard = null;
        if (hasCoins) {
            coins = 1;
        } else {
            coins = 0;
        }
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
    public void setCurrentAssistantCard(AssistantCard currentAssistantCard) {
        this.currentAssistantCard = currentAssistantCard;
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
