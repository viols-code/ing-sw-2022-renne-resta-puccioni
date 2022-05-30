package it.polimi.ingsw.view.beans;

import it.polimi.ingsw.model.Colour;

public class StudentCoordinates {
    private final int row;
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
