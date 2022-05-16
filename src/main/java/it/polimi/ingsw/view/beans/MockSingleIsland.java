package it.polimi.ingsw.view.beans;

import it.polimi.ingsw.model.Colour;

import java.util.HashMap;

/**
 * Class which represents a local copy of the single islands
 */
public class MockSingleIsland {
    /**
     * An HashMap containing the number of students for each colour on the single islands
     */
    private final HashMap<Colour, Integer> students;

    /**
     * Constructs the single island
     */
    public MockSingleIsland() {
        students = new HashMap<>();
        for (Colour colour : Colour.values()) {
            students.put(colour, 0);
        }
    }

    /**
     * Adds a students to the single island
     *
     * @param colour the colour of the student to add
     */
    public void setStudent(Colour colour) {
        students.replace(colour, students.get(colour), students.get(colour) + 1);
    }

    /**
     * Gets the number of the students of the selected colour
     *
     * @param colour the colour
     * @return the number of students of the colour selected
     */
    public int getStudents(Colour colour) {
        return students.get(colour);
    }
}
