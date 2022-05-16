package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

public class CurrentAssistantCardUpdate extends PlayerUpdate {
    @Serial
    private static final long serialVersionUID = 5500455658820902341L;
    private final String playerName;
    private final int assistantCard;

    public CurrentAssistantCardUpdate(String playerName, int assistantCard) {
        this.playerName = playerName;
        this.assistantCard = assistantCard;
    }

    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateCurrentAssistantCard(playerName, assistantCard);
    }
}
