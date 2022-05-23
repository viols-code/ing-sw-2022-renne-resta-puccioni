package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * Update: The current assistant card has changed
 */
public class CurrentAssistantCardUpdate extends PlayerUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 5500455658820902341L;
    /**
     * The nickname of the player
     */
    private final String playerName;
    /**
     * The new assistant card
     */
    private final int assistantCard;

    /**
     * Constructor
     *
     * @param playerName    the nickname of the player
     * @param assistantCard the assistant card the player has chosen to play
     */
    public CurrentAssistantCardUpdate(String playerName, int assistantCard) {
        this.playerName = playerName;
        this.assistantCard = assistantCard;
    }

    /**
     * Update the view with new assistant card played by this player
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateCurrentAssistantCard(playerName, assistantCard);
    }
}
