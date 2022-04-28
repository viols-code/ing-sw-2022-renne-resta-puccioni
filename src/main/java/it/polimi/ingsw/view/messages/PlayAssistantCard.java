package it.polimi.ingsw.view.messages;

import it.polimi.ingsw.controller.GameController;

import java.io.Serial;

public class PlayAssistantCard extends PlayerAction{
    @Serial
    private static final long serialVersionUID = -697694550449639585L;
    private int player;
    private final int assistantCard;

    public PlayAssistantCard(int player, int assistantCard){
        this.player = player;
        this.assistantCard = assistantCard;
    }

    @Override
    public void process(GameController controller) {
        controller.playCharacterCard(player, assistantCard);
    }
}
