package it.polimi.ingsw.model.player;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration of the colour of the tower in the game
 *
 * @version 1.0
 */
public enum TowerColour {
    WHITE(0),
    BLACK(1),
    GREY(2);

    /**
     * Value given to the tower colour
     */
    private final int value;
    /**
     * A map connecting the value and the tower colour
     */
    private static final Map<Integer, TowerColour> map = new HashMap<>();


    TowerColour(int value) {
        this.value = value;
    }

    static {
        for (TowerColour towerColour : TowerColour.values()) {
            map.put(towerColour.value, towerColour);
        }
    }

    /**
     * Return the TowerColour corresponding to the integer given
     *
     * @param towerColour an integer connected to the TowerColour
     * @return the TowerColour corresponding to the integer given
     */
    public static TowerColour valueOf(int towerColour) {
        return map.get(towerColour);
    }

}
