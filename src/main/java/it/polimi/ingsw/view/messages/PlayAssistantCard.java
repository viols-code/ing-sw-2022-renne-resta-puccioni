package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;

import java.io.Serial;

/**
 * Message to the server notifying the chosen AssistantCard
 */
public class PlayAssistantCard extends PlayerAction {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -697694550449639585L;
    /**
     * The AssistantCard's position
     */
    private final int assistantCard;

    /**
     * Constructor
     *
     * @param player the player's nickname
     * @param assistantCard the AssistantCard's position
     */
    public PlayAssistantCard(String player, int assistantCard) {
        super(player);
        this.assistantCard = assistantCard;
    }

    /**
     * Process the message by calling a method in the gameController
     *
     * @param controller the controller that will process the packet
     */
    @Override
    public void process(GameController controller) {
        controller.playAssistantCard(getPlayer(), assistantCard);
    }
}
