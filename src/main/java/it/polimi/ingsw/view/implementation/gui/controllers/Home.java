package it.polimi.ingsw.view.implementation.gui.controllers;

import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;

/**
 * The page in which the player connects to the game
 */
public class Home {

    @FXML
    public VBox leftVBox;

    @FXML
    private TextField serverIp;

    @FXML
    private ToggleButton connect;

    @FXML
    private Label errorDisplay;

    /**
     * Init method called by the FXMLLoader
     */
    @FXML
    private void initialize() {

        serverIp.setText("localhost:54321");
        serverIp.setOnMouseClicked(mouseEvent -> serverIp.selectAll());

        connect.setOnAction(actionEvent -> {
            if (!GUI.instance().getClient().connect(serverIp.textProperty().get())) {
                errorDisplay.setText("Can't connect to the server!");
                errorDisplay.setVisible(true);
            }
        });
    }


}
