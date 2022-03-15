package it.polimi.ingsw;

import java.util.HashSet;
import java.util.Set;

/**
 * Player
 *
 * @version 1.0
 */
public class Player {

    /**
     * the nickname of the player
     */
    private final String nickname;

    /**
     * a Set containing the AssistantCard of the player
     */
    private final Set<AssistantCard> assistantCardSet;

    /**
     * card played by the player in the current round
     */
    private AssistantCard currentAssistantCard;

    /**
     * number of coins
     */
    private int coins;

    /**
     * the school board associated to the player
     */
    private final SchoolBoard schoolBoard;

    /**
     * Constructor: creates a new Player with the given nick and the given number of towers
     *
     * @param nickname a string with the nickname
     */
    public Player(String nickname) {
        this.nickname = nickname;
        assistantCardSet = new HashSet<>();
        currentAssistantCard = null;
        coins = 1;
        schoolBoard = new SchoolBoard();
    }

    /*
     NICKNAME
     */

    /**
     * Get the nickname of the player.
     *
     * @return a string with the nickname
     */
    public synchronized String getNickname() {
        return nickname;
    }

    /*
    ASSISTANT CARD SET
     */
    public Set<AssistantCard> getAssistantCardSet() {
        return assistantCardSet;
    }


    /*
    ASSISTANT CARD LIST
     */
    public void addAssistantCardList(AssistantCard card){
        assistantCardSet.add(card);
    }

    /**
     * Remove the given card from the assistantCardList and set the card as the currentAssistantCard
     *
     * @param card the assistant card used by the player in that round
     * @throws IllegalArgumentException when the assistant card given is already been played
     */
    public void playAssistantCard(AssistantCard card) throws IllegalArgumentException {
        if (!assistantCardSet.contains(card)) {
            throw new IllegalArgumentException("The card has already been played");
        }
        setCurrentAssistantCard(card);
        assistantCardSet.remove(card);
    }

    /*
    CURRENT ASSISTANT CARD
     */

    /**
     * Get the currentAssistantCard
     *
     * @return the card played by the player in the current round
     */
    public AssistantCard getCurrentAssistantCard() {
        return currentAssistantCard;
    }

    /**
     * Set the currentAssistantCard
     *
     * @param currentAssistantCard the card played by the player in the current round
     */
    public void setCurrentAssistantCard(AssistantCard currentAssistantCard) {
        this.currentAssistantCard = currentAssistantCard;
    }

    /*
    COINS
     */

    /**
     * Get the number of coins
     *
     * @return the number of coins
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Add the given number of coins
     *
     * @param num the number of coins to add to the player
     */
    public void addCoins(int num) {
        coins += num;
    }

    /**
     * Remove the given number of coins
     *
     * @param num the number of coins to remove to the player
     * @throws IllegalArgumentException when there are not enough coins to do the operation
     */
    public void removeCoins(int num) throws IllegalArgumentException {
        if (coins - num < 0) {
            throw new IllegalArgumentException("There are not enough coins");
        }

        coins -= num;
    }

    /**
     * Get the school board of the player
     *
     * @return the school board of the player
     */
    public SchoolBoard getSchoolBoard(){
        return schoolBoard;
    }
}