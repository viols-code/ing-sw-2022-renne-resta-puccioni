package it.polimi.ingsw.view.beans;

import it.polimi.ingsw.model.game.GamePhase;
import it.polimi.ingsw.model.game.TurnPhase;

import java.util.ArrayList;
import java.util.List;

/**
 * Local copy of the game model
 */
public class MockModel {

    /**
     * The local player
     */
    private final MockPlayer localPlayer;

    /**
     * A list that contains the players in this game
     */
    private List<MockPlayer> players;

    /**
     * A local copy of the game table
     */
    private MockTable table;

    /**
     * A variable that states if the game mode is expert or not
     */
    private boolean isGameExpert;

    /**
     * Number of coins available
     */
    private int coins;

    /**
     * A list that contains the character cards drawn for this game
     */
    private List<MockCard> characterCards;

    /**
     * Character card played by the current player
     */
    private MockCard currentCharacterCard;

    /**
     * A variable that indicates the current game phase
     */
    private GamePhase gamePhase;

    /**
     * A variable that indicates the current turn phase
     */
    private TurnPhase turnPhase;

    /**
     * Constructs the local copy of the game
     */
    public MockModel() {
        this.localPlayer = null;
        currentCharacterCard = null;
        coins = -1;
        characterCards = new ArrayList<>();
        players = new ArrayList<>();
        table = new MockTable();
        currentCharacterCard = null;
        gamePhase = null;
        turnPhase = null;
    }

    /**
     * Gets the local player
     *
     * @return the local player
     */
    public MockPlayer getLocalPlayer() {
        return localPlayer;
    }

    /**
     * Gets the list of players in this game
     *
     * @return the players in this game
     */
    public List<MockPlayer> getPlayers() {
        return players;
    }

    /**
     * Adds all the players to the list
     *
     * @param players the list of player in the game
     */
    public void setPlayers(List<MockPlayer> players) {
        this.players = players;
    }

    /**
     * Gets the local copy of the game table
     *
     * @return the locl copy of the game table
     */
    public MockTable getTable() {
        return table;
    }

    /**
     * Gets the game mode
     *
     * @return true if the game mode is expert, false if the game mode is basic
     */
    public boolean isGameExpert() {
        return isGameExpert;
    }

    /**
     * Gets the number of coins available
     *
     * @return the number of coins
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Sets the number of coins available on the table
     *
     * @param coins the number of coins available
     */
    public void setCoins(int coins) {
        this.coins = coins;
    }

    /**
     * The list containing the character cards drawn for this game
     *
     * @return the character cards
     */
    public List<MockCard> getCharacterCards() {
        return characterCards;
    }

    /**
     * Adds a character card to the list
     *
     * @param characterCard the card
     */
    public void addCharacterCard(MockCard characterCard) {
        characterCards.add(characterCard);
    }

    /**
     * Gets the current character card
     *
     * @return the current character card
     */
    public MockCard getCurrentCharacterCard() {
        return currentCharacterCard;
    }

    /**
     * Sets the current character card
     *
     * @param currentCharacterCard the card
     */
    public void setCurrentCharacterCard(MockCard currentCharacterCard) {
        this.currentCharacterCard = currentCharacterCard;
    }

    /**
     * Gets the current game phase
     *
     * @return the game phase
     */
    public GamePhase getGamePhase() {
        return gamePhase;
    }

    /**
     * Sets the current game phase
     *
     * @param gamePhase the game phase
     */
    public void setGamePhase(GamePhase gamePhase) {
        this.gamePhase = gamePhase;
    }

    /**
     * Gets the current turn phase
     *
     * @return the turn phase
     */
    public TurnPhase getTurnPhase() {
        return turnPhase;
    }

    /**
     * Sets the current turn phase
     *
     * @param turnPhase the turn phase
     */
    public void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
    }
}
