package it.polimi.ingsw;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Player
 *
 * @version 1.0
 */
public class Player {

    /**
     * the nickname of the player
     */
    private final String nickname;

    /**
     * a Map containing the students in the diningRoom
     */
    private final HashMap<Colour, Integer> diningRoom;

    /**
     * a Map containing the students in the entrance
     */
    private final HashMap<Colour, Integer> entrance;

    /**
     * a Map containing the professors in the professorTable
     */
    private final HashMap<Colour, Boolean> professorTable;

    /**
     * number of towers in the SchoolBoard
     */
    private int towers;

    /**
     * a Set containing the AssistantCard of the player
     */
    private final Set<AssistantCard> assistantCardSet;

    /**
     * card played by the player in the current round
     */
    private AssistantCard currentAssistantCard;

    /**
     * number of coins
     */
    private int coins;

    /**
     * the game in which the player is playing
     */
    private final Game game;

    /**
     * Constructor: creates a new Player with the given nick and the given number of towers
     *
     * @param nickname a string with the nickname
     * @param game the game in which the player is playing
     */
    public Player(String nickname, Game game) {
        this.nickname = nickname;
        diningRoom = new HashMap<>();
        diningRoom.put(Colour.GREEN, 0);
        diningRoom.put(Colour.RED, 0);
        diningRoom.put(Colour.YELLOW, 0);
        diningRoom.put(Colour.PINK, 0);
        diningRoom.put(Colour.BLUE, 0);

        entrance = new HashMap<>();
        entrance.put(Colour.GREEN, 0);
        entrance.put(Colour.RED, 0);
        entrance.put(Colour.YELLOW, 0);
        entrance.put(Colour.PINK, 0);
        entrance.put(Colour.BLUE, 0);

        professorTable = new HashMap<>();
        professorTable.put(Colour.GREEN, false);
        professorTable.put(Colour.RED, false);
        professorTable.put(Colour.YELLOW, false);
        professorTable.put(Colour.PINK, false);
        professorTable.put(Colour.BLUE, false);

        towers = 0;
        assistantCardSet = new HashSet<>();
        currentAssistantCard = null;
        coins = 1;
        this.game = game;
    }

    /*
     NICKNAME
     */

    /**
     * Get the nickname of the player.
     *
     * @return a string with the nickname
     */
    public synchronized String getNickname() {
        return nickname;
    }


    /*
     DINING ROOM
     */

    /**
     * Get the number of students in the diningRoom of the given colour
     *
     * @param colour the colour of the returned value
     * @return the number of students of the given colour in the diningRoom
     */
    public int getDiningRoom(Colour colour) { return diningRoom.get(colour); }

    /**
     * Add a student of the given colour to the diningRoom
     *
     * @param colour the colour of the student to add to the diningRoom
     * @throws IllegalArgumentException when the diningRoom is already full
     */
    public void addStudentToDiningRoom(Colour colour) throws IllegalArgumentException{
        if(diningRoom.get(colour) + 1 > 10){
            throw new IllegalArgumentException("Too many students of colour " + colour.name().toLowerCase() +
                    " in the dining room");
        }

        diningRoom.replace(colour, diningRoom.get(colour),diningRoom.get(colour) + 1);
    }

    /**
     * Remove a student of the given colour from the diningRoom
     *
     * @param colour the colour of the student to remove from the diningRoom
     * @throws IllegalArgumentException when in the diningRoom there is no student of the given colour
     */
    public void removeStudentFromDiningRoom(Colour colour) throws IllegalArgumentException{
        if(diningRoom.get(colour) - 1 < 0){
            throw new IllegalArgumentException("There are not enough student of colour " +
                    colour.name().toLowerCase() + "in the dining room");
        }

        diningRoom.replace(colour, diningRoom.get(colour),diningRoom.get(colour) - 1);
    }

    /*
    ENTRANCE
     */

    /**
     * Get the number of students in the entrance of the given colour
     *
     * @param colour the colour of the returned value
     * @return the number of students in the entrance of the given colour
     */
    public int getEntrance(Colour colour) { return entrance.get(colour); }

    /**
     * Add a student of the given colour to the entrance
     *
     * @param colour the colour of the student to add to the entrance
     * @throws IllegalArgumentException when in the entrance there are too many students
     */
    public void addStudentToEntrance(Colour colour) throws IllegalArgumentException{
        if(entrance.size() == game.getNumberStudentsEntrance()){
            throw new IllegalArgumentException("Too many students in the entrance");
        }

        entrance.replace(colour, entrance.get(colour),entrance.get(colour) + 1);
    }

    /**
     * Add all the student in the CloudTile to the player's entrance
     *
     * @param tile the CloudTile chosen by the player
     */
    public void addStudentToEntrance(CloudTile tile){

        for (Colour colour: Colour.values()) {
            entrance.replace(colour, entrance.get(colour),entrance.get(colour) + tile.getTileStudents(colour));
        }
    }

    /**
     * Remove a student of the given colour from the entrance
     *
     * @param colour the colour of the student to remove from the entrance
     * @throws IllegalArgumentException when in the entrance there is no student of the given colour
     */
    public void removeStudentFromEntrance(Colour colour){
        if(entrance.get(colour) - 1 < 0){
            throw new IllegalArgumentException("There are no student of colour " + colour.name().toLowerCase() +
                    " in the entrance" );
        }

        entrance.replace(colour, entrance.get(colour),entrance.get(colour) - 1);
    }

    /*
    PROFESSOR
     */

    /**
     * Add the professor of the given colour to the professor table
     *
     * @param colour the colour of the professor to add
     */
    public void addProfessor(Colour colour){
        if(!hasProfessor(colour)){
            professorTable.replace(colour, false, true);
        }
    }

    /**
     * Remove the professor of the given colour from the professor table
     *
     * @param colour the colour of the professor to remove
     */
    public void removeProfessor(Colour colour){
        if(hasProfessor(colour)){
            professorTable.replace(colour, true, false);
        }
    }

    /**
     * Checks if the player has the professor corresponding to the given colour
     *
     * @param colour Colour of the professor to check
     * @return true if the player has the professor of the given colour, false otherwise
     */
    public boolean hasProfessor(Colour colour){
        return professorTable.get(colour);
    }

    /*
    TOWERS
     */

    /**
     * Get the number of towers in the SchoolBoard
     *
     * @return the number of towers in the SchoolBoard
     */
    public int getTowers() {
        return towers;
    }

    /**
     * Increment the number of tower by the number given
     *
     * @param num the number of towers to give to the player
     * @throws IllegalArgumentException when the number of towers exceed the maximum amount
     */
    public void addTower(int num) throws IllegalArgumentException{
        if(towers + num > game.getNumberOfTowersPerPlayer()){
            throw new IllegalArgumentException("The number of towers of each player should be less");
        }

        towers += num;
    }

    /**
     * Decrement the number of tower by the number given
     *
     * @throws IllegalArgumentException when there are not enough towers
     */
    public void removeTower(int num) throws IllegalArgumentException{
        if(towers - num < 0){
            throw new IllegalArgumentException("There are not enough towers");
        }

        towers -= num;
    }


    /*
    ASSISTANT CARD LIST
     */

    public void addAssistantCardList(AssistantCard card){
        assistantCardSet.add(card);
    }


    /**
     * Remove the given card from the assistantCardList and set the card as the currentAssistantCard
     *
     * @param card the assistant card used by the player in that round
     * @throws IllegalArgumentException when the assistant card given is already been played
     */
    public void playAssistantCard(AssistantCard card) throws IllegalArgumentException{
        if(!assistantCardSet.contains(card)){
            throw new IllegalArgumentException("The card has already been played");
        }
        setCurrentAssistantCard(card);
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
    }

    /*
    COINS
     */

    /**
     * Get the number of coins
     *
     * @return the number of coins
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Add the given number of coins
     *
     * @param num the number of coins to add to the player
     */
    public void addCoins(int num){
        coins += num;
    }

    /**
     * Remove the given number of coins
     *
     * @param num the number of coins to remove to the player
     * @throws IllegalArgumentException when there are not enough coins to do the operation
     */
    public void removeCoins(int num) throws IllegalArgumentException{
        if(coins - num <  0){
            throw new IllegalArgumentException("There are not enough coins");
        }

        coins -= num;
    }

}