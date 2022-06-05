package it.polimi.ingsw.view.implementation.gui.widgets.utils;

public class Coordinates {
    private int row;
    private int column;

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

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
