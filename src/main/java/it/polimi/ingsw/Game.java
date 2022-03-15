package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Game
 *
 * @version 1.0
 */
public class Game {

    /**
     * A List containing the players in the match
     */
    private final List<Player> players;

    /**
     * Identifies the player who's playing his turn
     */
    private Player currentPlayer;

    /**
     * the first player of the current round
     */
    private Player firstPlayerTurn;

    /**
     * A Map containing the number of students for each colour in the bag
     */
    private final HashMap<Colour, Integer> bag;

    /**
     * A List containing the Groups of island in the match
     */
    private final List<GroupIsland> islands;

    /**
     * Position corresponding to the index in the List named islands
     */
    private int motherNaturePosition;

    /**
     * Number of the current round
     */
    private int round;

    /**
     * A List containing the cloudTiles
     */
    private final List<CloudTile> cloudTiles;

    /**
     * A List containing the character cards drawn for the match
     */
    private final List<CharacterCard> characterCards;

    /**
     * Number of student that each Player has to move in a round
     */
    private int studentNumberMovement;

    /**
     * Number of towers that each Player has to build
     */
    private int numberOfTowersPerPlayer;

    /**
     * Number of students that each Player can have in his entrance
     */
    private int numberStudentsEntrance;

    private GamePhase gamePhase;


    /**
     * Constructor: creates a game relying on the number of players given
     */
    public Game() {
        players = new ArrayList<>();

        bag = new HashMap<>();
        bag.put(Colour.GREEN, 24);
        bag.put(Colour.RED, 24);
        bag.put(Colour.YELLOW, 24);
        bag.put(Colour.PINK, 24);
        bag.put(Colour.BLUE, 24);

        islands = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            islands.add(new GroupIsland());
        }
        islands.get(0).placeMotherNature();

        motherNaturePosition = 0;
        round = 0;
        currentPlayer = null;
        cloudTiles = new ArrayList<>();
        characterCards = new ArrayList<>();
        studentNumberMovement = 0;
        numberOfTowersPerPlayer = 0;
        numberStudentsEntrance = 0;
        gamePhase = GamePhase.SETTING;
    }

    /*
    PLAYERS
     */

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
        if (player.equals(currentPlayer)) {
            return true;
        }

        return false;
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
    BAG
     */

    /**
     * Get the number of student of the given colour
     *
     * @param colour the colour of the number of student to return
     * @return the number of student of the given colour
     */
    public int getBag(Colour colour) {
        return bag.get(colour);
    }

    /**
     * Add a student to the bag
     *
     * @param colour the colour of the student to be added
     */
    public void addStudentBag(Colour colour) {
        int currentValue = bag.get(colour);
        bag.replace(colour, bag.get(colour), bag.get(colour) + 1);
    }

    /**
     * Remove a student from the bag
     *
     * @param colour the colour of the student to be removed
     * @throws IllegalArgumentException if there are no student of the given colour
     */
    public void removeStudentBag(Colour colour) throws IllegalArgumentException {
        if (bag.get(colour) == 0) {
            throw new IllegalArgumentException("There are no student of colour " +
                    colour.name().toLowerCase() + "in the bag");
        }
        bag.replace(colour, bag.get(colour), bag.get(colour) - 1);
    }

    /*
    TO DO: BAG DRAW
     */

    /**
     * Draw a student from the bag
     *
     * @return the student drawn
     * @throws IllegalArgumentException if there are no student in the bag
     */
    public Colour bagDrawStudent() {
        if (bag.size() == 0) {
            throw new IllegalArgumentException("There are no student in the bag");
        }
        int n = (int) (Math.random() * bag.size()) + 1;
        if (n >= 1 && n <= getBag(Colour.YELLOW)) return Colour.YELLOW;
        else if (n > getBag(Colour.YELLOW) && n <= getBag(Colour.YELLOW) + getBag(Colour.BLUE)) return Colour.BLUE;
        else if (n > getBag(Colour.YELLOW) + getBag(Colour.BLUE) && n <= getBag(Colour.YELLOW) + getBag(Colour.BLUE) + getBag(Colour.PINK))
            return Colour.PINK;
        else if (n > getBag(Colour.YELLOW) + getBag(Colour.BLUE) + getBag(Colour.PINK)
                && n <= getBag(Colour.YELLOW) + getBag(Colour.BLUE) + getBag(Colour.PINK) + getBag(Colour.RED))
            return Colour.RED;
        else return Colour.GREEN;
    }


    /*
    ISLANDS
     */

    /**
     * Get the List of GroupIsland
     *
     * @return a List containing the GroupIsland
     */
    public List<GroupIsland> getIslands() {
        return islands;
    }

    /**
     * Remove a group of islands from the list
     *
     * @param groupIsland the groupIsland to be removed
     */
    public void removeGroupIsland(GroupIsland groupIsland) {
        islands.remove(groupIsland);
    }

    /**
     * Unifies the two groupIslands if possible
     *
     * @param groupIsland1 the first groupIsland to be unified
     * @param groupIsland2 the second groupIsland to be unified
     * @throws IllegalArgumentException if the two islands are not
     */
    public void unify(GroupIsland groupIsland1, GroupIsland groupIsland2) throws IllegalArgumentException {
        if (islands.indexOf(groupIsland1) == ((islands.indexOf(groupIsland2) + 1) % islands.size()) ||
                islands.indexOf(groupIsland2) == ((islands.indexOf(groupIsland1) + 1) % islands.size())) {
            groupIsland1.unifyIsland(groupIsland2);
            islands.remove(groupIsland2);
        } else {
            throw new IllegalArgumentException("The two islands are not unifiable");
        }
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
    CLOUD TILE
     */

    /**
     * Get the List of Cloud Tile
     *
     * @return a List containing the Cloud Tile remaining in the turn
     */
    public List<CloudTile> getCloudTiles() {
        return cloudTiles;
    }

    /**
     * Add a Cloud Tile, this is done at the beginning of a round
     *
     * @param cloudTile the cloud to be added to the game
     */
    public void addCLoudTile(CloudTile cloudTile) {
        cloudTiles.add(cloudTile);
    }

    /**
     * Remove a Cloud Tile because a player chose it
     *
     * @param cloudTile the cloud tile chosen by one player
     */
    public void removeCLoudTile(CloudTile cloudTile) {
        cloudTiles.remove(cloudTile);
    }

    /*
    CHARACTER CARDS
     */

    /**
     * Get the List of the Character Card
     *
     * @return a List containing the Character Card of the game
     */
    public List<CharacterCard> getCharacterCards() {
        return characterCards;
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
    int getNumberStudentsEntrance() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;
        Game game = (Game) o;
        return motherNaturePosition == game.motherNaturePosition && round == game.round && studentNumberMovement == game.studentNumberMovement && numberOfTowersPerPlayer == game.numberOfTowersPerPlayer && numberStudentsEntrance == game.numberStudentsEntrance && Objects.equals(players, game.players) && Objects.equals(currentPlayer, game.currentPlayer) && Objects.equals(firstPlayerTurn, game.firstPlayerTurn) && Objects.equals(bag, game.bag) && islands.equals(game.islands) && cloudTiles.equals(game.cloudTiles) && characterCards.equals(game.characterCards) && gamePhase == game.gamePhase;
    }

    @Override
    public int hashCode() {
        return Objects.hash(players, currentPlayer, firstPlayerTurn, bag, islands, motherNaturePosition, round, cloudTiles, characterCards, studentNumberMovement, numberOfTowersPerPlayer, numberStudentsEntrance, gamePhase);
    }

}


