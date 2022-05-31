package it.polimi.ingsw.view.implementation.gui;

import it.polimi.ingsw.model.game.GamePhase;
import it.polimi.ingsw.view.ModelUpdateHandler;

public class GUIModelUpdateHandler extends ModelUpdateHandler {
    public GUIModelUpdateHandler(GUI gui) {
        super(gui);
    }

    @Override
    public void updateGamePhase(GamePhase gamePhase) {
        super.updateGamePhase(gamePhase);
        if (gamePhase == GamePhase.PLAY_ASSISTANT_CARD && getView().getModel().getRound() == 1) {
            GUI.instance().showPlayerBoard();
        }
    }

    public void updateCurrentAssistantCard(String playerName, int assistantCard) {
        super.updateCurrentAssistantCard(playerName, assistantCard);
    }
}
