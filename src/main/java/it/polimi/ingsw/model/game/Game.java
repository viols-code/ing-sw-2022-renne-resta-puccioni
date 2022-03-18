package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.card.BasicState;
import it.polimi.ingsw.model.card.CharacterCard;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.table.Table;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Game
 *
 * @version 1.0
 */
public abstract class Game {

    /**
     * A List containing the players in the match
     */
    protected final List<Player> players;

    /**
     * Identifies the player who's playing his turn
     */
    protected Player currentPlayer;

    /**
     * the first player of the current round
     */
    protected Player firstPlayerTurn;

    /**
     * the table of the game
     */
    protected final Table table;

    /**
     * Number of the current round
     */
    protected int round;

    /**
     * the active character card
     */
    protected CharacterCard activeCharacterCard;

    /**
     * A List containing the assistant cards
     */
    protected final List<AssistantCard> assistantCard;

    /**
     * Number of student that each Player has to move in a round
     */
    protected int studentNumberMovement;

    /**
     * Number of towers that each Player has to build
     */
    protected int numberOfTowersPerPlayer;

    /**
     * Number of students that each Player can have in his entrance
     */
    protected int numberStudentsEntrance;

    /**
     * The current phase of the game
     */
    protected GamePhase gamePhase;

    /**
     * The current phase of the currentPlayer
     */
    protected TurnPhase turnPhase;


    /**
     * Constructor: creates a game
     */
    public Game() {
        players = new ArrayList<>();
        currentPlayer = null;
        firstPlayerTurn = null;
        table = new Table();
        round = 0;
        activeCharacterCard = new BasicState();
        currentPlayer = null;
        assistantCard = new ArrayList<>();

        AssistantCard card1 = new AssistantCard(1, 1);
        assistantCard.add(card1);
        AssistantCard card2 = new AssistantCard(2, 1);
        assistantCard.add(card2);
        AssistantCard card3 = new AssistantCard(3, 2);
        assistantCard.add(card3);
        AssistantCard card4 = new AssistantCard(4, 2);
        assistantCard.add(card4);
        AssistantCard card5 = new AssistantCard(5, 3);
        assistantCard.add(card5);
        AssistantCard card6 = new AssistantCard(6, 3);
        assistantCard.add(card6);
        AssistantCard card7 = new AssistantCard(7, 4);
        assistantCard.add(card7);
        AssistantCard card8 = new AssistantCard(8, 5);
        assistantCard.add(card8);
        AssistantCard card9 = new AssistantCard(9, 5);
        assistantCard.add(card9);
        AssistantCard card10 = new AssistantCard(10, 5);
        assistantCard.add(card10);

        studentNumberMovement = 0;
        numberOfTowersPerPlayer = 0;
        numberStudentsEntrance = 0;
        gamePhase = GamePhase.SETTING;
        turnPhase = TurnPhase.WAITING;
    }

    /*
    PLAYERS
     */

    /**
     * Get the player at the given index
     *
     * @param index the index of the player to return
     * @return the player at the given index
     */
    public Player getPlayerByIndex(int index) {
        return players.get(index);
    }

    /**
     * Get the number of players
     *
     * @return the number of players
     */
    public int getNumberOfPlayer() {
        return players.size();
    }

    /**
     * Adds a Player to the match
     *
     * @param player the player to be added to the game
     */
    public void addPlayer(Player player) {
        this.players.add(player);
    }

    /**
     * Remove a player from the match
     *
     * @param player the player to be removed from the game
     * @throws IllegalArgumentException if the player is not in the game
     */
    public void removePlayer(Player player) throws IllegalArgumentException {
        if (!players.contains(player)) {
            throw new IllegalArgumentException("This player is not in the game");
        }

        this.players.remove(player);
    }

    /**
     * Get the next player clockwise
     *
     * @return the next player clockwise
     */
    public Player nextPlayerClockwise() {
        return players.get((players.indexOf(currentPlayer) + 1) % players.size());
    }


    public Player nextPlayerTurn() {
       /*
       TO DO
        */
        return currentPlayer;
    }

    /**
     * Get the current player
     *
     * @return the current player
     */
    public boolean isCurrentPlayer(Player player) {
        return player.equals(currentPlayer);
    }

    /**
     * Set the current Player
     *
     * @param player the player to be set as the current player
     */
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    /**
     * Get the player who played first in the current round
     *
     * @return the player who played first in the current round
     */
    public Player getFirstPlayerTurn() {
        return this.firstPlayerTurn;
    }

    /**
     * Set the player who played first in the current round
     *
     * @param player the player who played first in the current round
     */
    public void setFirstPlayerTurn(Player player) {
        this.firstPlayerTurn = player;
    }

    /*
    TABLE
     */

    public Table getTable() {
        return table;
    }

    /*
    ROUND
     */

    /**
     * Get the round number
     *
     * @return the round number
     */
    public int getRound() {
        return round;
    }

    /**
     * Increments the round number
     */
    public void incrementRound() {
        round += 1;
    }

    /*
    CHARACTER CARDS
     */

    /**
     * Get the Character Card at the given index
     *
     * @return the Character Card at the given index
     * @throws IllegalAccessError if the mode is basic
     */
    public CharacterCard getCharacterCardsByIndex(int index) throws IllegalAccessError {
        throw new IllegalAccessError("This is for the Expert Mode");
    }

    /**
     * Add the character card given to the List
     *
     * @param card the Character Card to be added
     * @throws IllegalAccessError if the mode is basic
     */
    public void addCharacterCard(CharacterCard card) throws IllegalAccessError{
        throw new IllegalAccessError("This is for the Expert Mode");
    }

    /**
     * Get the active Character Card
     *
     * @return the active Character Card
     */
    public CharacterCard getActiveCharacterCard() {
        return activeCharacterCard;
    }

    /**
     * Set the active Character Card
     *
     * @param card the active Character Card
     * @throws IllegalAccessError if the mode is basic
     */
    public void setActiveCharacterCard(CharacterCard card) throws IllegalAccessError {
        throw new IllegalAccessError("This is for the Expert Mode");
    }

    /*
    ASSISTANT CARD
     */

    /**
     * Get the assistant card in the assistantCard List
     *
     * @param card the position of the assistant card to be returned
     * @return the assistant card position in the List
     * @throws IllegalArgumentException if the position given doesn't exist in the List
     */
    public AssistantCard getAssistantCard(int card) throws IllegalArgumentException {
        if (card < 0 || card > 9) {
            throw new IllegalArgumentException("The argument must be between 0 and 9");
        }
        return assistantCard.get(card);
    }

    /*
    INT TO CONTROL THE GAME
     */

    /**
     * Get the number of student movement the player has to do in their turn
     *
     * @return the number of student movement the player has to do in their turn
     */
    public int getStudentNumberMovement() {
        return studentNumberMovement;
    }

    /**
     * Sets the number of students that a Player has to move in a round
     *
     * @param studentNumberMovement number of students that a Player has to move in a round
     */
    public void setStudentNumberMovement(int studentNumberMovement) {
        this.studentNumberMovement = studentNumberMovement;
    }

    /**
     * Get the initial and maximum number of tower per player
     *
     * @return the initial and maximum number of tower per player
     */
    public int getNumberOfTowersPerPlayer() {
        return numberOfTowersPerPlayer;
    }

    /**
     * Sets the number of students that a Player can move in a round
     *
     * @param numberOfTowersPerPlayer number of students that a Player can move in a round
     */
    public void setNumberOfTowersPerPlayer(int numberOfTowersPerPlayer) {
        this.numberOfTowersPerPlayer = numberOfTowersPerPlayer;
    }


    /**
     * Get the number of students in the entrance
     *
     * @return the number of students in the entrance
     */
    public int getNumberStudentsEntrance() {
        return numberStudentsEntrance;
    }

    /**
     * Sets the number of students that a Player has in the entrance of his school board
     *
     * @param numberStudentsEntrance number of students that a Player has in the entrance of his school board
     */
    public void setNumberStudentsEntrance(int numberStudentsEntrance) {
        this.numberStudentsEntrance = numberStudentsEntrance;
    }

    /*
    GAME PHASE
     */

    /**
     * Get the current game phase
     *
     * @return the current game phase
     */
    public GamePhase getGamePhase() {
        return gamePhase;
    }

    /**
     * Set the current game phase
     *
     * @param gamePhase the game phase to be set
     */
    public void setGamePhase(GamePhase gamePhase) {
        this.gamePhase = gamePhase;
    }

    /*
    TURN PHASE
     */

    /**
     * Get the current turn phase
     *
     * @return the current turn phase
     */
    public TurnPhase getTurnPhase() {
        return turnPhase;
    }

    /**
     * Set the current turn phase
     *
     * @param turnPhase the turn phase to be set
     */
    public void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
    }

    public void setHasProtectIsland() throws IllegalAccessError{
        throw new IllegalAccessError("This is for the Expert Mode");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;
        Game game = (Game) o;
        return round == game.round && studentNumberMovement == game.studentNumberMovement && numberOfTowersPerPlayer == game.numberOfTowersPerPlayer && numberStudentsEntrance == game.numberStudentsEntrance && Objects.equals(players, game.players) && Objects.equals(currentPlayer, game.currentPlayer) && Objects.equals(firstPlayerTurn, game.firstPlayerTurn) && gamePhase == game.gamePhase && turnPhase == game.turnPhase;
    }

    @Override
    public int hashCode() {
        return Objects.hash(players, currentPlayer, firstPlayerTurn, round, studentNumberMovement, numberOfTowersPerPlayer, numberStudentsEntrance, gamePhase, turnPhase);
    }

}


