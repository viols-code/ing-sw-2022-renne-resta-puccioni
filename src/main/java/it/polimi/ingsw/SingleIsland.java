package it.polimi.ingsw;

import java.util.HashMap;

/**
 * SingleIsland
 *
 * @author laurapuccioni
 * @version 1.0
 */

public class SingleIsland {

    /**
     * A HashMap containing al the students that are on the SingleIsland
     */
    private HashMap<Colour, Integer> students;

    /**
     * Constructor: creates an empty SingleIsland
     */
    public SingleIsland(){
        students = null;
    }

    /**
     * Get the number of students of the given colour contained in the SingleIsland
     *
     * @param colour
     * @return the number of students of the given colour
     */
    public int getStudents(Colour colour){
        return students.get(colour);
    }

    /**
     * Adds a student of the specified colour to the SingleIsland
     *
     * @param colour
     */
    public void addStudent(Colour colour){

    }


}
