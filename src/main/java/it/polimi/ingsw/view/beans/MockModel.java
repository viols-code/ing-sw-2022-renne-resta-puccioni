package it.polimi.ingsw.view.beans;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.GamePhase;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.model.player.Wizard;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.List;

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
    private final ObservableMap<String, MockPlayer> players;

    /**
     * The current player
     */
    private final Property<MockPlayer> currentPlayer;

    /**
     * A local copy of the game table
     */
    private final MockTable table;

    /**
     * The current round
     */
    private final IntegerProperty round;

    /**
     * A variable that states if the game mode is expert or not
     */
    private boolean isGameExpert;

    /**
     * Number of coins available
     */
    private final IntegerProperty coins;

    /**
     * A list that contains the character cards drawn for this game
     */
    private final ObservableList<MockCard> characterCards;

    /**
     * Character card played by the current player
     */
    private final Property<MockCard> currentCharacterCard;

    /**
     * A variable that indicates the current game phase
     */
    private final Property<GamePhase> gamePhase;

    /**
     * A variable that indicates the current turn phase
     */
    private final Property<TurnPhase> turnPhase;

    /**
     * The winner
     */
    private final Property<MockPlayer> winner;

    /**
     * The row of the student selected
     */
    private final IntegerProperty position;

    /**
     * The colour selected
     */
    private Colour selectedColour;

    private final IntegerProperty currentPlayers;

    private final IntegerProperty playersToStart;

    private final ObservableList<String> nicknames;

    /**
     * Constructs the local copy of the game
     */
    public MockModel() {
        this.localPlayer = null;
        currentPlayer = new SimpleObjectProperty<>();
        coins = new SimpleIntegerProperty(-1);
        characterCards = FXCollections.observableArrayList();
        players = FXCollections.observableHashMap();
        table = new MockTable();
        round = new SimpleIntegerProperty();
        currentCharacterCard = new SimpleObjectProperty<>();
        gamePhase = new SimpleObjectProperty<>();
        turnPhase = new SimpleObjectProperty<>();
        winner = new SimpleObjectProperty<>();
        position = new SimpleIntegerProperty();
        position.setValue(-1);
        currentPlayers = new SimpleIntegerProperty();
        playersToStart = new SimpleIntegerProperty();
        nicknames = FXCollections.observableArrayList();
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
    public ObservableMap<String, MockPlayer> getPlayers() {
        return players;
    }

    /**
     * Gets the player by the given nickname
     *
     * @return the player with the given nickname
     */
    public MockPlayer getPlayerByNickname(String nickname) throws IllegalArgumentException {
        List<String> matches = this.players.keySet()
                .stream()
                .filter(player -> player.equalsIgnoreCase(nickname)).toList();
        if (matches.size() == 1) {
            return players.get(matches.get(0));
        } else {
            throw new IllegalArgumentException("There is no player with that nickname");
        }
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
     */
    public void addPlayer(String nickname, Wizard wizard, boolean gameMode, boolean localPlayer) {
        List<String> matches = this.players.keySet()
                .stream()
                .filter(player -> player.equalsIgnoreCase(nickname)).toList();
        if (matches.size() == 0) {
            MockPlayer newPlayer = new MockPlayer(nickname, wizard, gameMode, localPlayer);
            players.put(nickname, newPlayer);
            if (localPlayer) {
                this.localPlayer = newPlayer;
            }
        }
    }

    /**
     * Gets the current Player
     *
     * @return the current Player
     */
    public MockPlayer getCurrentPlayer() {
        return currentPlayer.getValue();
    }

    /**
     * Sets the current Player
     *
     * @param currentPlayer the current Player
     */
    public void setCurrentPlayer(MockPlayer currentPlayer) {
        this.currentPlayer.setValue(currentPlayer);
    }

    /**
     * Gets the local copy of the game table
     *
     * @return the local copy of the game table
     */
    public MockTable getTable() {
        return table;
    }

    /**
     * Gets the current round
     *
     * @return the current round
     */
    public int getRound() {
        return round.getValue();
    }

    /**
     * Sets the round
     *
     * @param round the round to set
     */
    public void setRound(int round) {
        this.round.setValue(round);
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
     * Sets the gameMode
     *
     * @param isGameExpert true if the gameMode is expert, false if it is basic
     */
    public void setGameMode(boolean isGameExpert) {
        this.isGameExpert = isGameExpert;
    }

    /**
     * Gets the number of coins available
     *
     * @return the number of coins
     */
    public int getCoins() {
        return coins.getValue();
    }

    /**
     * Sets the number of coins available on the table
     *
     * @param coins the number of coins available
     */
    public void setCoins(int coins) {
        this.coins.setValue(coins);
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
        List<MockCard> card = characterCards.stream().filter(characterCard -> characterCard.getType().equals(type)).toList();
        if (card.size() > 0) {
            return card.get(0);
        } else
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
        return currentCharacterCard.getValue();
    }

    /**
     * Sets the current character card
     *
     * @param currentCharacterCard the card
     */
    public void setCurrentCharacterCard(MockCard currentCharacterCard) {
        this.currentCharacterCard.setValue(currentCharacterCard);
    }

    /**
     * Gets the current game phase
     *
     * @return the game phase
     */
    public GamePhase getGamePhase() {
        return gamePhase.getValue();
    }

    /**
     * Sets the current game phase
     *
     * @param gamePhase the game phase
     */
    public void setGamePhase(GamePhase gamePhase) {
        this.gamePhase.setValue(gamePhase);
    }

    /**
     * Gets the current turn phase
     *
     * @return the turn phase
     */
    public TurnPhase getTurnPhase() {
        return turnPhase.getValue();
    }

    /**
     * Sets the current turn phase
     *
     * @param turnPhase the turn phase
     */
    public void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase.setValue(turnPhase);
    }

    /**
     * Gets the winner of the game
     *
     * @return the winner of the game
     */
    public MockPlayer getWinner() {
        return winner.getValue();
    }

    /**
     * Sets the winner of the game
     *
     * @param winner the winner of the game
     */
    public void setWinner(MockPlayer winner) {
        this.winner.setValue(winner);
    }

    public void updatePlayerCount(int currentPlayers, int playersToStart) {
        Platform.runLater(() -> {
            this.currentPlayers.setValue(currentPlayers);
            this.playersToStart.setValue(playersToStart);
        });
    }

    public Property<MockPlayer> getCurrentPlayerProperty() {
        return currentPlayer;
    }

    public IntegerProperty getRoundProperty() {
        return round;
    }

    public Property<TurnPhase> getTurnPhaseProperty() {
        return this.turnPhase;
    }

    public IntegerProperty currentPlayersProperty() {
        return currentPlayers;
    }

    public IntegerProperty playersToStartProperty() {
        return playersToStart;
    }

    public void addPlayerNickname(String name){
        this.nicknames.add(name);
    }

    public ObservableList<String> getNicknames(){
        return this.nicknames;
    }

    public IntegerProperty getCoinsProperty() {
        return coins;
    }

    public IntegerProperty getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position.setValue(position);
    }


    public Colour getSelectedColour() {
        return selectedColour;
    }

    public void setSelectedColour(Colour selectedColour) {
        this.selectedColour = selectedColour;
    }
}