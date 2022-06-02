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
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class WaitingPlayers {

    @FXML
    private Label currentPlayers;

    @FXML
    private Label playersToStart;

    @FXML
    private GridPane playerList;

    private List<String> players;

    @FXML
    private void initialize() {
        GUI gui = GUI.instance();

        //Da sistemare, c'è ancora qualche problemino quando si connette un altro giocatore prima che il primo abbia deciso il num di giocatori

        currentPlayers.setText(gui.getModel().currentPlayersProperty().getValue().toString());
        playersToStart.setText(gui.getModel().playersToStartProperty().getValue().toString());
        playersToStart.setVisible(false);

        if(gui.getModel().playersToStartProperty().getValue() != -1) {
            playersToStart.setVisible(true);
        }

        else {
            gui.getModel().playersToStartProperty().addListener((change, prev, next) -> {

                Platform.runLater(() -> {
                    if (gui.getModel().playersToStartProperty().getValue() != -1) {
                        playersToStart.setVisible(true);
                    }
                });
            });
        }

        gui.getModel().currentPlayersProperty().addListener((change, prev, next) -> {
            if(next.intValue() != prev.intValue()) {
                Platform.runLater(() -> {
                    currentPlayers.setText(next.toString());
                });
            }
        });


        //Questo funziona!
        players = FXCollections.observableList(new ArrayList<>(gui.getModel().getNicknames()));

        gui.getModel().getNicknames().addListener((ListChangeListener<? super String>) change -> {
                if(change.next()) {
                    Platform.runLater(() -> {
                        players.addAll(change.getAddedSubList());
                        initializeGrid();
                    });
                }
        });

        initializeGrid();
    }

    private void initializeGrid(){
        for(int i = 0; i < players.size(); i++){
            Label label = new Label();
            label.setText(players.get(i));
            label.setStyle("-fx-font: 22 System;");
            GridPane.setHalignment(label, HPos.CENTER);
            playerList.add(label, 0, i+1);
        }
    }
}
