package it.polimi.ingsw.model.table.island;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.messages.SingleIslandUpdate;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.IServerPacket;

import java.util.HashMap;

/**
 * SingleIsland
 *
 * @version 1.0
 */

public class SingleIsland extends Observable<IServerPacket> {

    /**
     * A HashMap containing al the students that are on the SingleIsland
     */
    private final HashMap<Colour, Integer> students;

    /**
     * Constructor: creates an empty SingleIsland
     */
    public SingleIsland() {

        students = new HashMap<>();

        students.put(Colour.GREEN, 0);
        students.put(Colour.RED, 0);
        students.put(Colour.YELLOW, 0);
        students.put(Colour.PINK, 0);
        students.put(Colour.BLUE, 0);
    }

    /**
     * Get the number of students of the given colour contained in the SingleIsland
     *
     * @param colour the colour of students
     * @return the number of students of the given colour
     */
    public int getStudents(Colour colour) {
        return students.get(colour);
    }

    /**
     * Adds a student of the specified colour to the SingleIsland
     *
     * @param colour the colour of the student to be added
     */
    public void addStudent(Colour colour) {
        students.replace(colour, students.get(colour), students.get(colour) + 1);
        notify(new SingleIslandUpdate(colour));
    }


}
