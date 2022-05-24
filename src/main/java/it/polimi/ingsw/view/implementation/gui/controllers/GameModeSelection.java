package it.polimi.ingsw.view.implementation.gui.controllers;

import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.fxml.FXML;

public class GameModeSelection {

    @FXML
    private void basic() {
        GUI.instance().getActionSender().setGameMode(false);
    }

    @FXML
    private void expert() {
        GUI.instance().getActionSender().setGameMode(true);
    }
}
