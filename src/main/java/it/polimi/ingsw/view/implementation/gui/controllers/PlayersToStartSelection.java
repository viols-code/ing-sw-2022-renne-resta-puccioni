package it.polimi.ingsw.view.implementation.gui.controllers;

import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.fxml.FXML;

public class PlayersToStartSelection {

    @FXML
    private void twoPlayersGame(){
        GUI.instance().getActionSender().setPlayersToStart(2);
    }

    @FXML
    private void threePlayersGame(){
        GUI.instance().getActionSender().setPlayersToStart(3);
    }
}
