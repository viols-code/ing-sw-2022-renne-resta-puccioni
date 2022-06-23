package it.polimi.ingsw.view.implementation.gui.widgets.utils;

/**
 * Class which represents the coordinates of a student on the GUI
 */
public class Coordinates {
    private final int row;
    private final int column;

    /**
     * Contructor of the coordinates
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Coordinates(int x, int y) {
        row = x;
        column = y;
    }

    /**
     * Gets the x coordinate
     * @return the x coordinate
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the y coordinate
     * @return the y coordinate
     */
    public int getColumn() {
        return column;
    }

}
