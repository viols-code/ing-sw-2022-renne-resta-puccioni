package it.polimi.ingsw.view.implementation.gui.controllers;

import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.fxml.FXML;

/**
 * Controller of the page used for the game mode selection
 */
public class GameModeSelection {

    /**
     * The method called when the player clicks on the "basic" button
     */
    @FXML
    private void basic() {
        GUI.instance().getActionSender().setGameMode(false);
    }

    /**
     * The method called when the player clicks on the "expert" button
     */
    @FXML
    private void expert() {
        GUI.instance().getActionSender().setGameMode(true);
    }
}
