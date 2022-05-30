package it.polimi.ingsw.model.player;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Enumeration of the wizard in the game
 *
 * @version 1.0
 */
public enum Wizard {
    TYPE_1(1),
    TYPE_2(2),
    TYPE_3(3),
    TYPE_4(4);

    /**
     * Value given to the wizard
     */
    private final int value;
    /**
     * A map connecting the value and the wizard
     */
    private static final Map<Integer, Wizard> map = new HashMap<>();

    Wizard(int value) {
        this.value = value;
    }

    static {
        for (Wizard wizard : Wizard.values()) {
            map.put(wizard.value, wizard);
        }
    }

    /**
     * Return the Wizard corresponding to the integer given
     *
     * @param wizard an integer connected to the Wizard
     * @return the Wizard corresponding to the integer given
     */
    public static Wizard valueOf(int wizard) {
        return map.get(wizard);
    }

    public static int getWizardCode(Wizard wizard) {
        return map.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(wizard))
                .map(entry -> entry.getKey())
                .collect(Collectors.toList())
                .get(0);
    }
}
