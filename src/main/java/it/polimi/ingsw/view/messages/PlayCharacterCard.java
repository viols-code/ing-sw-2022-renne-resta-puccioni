package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;

import java.io.Serial;

/**
 * Message to the server notifying the chosen CharacterCard
 */
public class PlayCharacterCard extends PlayerAction {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -695694550449639585L;
    /**
     * The CharacterCard's position
     */
    private final int characterCard;

    /**
     * Constructor
     *
     * @param player        the player's nickname
     * @param characterCard the CharacterCard's position
     */
    public PlayCharacterCard(String player, int characterCard) {
        super(player);
        this.characterCard = characterCard;
    }

    /**
     * Process the message by calling a method in the gameController
     *
     * @param controller the controller that will process the packet
     */
    @Override
    public void process(GameController controller) {
        controller.playCharacterCard(getPlayer(), characterCard);
    }
}
