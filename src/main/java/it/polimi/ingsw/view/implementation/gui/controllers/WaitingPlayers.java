package it.polimi.ingsw.view.implementation.gui.controllers;

import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

/**
 * The page that the player sees when he/she is already connected but there are not enough players connected yet.
 * This page is shown while the player is waiting for other players to connect.
 * The page is updated every time a new player connects with his/her nickname.
 */
public class WaitingPlayers {

    @FXML
    private GridPane playerList;

    private List<String> players;

    @FXML
    private void initialize() {

        GUI gui = GUI.instance();

        //players contains all the nicknames of the players connected
        players = FXCollections.observableList(new ArrayList<>(gui.getModel().getNicknames()));

        gui.getModel().getNicknames().addListener((ListChangeListener<? super String>) change -> {
            if (change.next()) {
                //the list nickname is created in order to avoid duplicated names every time a new nickname is added
                List<String> nicknames = new ArrayList<>();
                for (int k = 0; k < change.getAddedSize(); k++) {
                    nicknames.add(change.getAddedSubList().get(k));
                }
                Platform.runLater(() -> {
                    for (int j = 0; j < change.getAddedSubList().size(); j++) {
                        //deletes duplicated nicknames from the list nickname
                        for (int i = 0; i < players.size(); i++) {
                            if (change.getAddedSubList().get(j).equals(players.get(i))) {
                                nicknames.remove(j);
                            }
                        }
                    }

                    //adds the new nicknames but the ones already existing to the list players
                    players.addAll(nicknames);
                    initializeGrid();
                });
            }
        });

        initializeGrid();
    }

    /**
     * Creates the grid where the nicknames are shown
     */
    private void initializeGrid() {
        for (int i = 0; i < players.size(); i++) {
            Label label = new Label();
            label.setText(players.get(i));
            label.setStyle("-fx-font: 22 System;");
            GridPane.setHalignment(label, HPos.CENTER);
            playerList.add(label, 0, i + 1);
        }
    }
}
