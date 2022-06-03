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
    private GridPane playerList;

    private List<String> players;

    @FXML
    private void initialize() {
        GUI gui = GUI.instance();

        //Questo funziona!
        players = FXCollections.observableList(new ArrayList<>(gui.getModel().getNicknames()));

        gui.getModel().getNicknames().addListener((ListChangeListener<? super String>) change -> {
                if(change.next()) {
                    List<String> nicknames = new ArrayList<>();
                    for(int k= 0; k < change.getAddedSize(); k++){
                        nicknames.add(change.getAddedSubList().get(k));
                    }
                    Platform.runLater(() -> {
                        for(int j = 0; j < change.getAddedSubList().size(); j++){
                            for(int i = 0; i < players.size(); i++){
                                if(change.getAddedSubList().get(j).equals(players.get(i))){
                                    nicknames.remove(j);
                                 }
                            }
                        }

                        players.addAll(nicknames);
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
