package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Wizard;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Player
 *
 * @version 1.0
 */
public abstract class Player {

    /**
     * the nickname of the player
     */
    private final String nickname;

    /**
     * the wizard chosen by the player
     */
    private final Wizard wizard;

    /**
     * a Set containing the AssistantCard of the player
     */
    private final Set<AssistantCard> assistantCardSet;

    /**
     * card played by the player in the current round
     */
    private AssistantCard currentAssistantCard;

    /**
     * the school board associated to the player
     */
    private final SchoolBoard schoolBoard;

    /**
     * Constructor: creates a new Player with the given nick and the given wizard
     *
     * @param nickname a string with the nickname
     * @param wizard   the wizard chosen by the player
     */
    public Player(String nickname, Wizard wizard) {
        this.nickname = nickname;
        this.wizard = wizard;
        assistantCardSet = new HashSet<>();
        currentAssistantCard = null;
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
    WIZARD
     */

    /**
     * Get the wizard chosen by the player
     *
     * @return the wizard chosen by the player
     */
    public Wizard getWizard() {
        return wizard;
    }


    /*
    ASSISTANT CARD SET
     */

    /**
     * Return true if the assistant card given in present, false otherwise
     *
     * @param card the assistant card
     * @return true if the v card given in present, false otherwise
     */
    public boolean isAssistantCardPresent(AssistantCard card) {
        return assistantCardSet.contains(card);
    }

    /**
     * Add the given assistant card
     *
     * @param card the assistant card to be added
     */
    public void addAssistantCard(AssistantCard card) {
        assistantCardSet.add(card);
    }

    /**
     * Remove the given assistant card
     *
     * @param card the assistant card to be removed
     */
    public void removeAssistantCard(AssistantCard card) {
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
     * @throws IllegalAccessError if the mode is basic
     */
    public int getCoins() throws IllegalAccessError {
        throw new IllegalAccessError("This is for the Expert Mode");
    }

    /**
     * Add the given number of coins
     *
     * @param num the number of coins to add to the player
     * @throws IllegalAccessError if the mode is basic
     */
    public void addCoins(int num) throws IllegalAccessError {
        throw new IllegalAccessError("This is for the Expert Mode");
    }

    /**
     * Remove the given number of coins
     *
     * @param num the number of coins to remove to the player
     * @throws IllegalAccessError if the mode is basic
     */
    public void removeCoins(int num) throws IllegalAccessError {
        throw new IllegalAccessError("This is for the Expert Mode");
    }

    /*
     * SCHOOL BOARD
     */

    /**
     * Get the school board of the player
     *
     * @return the school board of the player
     */
    public SchoolBoard getSchoolBoard() {
        return schoolBoard;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return getCoins() == player.getCoins() && Objects.equals(getNickname(), player.getNickname()) && Objects.equals(getCurrentAssistantCard(), player.getCurrentAssistantCard()) && Objects.equals(getSchoolBoard(), player.getSchoolBoard());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNickname(), getCurrentAssistantCard(), getCoins(), getSchoolBoard());
    }
}