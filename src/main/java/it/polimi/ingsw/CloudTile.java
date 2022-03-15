package it.polimi.ingsw;

import java.util.HashMap;

/**
 * CloudTile
 *
 * @version 1.0
 */

public class CloudTile {

    /**
     * A HashMap containing the students on the CloudTile
     */
    private final HashMap<Colour, Integer> tileStudents;

    /**
     * Constructor: creates a new CloudTile
     */
    public CloudTile() {
        tileStudents = new HashMap<>();

        tileStudents.put(Colour.GREEN, 0);
        tileStudents.put(Colour.RED, 0);
        tileStudents.put(Colour.YELLOW, 0);
        tileStudents.put(Colour.PINK, 0);
        tileStudents.put(Colour.BLUE, 0);
    }

    /**
     * Get the students of the given colour that are on the CloudTile
     *
     * @param colour colour of the number of student we want to get
     * @return the number of students of the given colour on the CloudTile
     */
    public int getTileStudents(Colour colour) {
        return tileStudents.get(colour);
    }

}
