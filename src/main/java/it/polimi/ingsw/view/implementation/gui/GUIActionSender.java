package it.polimi.ingsw.view.implementation.gui;

import it.polimi.ingsw.view.ActionSender;

public class GUIActionSender extends ActionSender {
    public GUIActionSender(GUI gui) {
        super(gui);
    }

    /**
     * Sends a message with the chosen assistant card
     *
     * @param localPlayer   the nickname of the local player
     * @param assistantCard the value of assistant card
     */
    @Override
    public void playAssistantCard(String localPlayer, int assistantCard) {
        try {
            super.playAssistantCard(localPlayer, assistantCard);
        } catch (IllegalArgumentException e) {

        }
    }
}
