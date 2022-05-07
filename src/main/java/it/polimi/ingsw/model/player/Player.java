package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.messages.CurrentAssistantCardUpdate;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.IServerPacket;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Player
 *
 * @version 1.0
 */
public abstract class Player extends Observable<IServerPacket> {
    /**
     * the nickname of the player
     */
    protected final String nickname;
    /**
     * the wizard chosen by the player
     */
    protected final Wizard wizard;
    /**
     * The colour of the tower
     */
    protected final TowerColour towerColour;
    /**
     * a Set containing the AssistantCard of the player
     */
    protected final Set<AssistantCard> assistantCardSet;
    /**
     * card played by the player in the current round
     */
    protected AssistantCard currentAssistantCard;
    /**
     * the school board associated to the player
     */
    protected final SchoolBoard schoolBoard;
    /**
     * true if the player has already played in the round, false otherwise
     */
    protected boolean hasAlreadyPlayed;

    /**
     * Constructor: creates a new Player with the given nick and the given wizard
     *
     * @param nickname a string with the nickname
     * @param wizard   the wizard chosen by the player
     */
    public Player(String nickname, Wizard wizard, TowerColour towerColour) {
        this.nickname = nickname;
        this.wizard = wizard;
        this.towerColour = towerColour;
        assistantCardSet = new HashSet<>();
        currentAssistantCard = null;
        schoolBoard = new SchoolBoard(nickname);
        hasAlreadyPlayed = false;
    }

    /*
     NICKNAME
     */

    /**
     * Get the nickname of the player.
     *
     * @return a string with the nickname
     */
    public String getNickname() {
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

    /**
     * Get the TowerColour
     *
     * @return the towerColour
     */
    public TowerColour getTowerColour() {
        return towerColour;
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
        notify(new CurrentAssistantCardUpdate(currentAssistantCard.getValue()));
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

    /*
    HAS ALREADY PLAYED
     */

    /**
     * Return hasAlreadyPlayed
     *
     * @return true if the player has already played in the round, false otherwise
     */
    public boolean getHasAlreadyPlayed() {
        return hasAlreadyPlayed;
    }

    /**
     * Set hasAlreadyPlayed
     *
     * @param hasAlreadyPlayed true if the player has already played in the round, false otherwise
     */
    public void setHasAlreadyPlayed(boolean hasAlreadyPlayed) {
        this.hasAlreadyPlayed = hasAlreadyPlayed;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;
        return getNickname().equals(player.getNickname()) && getWizard() == player.getWizard() && assistantCardSet.equals(player.assistantCardSet) && Objects.equals(getCurrentAssistantCard(), player.getCurrentAssistantCard()) && getSchoolBoard().equals(player.getSchoolBoard());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNickname(), getWizard(), assistantCardSet, getCurrentAssistantCard(), getSchoolBoard());
    }
}