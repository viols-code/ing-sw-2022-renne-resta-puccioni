package it.polimi.ingsw;

import java.util.HashMap;

/**
 * CloudTile
 *
 * @author laurapuccioni
 * @version 1.0
 */

public class CloudTile {

    /**
     * A HashMap containing the students on the CloudTile
     */
    private HashMap<Colour, Integer> tileStudents;

    /**
     * Get the students that are on the CloudTile
     *
     * @param colour
     * @return the number of students of the given colour on the CloudTile
     */
    public int getTileStudents(Colour colour) {
        return tileStudents.get(colour);
    }

}
