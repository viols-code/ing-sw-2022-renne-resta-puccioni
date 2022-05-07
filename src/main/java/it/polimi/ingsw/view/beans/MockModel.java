package it.polimi.ingsw.view.beans;

import it.polimi.ingsw.model.game.GamePhase;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.model.player.Wizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Local copy of the game model
 */
public class MockModel {

    /**
     * The local player
     */
    private MockPlayer localPlayer;

    /**
     * A list that contains the players in this game
     */
    private HashMap<String, MockPlayer> players;

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
        players = new HashMap<>();
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
    public HashMap<String, MockPlayer> getPlayers() {
        return players;
    }

    /**
     * Gets the player by the given nickname
     *
     * @return the player with the given nickname
     */
    public MockPlayer getPlayerByNickname(String nickname) {
        return players.get(nickname);
    }

    /**
     * Sets the MockPlayer that is playing with this instance of the client.
     *
     * @param localPlayer the local player to be set
     */
    public void setLocalPlayer(MockPlayer localPlayer) {
        this.localPlayer = localPlayer;
    }

    /**
     * Adds the player in the list
     *
     */
    public MockPlayer addPlayer(String nickname, Wizard wizard, boolean gameMode,boolean localPlayer) {
        if (!players.containsKey(nickname.toLowerCase())) {
            MockPlayer newPlayer = new MockPlayer(nickname, wizard, gameMode, localPlayer);
            players.put(nickname.toLowerCase(), newPlayer);
            return newPlayer;
        } else
            return players.get(nickname.toLowerCase());
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

    public void setGameMode(boolean isGameExpert){
        this.isGameExpert = isGameExpert;
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
     * Gets the mock card with the given index
     *
     * @param index the index
     * @return the mock card
     */
    public MockCard getCharacterCardByIndex(int index) {
        return characterCards.get(index);
    }

    /**
     * Gets the mock card with the given type
     *
     * @param type the type
     * @return the mock card
     */
    public MockCard getCharacterCardByType(CharacterCardEnumeration type) {
        List<MockCard> card = characterCards.stream().filter(characterCard -> characterCard.getType().equals(type)).collect(Collectors.toList());
        if(card.size() > 0){
            return card.get(0);
        }
        else
            return null;
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
