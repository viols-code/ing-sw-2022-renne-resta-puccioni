package it.polimi.ingsw.view.beans;

import it.polimi.ingsw.model.Colour;

import java.util.HashMap;

/**
 * Class that contains a local copy of the player's school board
 */
public class MockSchoolBoard {
    private HashMap<Colour, Integer> entrance;
    private HashMap<Colour, Integer> diningRoom;
    private HashMap<Colour, Boolean> professorTable;
    private int towers;

    public MockSchoolBoard() {
        entrance = new HashMap<>();
        diningRoom = new HashMap<>();
        professorTable = new HashMap<>();

        for (Colour colour : Colour.values()) {
            entrance.put(colour, 0);
            diningRoom.put(colour, 0);
            professorTable.put(colour, false);
        }
    }

    /**
     * Gets the entrance
     *
     * @return the entrance
     */
    public HashMap<Colour, Integer> getEntrance() {
        return entrance;
    }

    /**
     * Updates the entrance
     *
     * @param entrance the entrance updated
     */
    public void setEntrance(HashMap<Colour, Integer> entrance) {
        this.entrance = entrance;
    }

    /**
     * Gets the dining room
     *
     * @return the dining room
     */
    public HashMap<Colour, Integer> getDiningRoom() {
        return diningRoom;
    }

    /**
     * Updates the dining room
     *
     * @param diningRoom the dining room updated
     */
    public void setDiningRoom(HashMap<Colour, Integer> diningRoom) {
        this.diningRoom = diningRoom;
    }

    /**
     * Gets the professor table
     *
     * @return the professor table
     */
    public HashMap<Colour, Boolean> getProfessorTable() {
        return professorTable;
    }

    /**
     * Updates the professor table
     *
     * @param professorTable the professor table updated
     */
    public void setProfessorTable(HashMap<Colour, Boolean> professorTable) {
        this.professorTable = professorTable;
    }

    /**
     * Gets the number of towers on that school board
     *
     * @return the number of towers on that school board
     */
    public int getTowers() {
        return towers;
    }

    /**
     * Sets the number of towers on that school board
     *
     * @param towers the number of towers
     */
    public void setTowers(int towers) {
        this.towers = towers;
    }
}