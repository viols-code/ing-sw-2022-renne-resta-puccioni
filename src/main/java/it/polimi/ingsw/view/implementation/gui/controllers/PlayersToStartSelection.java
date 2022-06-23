package it.polimi.ingsw.view.implementation.gui.controllers;

import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.fxml.FXML;

/**
 * The page in which the first player connected decides the number of players of the game
 */
public class PlayersToStartSelection {

    /**
     * Method called when the player clicks on the button "2"
     */
    @FXML
    private void twoPlayersGame() {
        GUI.instance().getActionSender().setPlayersToStart(2);
    }

    /**
     * Method called when the player clicks on the button "3"
     */
    @FXML
    private void threePlayersGame() {
        GUI.instance().getActionSender().setPlayersToStart(3);
    }
}
