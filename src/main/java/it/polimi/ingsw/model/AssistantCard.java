package it.polimi.ingsw.model;

import java.util.Objects;

/**
 * AssistantCard
 *
 * @version 1.0
 */
public class AssistantCard {
    /**
     * The value of the assistant card
     */
    private final int value;
    /**
     * The mother nature steps you can do if you play this card
     */
    private final int motherNatureMovement;

    /**
     * Constructor: creates a new AssistantCard with the given value and motherNatureMovement.
     *
     * @param value                value used for turn order
     * @param motherNatureMovement movements Mother Nature may perform
     */
    public AssistantCard(int value, int motherNatureMovement) {
        this.value = value;
        this.motherNatureMovement = motherNatureMovement;
    }

    /**
     * Get the card value
     *
     * @return value used for turn order
     */
    public int getValue() {
        return value;
    }

    /**
     * Get the motherNatureMovement
     *
     * @return movements Mother Nature may perform
     */
    public int getMotherNatureMovement() {
        return motherNatureMovement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssistantCard that = (AssistantCard) o;
        return getValue() == that.getValue() && getMotherNatureMovement() == that.getMotherNatureMovement();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue(), getMotherNatureMovement());
    }
}