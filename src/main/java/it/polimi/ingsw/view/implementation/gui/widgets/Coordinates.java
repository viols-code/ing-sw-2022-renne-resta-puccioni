package it.polimi.ingsw.view.implementation.gui.widgets;

public class Coordinates {
    private final int row;
    private final int column;

    public Coordinates(int x, int y) {
        row = x;
        column = y;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
