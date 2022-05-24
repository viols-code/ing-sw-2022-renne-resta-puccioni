package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;

import java.io.Serial;

/**
 * Message to notify the update of the current assistant card
 */
public class PlayAssistantCard extends PlayerAction {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -697694550449639585L;

    /**
     * THe nickname of the player
     */
    private String player;

    /**
     * The index of the assistant card
     */
    private final int assistantCard;

    /**
     * Constructs the message
     *
     * @param player the nickname of the player
     * @param assistantCard the index of the assistant card
     */
    public PlayAssistantCard(String player, int assistantCard) {
        this.player = player;
        this.assistantCard = assistantCard;
    }

    /**
     * Notifies the game controller with the assistant card chosen
     *
     * @param controller the controller that will process the packet
     */
    @Override
    public void process(GameController controller) {
        controller.playAssistantCard(player, assistantCard);
    }
}
