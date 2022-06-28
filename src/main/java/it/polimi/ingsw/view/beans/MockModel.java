package it.polimi.ingsw.view.beans;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.GamePhase;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.model.player.Wizard;
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
     * Number of coins available on the table
     */
    private final IntegerProperty coins;

    /**
     * A list that contains the character cards drawn for this game
     */
    private final ObservableList<MockCard> characterCards;

    /**
     * The basic state
     */
    private final MockCard basicState;

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

    /**
     * The row of the student selected
     */
    private final IntegerProperty positionExchange;

    /**
     * The colour selected
     */
    private Colour selectedColourExchange;

    /**
     * The student on card selected
     */
    private final Property<Colour> studentOnCardSelected;

    /**
     * A list containing the nicknames of the players
     */
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
        basicState = new MockCard(CharacterCardEnumeration.BASIC_STATE);
        setCurrentCharacterCard(basicState);
        position.setValue(-1);
        nicknames = FXCollections.observableArrayList();
        studentOnCardSelected = new SimpleObjectProperty<>();
        positionExchange = new SimpleIntegerProperty();
        positionExchange.setValue(-1);
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
     * @param nickname the nickname of the player
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
     * Adds the player in the list
     *
     * @param nickname    the nickname of the player to add
     * @param wizard      the wizard chosen by the player to add
     * @param gameMode    the game mode
     * @param localPlayer true if the player is the local player of this view
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
    public synchronized MockPlayer getCurrentPlayer() {
        return currentPlayer.getValue();
    }

    /**
     * Sets the current Player
     *
     * @param currentPlayer the current Player
     */
    public synchronized void setCurrentPlayer(MockPlayer currentPlayer) {
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
    public synchronized int getRound() {
        return round.getValue();
    }

    /**
     * Sets the round
     *
     * @param round the round to set
     */
    public synchronized void setRound(int round) {
        this.round.setValue(round);
    }

    /**
     * Gets the game mode
     *
     * @return true if the game mode is expert, false if the game mode is basic
     */
    public synchronized boolean isGameExpert() {
        return isGameExpert;
    }

    /**
     * Sets the gameMode
     *
     * @param isGameExpert true if the gameMode is expert, false if it is basic
     */
    public synchronized void setGameMode(boolean isGameExpert) {
        this.isGameExpert = isGameExpert;
    }

    /**
     * Gets the number of coins available on the table
     *
     * @return the number of coins on the table
     */
    public synchronized int getCoins() {
        return coins.getValue();
    }

    /**
     * Sets the number of coins available on the table
     *
     * @param coins the number of coins available
     */
    public synchronized void setCoins(int coins) {
        this.coins.setValue(coins);
    }

    /**
     * Gets the mock card with the given index
     *
     * @param index the index of the character card
     * @return the mock card with the given index
     */
    public MockCard getCharacterCardByIndex(int index) {
        return characterCards.get(index);
    }

    /**
     * Gets the mock card with the given type
     *
     * @param type the type of the character card
     * @return the mock card of the give type
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
    public synchronized MockCard getCurrentCharacterCard() {
        return currentCharacterCard.getValue();
    }

    /**
     * Sets the current character card
     *
     * @param currentCharacterCard the card
     */
    public synchronized void setCurrentCharacterCard(MockCard currentCharacterCard) {
        this.currentCharacterCard.setValue(currentCharacterCard);
    }

    /**
     * Gets the current game phase
     *
     * @return the game phase
     */
    public synchronized GamePhase getGamePhase() {
        return gamePhase.getValue();
    }

    /**
     * Sets the current game phase
     *
     * @param gamePhase the game phase
     */
    public synchronized void setGamePhase(GamePhase gamePhase) {
        this.gamePhase.setValue(gamePhase);
    }

    /**
     * Gets the current turn phase
     *
     * @return the turn phase
     */
    public synchronized TurnPhase getTurnPhase() {
        return turnPhase.getValue();
    }

    /**
     * Sets the current turn phase
     *
     * @param turnPhase the turn phase
     */
    public synchronized void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase.setValue(turnPhase);
    }

    /**
     * Gets the winner of the game
     *
     * @return the winner of the game
     */
    public synchronized MockPlayer getWinner() {
        return winner.getValue();
    }

    /**
     * Sets the winner of the game
     *
     * @param winner the winner of the game
     */
    public synchronized void setWinner(MockPlayer winner) {
        this.winner.setValue(winner);
    }

    /**
     * Gets the current player as a Property<MockPlayer>
     *
     * @return the current player as a Property<MockPlayer>
     */
    public Property<MockPlayer> getCurrentPlayerProperty() {
        return currentPlayer;
    }

    /**
     * Gets the current round as an IntegerProperty
     *
     * @return the current round as an IntegerProperty
     */
    public IntegerProperty getRoundProperty() {
        return round;
    }

    /**
     * Gets the turn phase as a Property<TurnPhase>
     *
     * @return the turnPhase as a Property<TurnPhase>
     */
    public Property<TurnPhase> getTurnPhaseProperty() {
        return this.turnPhase;
    }

    /**
     * Adds a nickname to the list of nicknames of the players connected (used to show the list of names in the WaitingPlayers page)
     *
     * @param name the nickname of the player to add
     */
    public void addPlayerNickname(String name) {
        this.nicknames.add(name);
    }

    /**
     * Gets the list of nicknames of the players connected
     *
     * @return the ObservableList with the nicknames
     */
    public ObservableList<String> getNicknames() {
        return this.nicknames;
    }

    /**
     * Gets the number of coins available on the table as an IntegerProperty
     *
     * @return the number of coins available on the table as an IntegerProperty
     */
    public IntegerProperty getCoinsProperty() {
        return coins;
    }

    /**
     * Gets the position of the student selected as an IntegerProperty
     *
     * @return the position of the student selected as an IntegerProperty
     */
    public synchronized IntegerProperty getPosition() {
        return position;
    }

    /**
     * Sets the position of the student selected as an IntegerProperty
     *
     * @param position the position of the student selected as an IntegerProperty
     */
    public synchronized void setPosition(int position) {
        this.position.setValue(position);
    }

    /**
     * Gets the colour selected
     *
     * @return the colour selected
     */
    public synchronized Colour getSelectedColour() {
        return selectedColour;
    }

    /**
     * Sets the colour selected
     *
     * @param selectedColour the colour selected
     */
    public synchronized void setSelectedColour(Colour selectedColour) {
        this.selectedColour = selectedColour;
    }

    /**
     * Gets the colour selected for the exchange character card
     *
     * @return the colour selected
     */
    public synchronized Colour getSelectedColourExchange() {
        return selectedColourExchange;
    }

    /**
     * Sets the colour selected for the exchange character card
     *
     * @param selectedColour the colour selected
     */
    public synchronized void setSelectedColourExchange(Colour selectedColour) {
        this.selectedColourExchange = selectedColour;
    }

    /**
     * Gets the position of the student selected for the exchange character card as an IntegerProperty
     *
     * @return the position of the student selected as an IntegerProperty
     */
    public synchronized IntegerProperty getPositionExchange() {
        return positionExchange;
    }

    /**
     * Sets the position of the student selected for the exchange character card as an IntegerProperty
     *
     * @param position the position of the student selected as an IntegerProperty
     */
    public synchronized void setPositionExchange(int position) {
        this.positionExchange.setValue(position);
    }

    /**
     * Gets the basic state
     *
     * @return the basic state
     */
    public synchronized MockCard getBasicState() {
        return basicState;
    }

    /**
     * Gets the current character card as a Property<MockCard>
     *
     * @return the current character card as a Property<MockCard>
     */
    public Property<MockCard> currentCharacterCardProperty() {
        return currentCharacterCard;
    }

    /**
     * States if the character card given is one of the three cards available in the game
     *
     * @param type the type of the character card
     * @return true if the character card is present false if it isn't
     */
    public boolean isCharacterCardPresent(CharacterCardEnumeration type) {
        return characterCards.stream().filter(card -> card.getType().equals(type)).count() == 1;
    }

    /**
     * Gets the student that has been selected from the card
     *
     * @return the colour of the student that has been selected
     */
    public synchronized Colour getStudentOnCardSelected() {
        return studentOnCardSelected.getValue();
    }

    /**
     * Sets the colour of the student selected from the card
     *
     * @param studentOnCardSelected the student that has been selected
     */
    public synchronized void setStudentOnCardSelected(Colour studentOnCardSelected) {
        this.studentOnCardSelected.setValue(studentOnCardSelected);
    }

    /**
     * Gets the student that has been selected from the card as a Property<Colour>
     *
     * @return the colour of the student that has been selected as a Property<Colour>
     */
    public Property<Colour> getStudentOnCardSelectedProperty() {
        return studentOnCardSelected;
    }
}