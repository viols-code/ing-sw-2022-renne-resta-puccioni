package it.polimi.ingsw.view.messages;

import java.io.Serial;

/**
 * Represents a message for the CharacterCard's state changing
 */
public abstract class ChangeCharacterCardState extends PlayerEvent {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -695694550449639585L;

    /**
     * Constructor
     *
     * @param player the player's nickname
     */
    public ChangeCharacterCardState(String player) {
        super(player);
    }
}
