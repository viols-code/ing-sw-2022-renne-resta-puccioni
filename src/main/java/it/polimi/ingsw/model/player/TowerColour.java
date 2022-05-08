package it.polimi.ingsw.model.player;

import java.util.HashMap;
import java.util.Map;

public enum TowerColour {
    WHITE(0),
    BLACK(1),
    GREY(2);

    private final int value;
    private static final Map<Integer, TowerColour> map = new HashMap<>();

    TowerColour(int value) {
        this.value = value;
    }

    static {
        for (TowerColour towerColour : TowerColour.values()) {
            map.put(towerColour.value, towerColour);
        }
    }

    public static TowerColour valueOf(int towerColour) {
        return map.get(towerColour);
    }

}
