package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;

import java.io.Serial;

/**
 * Message to notify the update of the current character card
 */
public class PlayCharacterCard extends PlayerAction {

    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -695694550449639585L;

    /**
     * The nickname of the player
     */
    private String player;

    /**
     * The character card played
     */
    private final int characterCard;

    /**
     * Constructs the message
     *
     * @param player the nickname of the player
     * @param characterCard the index of the character card played
     */
    public PlayCharacterCard(String player, int characterCard) {
        this.player = player;
        this.characterCard = characterCard;
    }

    /**
     * Notifies the game controller with the character card played
     *
     * @param controller the controller that will process the packet
     */
    @Override
    public void process(GameController controller) {
        controller.playCharacterCard(player, characterCard);
    }
}
