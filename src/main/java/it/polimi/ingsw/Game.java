package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
    * Game
    *
    * @author sararesta
    * @version 1.0

    */
public class Game {
    /**
     * A List containing the players in the match
     */
    private List<Player> players;

    /**
     * A Map containing the number of students for each colour in the bag
     */
    private HashMap<Colour,Integer> bag;

    /**
     * A List containing the Groups of island in the match
     */
    private List<GroupIsland> islands;

    /**
     * Number of the current round
     */
    private int round;

    /**
     * Identifies the player who's playing his turn
     */
    private Player currentPlayer;

    /**
     * A List containing the cloudTiles
     */
    private List<CloudTile> cloudTiles;

    /**
     * A List containing the character cards drawn for the match
     */
    private List<CharacterCard> characterCards;

    /**
     * Number of student that each Player has to move in a round
     */
    private int studentNumberMovement;

    /**
     * Number of towers that each Player has to build
     */
    private int numberOfTowersPerPlayer;

    /**
     * Constructor: creates a game relying on the number of players given
     * @param numberOfPlayers
     * @return the game
     */
    public Game(int numberOfPlayers){

        players=null;

        bag.put(Colour.GREEN, 26);
        bag.put(Colour.RED, 26);
        bag.put(Colour.YELLOW, 26);
        bag.put(Colour.PINK, 26);
        bag.put(Colour.BLUE, 26);

        islands=new ArrayList<GroupIsland>();
        for(int i=0;i<12;i++){
            islands.add(new GroupIsland());
        }

        round=0;

        currentPlayer=null;

        cloudTiles=null;

        characterCards=null;

        if(numberOfPlayers==2){
            studentNumberMovement=3;
            numberOfTowersPerPlayer=8;
        }
        if(numberOfPlayers==3){
            studentNumberMovement=4;
            numberOfTowersPerPlayer=6;
        }

        characterCards=null;


    }




    /**
     * Adds a Player to the match
     * @param player
     */
    public void addPlayer(Player player){
        this.players.add(player);
    }

    /**
     * Remove a player from the match
     *
     * @param player
     */
    public void removePlayer(Player player){
        this.players.remove(player);
    }

    /**
     * Add a student to the bag
     *
     * @param colour
     */
    public void addStudentBag(Colour colour){
        int currentValue=bag.get(colour);
        bag.put(colour,currentValue+1);
    }

    /**
     * Remove a student from the bag
     *
     * @param colour
     */
    public void removeStudentBag(Colour colour){
        int currentValue=bag.get(colour);
        bag.put(colour,currentValue-1);
    }

    /**
     * Remove a group of islands from the list
     *
     * @param groupIsland
     */
    public void removeGroupIsland(GroupIsland groupIsland){
        islands.remove(groupIsland);
    }

    /**
     * Increments the round number
     */
    public void incrementRound(){
        round+=1;
    }

    /**
     * Changes the current Player
     *
     * @param player
     */
    public void changeCurrentPlayer(Player player){
        currentPlayer=player;
    }

    /**
     * Add a Cloud Tile, this is done at the beginning of a round
     *
     * @param cloudTile
     */
    public void addCLoudTile(CloudTile cloudTile){
        cloudTiles.add(cloudTile);
    }

    /**
     * Remove a Cloud Tile because a player chose it
     *
     * @param cloudTile
     */
    public void removeCLoudTile(CloudTile cloudTile){
        cloudTiles.remove(cloudTile);
    }

    /**
     *  getter
     */
    public List<Player> getPlayers() {
        return players;
    }


    public HashMap<Colour, Integer> getBag() {
        return bag;
    }

    public List<GroupIsland> getIslands() {
        return islands;
    }

    public int getRound() {
        return round;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public List<CloudTile> getCloudTiles() {
        return cloudTiles;
    }

    public List<CharacterCard> getCharacterCards() {
        return characterCards;
    }

    public int getStudentNumberMovement() {
        return studentNumberMovement;
    }
}
