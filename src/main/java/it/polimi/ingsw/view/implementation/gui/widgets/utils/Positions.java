package it.polimi.ingsw.view.implementation.gui.widgets.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class representing the positions of the groupIslands on the window
 */
public class Positions {

    private static final List<Double> angles = new ArrayList<>(Arrays.asList(Math.PI, Math.PI * 5 / 6, Math.PI * 4 / 6, Math.PI * 3 / 6, Math.PI * 2 / 6, Math.PI * 1 / 6, 0.0, Math.PI * 11 / 6, Math.PI * 10 / 6, Math.PI * 9 / 6, Math.PI * 8 / 6, Math.PI * 7 / 6));

    private static final List<List<Coordinates>> singleIslandCoordinates = new ArrayList<>(List.of(new ArrayList<>(List.of(new Coordinates(0, 0))), new ArrayList<>(Arrays.asList(new Coordinates(0, 0),
            new Coordinates(74, 50))), new ArrayList<>(Arrays.asList(new Coordinates(14, 33), new Coordinates(100, 0),
            new Coordinates(86, 94))), new ArrayList<>(Arrays.asList(new Coordinates(0, 0), new Coordinates(0, 92),
            new Coordinates(94, 0), new Coordinates(94, 92))), new ArrayList<>(Arrays.asList(new Coordinates(7, 30),
            new Coordinates(92, 68), new Coordinates(160, 18), new Coordinates(7, 118), new Coordinates(92, 160))),
            new ArrayList<>(Arrays.asList(new Coordinates(6, 6), new Coordinates(85, 62), new Coordinates(155, 6),
            new Coordinates(0, 96), new Coordinates(75, 154), new Coordinates(161, 112))), new ArrayList<>(Arrays.asList(new Coordinates(22, 40),
            new Coordinates(114, 7), new Coordinates(187, 71), new Coordinates(100, 100), new Coordinates(7, 133),
            new Coordinates(81, 198), new Coordinates(173, 171))), new ArrayList<>(Arrays.asList(new Coordinates(7, 0),
            new Coordinates(100, 0), new Coordinates(7, 93), new Coordinates(100, 93), new Coordinates(200, 93),
            new Coordinates(7, 193), new Coordinates(100, 193), new Coordinates(193, 193)))));

    private static final List<Integer> groupIslandPaneDimensions = new ArrayList<>(Arrays.asList(0, 100, 150, 200, 200, 260, 260, 300, 300));

    /**
     * Gets the angle of the given index
     * @param index the index given
     * @return the angle represented by the given index
     */
    public static double getAngle(int index) {
        return angles.get(index);
    }

    /**
     * Gets the coordinates of the given singleIsland
     * @param numberOfSingleIsland the index of the single island
     * @return the Coordinates of the single island given
     */
    public static List<Coordinates> getIslandsCoordinates(int numberOfSingleIsland) {
        return singleIslandCoordinates.get(numberOfSingleIsland - 1);
    }

    /**
     * Gets the dimensions of the island pane
     * @param numberOfSingleIsland the index of the single island
     * @return the pane dimensions of the single island given
     */
    public static int getIslandPaneDimension(int numberOfSingleIsland) {
        return groupIslandPaneDimensions.get(numberOfSingleIsland);
    }


}
