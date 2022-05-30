package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

public class AssistantCardNullUpdate extends GameUpdate{
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -3154128929671352189L;
    /**
     * The nickname of the player
     */
    private final String playerName;

    /**
     * Constructor
     *
     * @param playerName    the nickname of the player
     */
    public AssistantCardNullUpdate(String playerName){
        this.playerName = playerName;
    }

    /**
     * Update the model with the character card activated
     *
     * @param view view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateCurrentAssistantCardNull(playerName);
    }

}
