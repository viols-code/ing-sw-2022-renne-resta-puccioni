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
     * Number of strdents that each Player can has in his entrance
     */
    private int numberStudentsEntrance;



    /**
     * Constructor: creates a game relying on the number of players given
     *
     * @return the game
     */
    public Game(){

        players=null;

        bag.put(Colour.GREEN, 24);
        bag.put(Colour.RED, 24);
        bag.put(Colour.YELLOW, 24);
        bag.put(Colour.PINK, 24);
        bag.put(Colour.BLUE, 24);

        islands=new ArrayList<GroupIsland>();
        for(int i=0;i<12;i++){
            islands.add(new GroupIsland());
        }

        round=0;

        currentPlayer=null;

        cloudTiles=null;

        characterCards=null;

        studentNumberMovement=0;

        numberOfTowersPerPlayer=0;

        numberStudentsEntrance=0;

        characterCards=null;


    }


    /**
     * Adds a Player to the match
     *
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
     * Sets the number of students that a Player has in the entrance of his school board
     *
     * @param numberStudentsEntrance
     */
    public void setNumberStudentsEntrance(int numberStudentsEntrance) {
        this.numberStudentsEntrance = numberStudentsEntrance;
    }

    /**
     * Sets the number of students that a Player can move in a round
     *
     * @param studentNumberMovement
     */
    public void setStudentNumberMovement(int studentNumberMovement) {
        this.studentNumberMovement = studentNumberMovement;
    }

    /**
     * Sets the number of students that a Player can move in a round
     *
     * @param numberOfTowersPerPlayer
     */
    public void setNumberOfTowersPerPlayer(int numberOfTowersPerPlayer) {
        this.numberOfTowersPerPlayer = numberOfTowersPerPlayer;
    }

    /**
     * States if two groups of islands are unifiable or not
     *
     * @param groupIsland1
     * @param groupIsland2
     * @return true if the two groups of islands are unifiable
     */
    public boolean areIslandsUnifiable(GroupIsland groupIsland1,GroupIsland groupIsland2){
        if(islands.indexOf(groupIsland1)==islands.indexOf(groupIsland2)+1||islands.indexOf(groupIsland2)==islands.indexOf(groupIsland1)+1||(islands.indexOf(groupIsland1)==0&&islands.indexOf(groupIsland2)==islands.size()-1)||(islands.indexOf(groupIsland2)==0&&islands.indexOf(groupIsland1)==islands.size()-1))
            return true;
        else return false;
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

    public int getNumberOfTowersPerPlayer() {
        return numberOfTowersPerPlayer;
    }

     int getNumberStudentsEntrance() {
        return numberStudentsEntrance;
    }
}
