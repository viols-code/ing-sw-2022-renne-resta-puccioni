package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Wizard;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Enumeration of the colour in the game
 *
 * @version 1.0
 */
public enum Colour {
    GREEN(0),
    RED(1),
    YELLOW(2),
    PINK(3),
    BLUE(4);

    /**
     * Value given to the wizard
     */
    private final int value;
    /**
     * A map connecting the value and the wizard
     */
    private static final Map<Integer, Colour> map = new HashMap<>();

    Colour(int value) {
        this.value = value;
    }

    static {
        for (Colour colour : Colour.values()) {
            map.put(colour.value, colour);
        }
    }

    public static Colour valueOf(int colour) {
        return map.get(colour);
    }

    public static int getColourCode(Colour colour) {
        return map.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(colour))
                .map(entry -> entry.getKey())
                .collect(Collectors.toList())
                .get(0);
    }
}


