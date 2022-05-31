package it.polimi.ingsw.view.beans;

import it.polimi.ingsw.model.Colour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

/**
 * Class which represents a local copy of the single islands
 */
public class MockSingleIsland {
    /**
     * An HashMap containing the number of students for each colour on the single islands
     */
    private final ObservableMap<Colour, Integer> students;

    /**
     * Constructs the single island
     */
    public MockSingleIsland() {
        students = FXCollections.observableHashMap();
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

    public ObservableMap<Colour, Integer> getStudentsProperty() {
        return students;
    }
}