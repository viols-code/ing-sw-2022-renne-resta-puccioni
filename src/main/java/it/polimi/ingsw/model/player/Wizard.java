package it.polimi.ingsw.model.player;

import java.util.HashMap;
import java.util.Map;

public enum Wizard {
    TYPE_1(1),
    TYPE_2(2),
    TYPE_3(3),
    TYPE_4(4);

    private final int value;
    private static final Map<Integer, Wizard> map = new HashMap<>();

    Wizard(int value) {
        this.value = value;
    }

    static {
        for (Wizard wizard : Wizard.values()) {
            map.put(wizard.value, wizard);
        }
    }

    public static Wizard valueOf(int wizard) {
        return map.get(wizard);
    }
}
