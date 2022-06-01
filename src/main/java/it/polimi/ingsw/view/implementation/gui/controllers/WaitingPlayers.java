package it.polimi.ingsw.view.implementation.gui.controllers;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.beans.MockPlayer;
import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class WaitingPlayers {


    @FXML
    private GridPane playerList;

    private List<String> players;

    @FXML
    private void initialize() {
        GUI gui = GUI.instance();

        players = FXCollections.observableList(new ArrayList<>(gui.getModel().getNicknames()));

        gui.getModel().getNicknames().addListener((ListChangeListener<? super String>) change -> {
                if(change.next()) {
                    Platform.runLater(() -> {
                        players.addAll(change.getAddedSubList());
                        for(int i = 0; i < players.size(); i++){
                            Label label = new Label();
                            label.setText(players.get(i));
                            label.setStyle("-fx-font: 22 System;");
                            playerList.add(label, i+1, 0);
                        }
                    });
                }
        });

        for(int i = 0; i < players.size(); i++){
            Label label = new Label();
            label.setText(players.get(i));
            label.setStyle("-fx-font: 22 System;");
            playerList.add(label, i+1, 0);
        }

    }
}
