package it.polimi.ingsw.view.beans;

import it.polimi.ingsw.model.Colour;

/**
 * The coordinate of a student in the GridPane
 */
public class StudentCoordinates {
    /**
     * The row of the student
     */
    private final int row;
    /**
     * The column of the student
     */
    private final int column;
    private final Colour studentColour;

    public StudentCoordinates(int row, int column, Colour studentColour) {
        this.row = row;
        this.column = column;
        this.studentColour = studentColour;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Colour getStudentColour() {
        return studentColour;
    }
}
