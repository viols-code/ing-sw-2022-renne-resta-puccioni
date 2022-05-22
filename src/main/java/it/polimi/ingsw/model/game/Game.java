package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.card.BasicState;
import it.polimi.ingsw.model.card.CharacterCard;
import it.polimi.ingsw.model.messages.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Table;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.IServerPacket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Game
 *
 * @version 1.0
 */
public abstract class Game extends Observable<IServerPacket> {

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
     * the first player of the last round
     */
    protected Player firstPlayerLastTurn;
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
     * the basic state
     */
    protected CharacterCard basicState;
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
     * The winner of the game
     */
    protected Player winner;


    /**
     * Constructor: creates a game
     */
    public Game() {
        players = new ArrayList<>();
        currentPlayer = null;
        firstPlayerTurn = null;
        firstPlayerLastTurn = null;
        table = new Table();
        round = 1;
        activeCharacterCard = new BasicState(this);
        basicState = activeCharacterCard;
        assistantCard = new ArrayList<>();

        studentNumberMovement = 0;
        numberOfTowersPerPlayer = 0;
        numberStudentsEntrance = 0;
        winner = null;
    }

    /**
     * Adds the assistant cards and the game phase and turn phase
     */
    public void setUp() {
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
        AssistantCard card8 = new AssistantCard(8, 4);
        assistantCard.add(card8);
        AssistantCard card9 = new AssistantCard(9, 5);
        assistantCard.add(card9);
        AssistantCard card10 = new AssistantCard(10, 5);
        assistantCard.add(card10);

        setGamePhase(GamePhase.SETTING);
        setTurnPhase(TurnPhase.WAITING);
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
     * Gets the index of the given player in the list of players
     *
     * @param player of whom the index is wanted
     * @return the index of the given player
     */
    public int getIndexOfPlayer(Player player) throws IllegalArgumentException {

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).equals(player)) {
                return i;
            }
        }
        throw new IllegalArgumentException("This player is not in the game");
    }

    /**
     * Get the player with the given nickname
     *
     * @param nickname the nickname of the player to return
     * @return the player with the given nickname
     */
    public Player getPlayerByNickname(String nickname) throws IllegalArgumentException {
        for (int i = 0; i < getNumberOfPlayer(); i++) {
            if (nickname.equals(getPlayerByIndex(i).getNickname())) {
                return getPlayerByIndex(i);
            }
        }

        throw new IllegalArgumentException("The nickname is not present");
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
        //notify(new PlayersUpdate(player.getNickname(),player.getWizard()));
        notify(new TowerColourUpdate(player.getNickname(),player.getTowerColour()));
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
        //to-do : notify the client that a player has been removed
    }

    /**
     * Get the next player clockwise
     *
     * @return the next player clockwise
     */
    public Player nextPlayerClockwise() {
        return players.get((players.indexOf(currentPlayer) + 1) % players.size());
    }


    /**
     * Says which player is the next one according to the number on the assistantCard played
     *
     * @return the nextPlayer according to the assistantCard number
     */
    public Player nextPlayerTurn() {
        HashMap<Player, Integer> values = new HashMap<>();
        List<Player> res;

        for (int i = 0; i < getNumberOfPlayer(); i++) {
            if (!getPlayerByIndex(i).getHasAlreadyPlayed()) {
                values.put(getPlayerByIndex(i), getPlayerByIndex(i).getCurrentAssistantCard().getValue());
            }
        }

        Integer min = values.values().stream().reduce(11, (y1, y2) -> {
            if (y1 < y2) return y1;
            else return y2;
        });

        res = values.entrySet()
                .stream()
                .filter(x -> x.getValue().equals(min))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());


        if (res.size() == 1) {
            return res.get(0);
        } else {
            int j = 0;
            for (int i = 0; i < getNumberOfPlayer(); i++) {
                if (firstPlayerLastTurn.equals(getPlayerByIndex(i))) {
                    j = i;
                }
            }
            int i = 0;
            while (i < getNumberOfPlayer()) {
                if (res.contains(getPlayerByIndex(j))) {
                    return getPlayerByIndex(j);
                }
                j = (j + 1) % getNumberOfPlayer();
                i++;
            }
        }

        return getPlayerByIndex(0);
    }

    /**
     * Get the current player
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Set the current Player
     *
     * @param player the player to be set as the current player
     */
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
        notify(new CurrentPlayerUpdate(player.getNickname()));
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

    /**
     * Set the player who played first in the last round
     *
     * @param player the player who played first in the last round
     */
    public void setFirstPlayerLastTurn(Player player) {
        this.firstPlayerLastTurn = player;
    }

    /*
    TABLE
     */

    /**
     * Return the table of the game
     *
     * @return the Table of the Game
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
        notify(new RoundUpdate(this.round));
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
    public CharacterCard getCharacterCardByIndex(int index) throws IllegalAccessError {
        throw new IllegalAccessError("This is for the Expert Mode");
    }

    /**
     * Get the index of the Character Card given
     *
     * @return the index of the characterCard given
     * @throws IllegalAccessError if the mode is basic
     */
    public int getCharacterCardIndex(CharacterCard card) throws IllegalAccessError {
        throw new IllegalAccessError("This is for the Expert Mode");
    }

    /**
     * Get the number of CharacterCard in the list
     *
     * @return the number of CharacterCard in the list
     * @throws IllegalAccessError if the mode is basic
     */
    public int getNumberOfCharacterCard() throws IllegalAccessError {
        throw new IllegalAccessError("This is for the Expert Mode");
    }

    /**
     * Add the character card given to the List
     *
     * @param card the Character Card to be added
     * @throws IllegalAccessError if the mode is basic
     */
    public void addCharacterCard(CharacterCard card) throws IllegalAccessError {
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

    /**
     * Return basicState
     *
     * @return basicState
     */
    public CharacterCard getBasicState() {
        return basicState;
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
        notify(new GamePhaseUpdate(this.gamePhase));
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
        notify(new TurnPhaseUpdate(this.turnPhase));
    }

    /*
    WINNER
    */

    /**
     * Return the winner of the game
     *
     * @return the winner of the game
     */
    public Player getWinner() {
        return winner;
    }

    /**
     * Se the winner of the game
     *
     * @param winner the winner of the game
     */
    public void setWinner(Player winner) {
        this.winner = winner;
        notify(new WinnerUpdate(winner.getNickname()));
    }


    /*
    HAS PROTECTED ISLAND
     */

    /**
     * Return true if the game has the ProtectIsland card
     *
     * @return true if the game has the ProtectIsland card, false otherwise
     * @throws IllegalAccessError if the game is basic
     */
    public boolean hasProtectIslandCard() throws IllegalAccessError {
        throw new IllegalAccessError("This is for the Expert Mode");
    }

    /*
    HAS PLAYED CHARACTER CARD
     */

    /**
     * Get hasPlayedCharacterCard
     *
     * @return true if the player has already played a characterCard in this turn, false otherwise
     * @throws IllegalAccessError if the game is basic
     */
    public boolean getHasPlayedCharacterCard() throws IllegalAccessError {
        throw new IllegalAccessError("This is for the Expert Mode");
    }

    /**
     * Set hasPlayedCharacterCard
     *
     * @param hasPlayedCharacterCard true if the player has already played a characterCard in this turn, false otherwise
     * @throws IllegalAccessError if the game is basic
     */
    public void setHasPlayedCharacterCard(boolean hasPlayedCharacterCard) throws IllegalAccessError {
        throw new IllegalAccessError("This is for the Expert Mode");
    }

    /*
    COINS
     */

    /**
     * Gets the number of coins available on the table
     *
     * @return the number of coins available
     * @throws IllegalAccessError if the game is not expert
     */
    public int getCoins() throws IllegalAccessError {
        throw new IllegalAccessError("This is for the Expert Mode");
    }

    /**
     * Sets the number of coins available on the table
     *
     * @param coins the coins
     * @throws IllegalAccessError if the game is not expert
     */
    public void setCoins(int coins) throws IllegalAccessError {
        throw new IllegalAccessError("This is for the Expert Mode");
    }


    /**
     * Notifies the Player that one of the action that he has tried to perform is invalid.
     *
     * @param player  the player that should be notified
     * @param message the error message
     */
    public void notifyInvalidAction(String player, String message) {
        notify(new InvalidActionUpdate(player, message));
    }
}