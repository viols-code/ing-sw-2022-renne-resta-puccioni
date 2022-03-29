package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Colour;

import java.util.HashMap;

public class SchoolBoard {
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

    public SchoolBoard() {
        diningRoom = new HashMap<>();
        for(Colour colour: Colour.values()){
            diningRoom.put(colour, 0);
        }

        entrance = new HashMap<>();
        for(Colour colour: Colour.values()){
            entrance.put(colour, 0);
        }

        professorTable = new HashMap<>();
        for(Colour colour: Colour.values()){
            professorTable.put(colour, false);
        }

        towers = 0;
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
    public int getDiningRoom(Colour colour) {
        return diningRoom.get(colour);
    }

    /**
     * Add a student of the given colour to the diningRoom
     *
     * @param colour the colour of the student to add to the diningRoom
     * @throws IllegalArgumentException if there are already 10 students of that colour
     */
    public void addStudentToDiningRoom(Colour colour) throws IllegalArgumentException {
        if (getDiningRoom(colour) == 10) throw new IllegalArgumentException("This table of the dining room is full");
        diningRoom.replace(colour, diningRoom.get(colour), diningRoom.get(colour) + 1);
    }

    /**
     * Remove a student of the given colour from the diningRoom
     *
     * @param colour the colour of the student to remove from the diningRoom
     * @throws IllegalArgumentException if there are no students of the colour selected
     */
    public void removeStudentFromDiningRoom(Colour colour) throws IllegalArgumentException {
        if (getDiningRoom(colour) == 0)
            throw new IllegalArgumentException("There are no students of that colour in the dining room");
        diningRoom.replace(colour, diningRoom.get(colour), diningRoom.get(colour) - 1);
    }

    /**
     * Remove all student from diningRoom
     */
    public void removeAllStudentFromDiningRoom(Colour colour) {
        diningRoom.replace(colour, diningRoom.get(colour), 0);
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
    public int getEntrance(Colour colour) {
        return entrance.get(colour);
    }

    /**
     * Add a student of the given colour to the entrance
     *
     * @param colour the colour of the student to add to the entrance
     * @throws IllegalArgumentException if the entrance is full
     */
    public void addStudentToEntrance(Colour colour) throws IllegalArgumentException {
        entrance.replace(colour, entrance.get(colour), entrance.get(colour) + 1);
    }

    /**
     * Remove a student of the given colour from the entrance
     *
     * @param colour the colour of the student to remove from the entrance
     */
    public void removeStudentFromEntrance(Colour colour) {
        entrance.replace(colour, entrance.get(colour), entrance.get(colour) - 1);
    }

     /*
    PROFESSOR
     */

    /**
     * Add the professor of the given colour to the professor table
     *
     * @param colour the colour of the professor to add
     */
    public void addProfessor(Colour colour) {
        if (!hasProfessor(colour)) {
            professorTable.replace(colour, false, true);
        }
    }

    /**
     * Remove the professor of the given colour from the professor table
     *
     * @param colour the colour of the professor to remove
     */
    public void removeProfessor(Colour colour) {
        if (hasProfessor(colour)) {
            professorTable.replace(colour, true, false);
        }
    }

    /**
     * Checks if the player has the professor corresponding to the given colour
     *
     * @param colour Colour of the professor to check
     * @return true if the player has the professor of the given colour, false otherwise
     */
    public boolean hasProfessor(Colour colour) {
        return professorTable.get(colour);
    }

    /**
     * Return the total number of student if the entrance
     *
     * @return the total number of student if the entrance
     */
    public int getNumberStudentsEntrance() {
        int count = 0;

        for (Colour colour : Colour.values()) {
            count += getEntrance(colour);
        }

        return count;
    }

    /**
     * Return the number of professor
     *
     * @return the number of professor
     */
    public int getNumberOfProfessors() {
        int count = 0;

        for (Colour colour : Colour.values()) {
            if (hasProfessor(colour)) {
                count++;
            }
        }

        return count;
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
     */
    public void addTower(int num) {
        towers += num;
    }

    /**
     * Decrement the number of tower by the number given
     */
    public void removeTower(int num) {
        towers -= num;
    }
}
