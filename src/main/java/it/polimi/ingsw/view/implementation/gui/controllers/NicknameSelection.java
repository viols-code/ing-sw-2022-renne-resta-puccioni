package it.polimi.ingsw.view.implementation.gui.controllers;

import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class NicknameSelection {

    @FXML
    private TextField username;
    @FXML
    private Button choose;
    @FXML
    private Label wrongNickname;

    @FXML
    private void initialize() {
        choose.setOnAction(actionEvent -> {
            if (username.getText().trim().length() != username.getText().length()) {
                wrongNickname.setText("The nickname must be without empty spaces");
                wrongNickname.setVisible(true);
            } else if (username.getText().split(" ").length > 1) {
                wrongNickname.setText("The nickname must be without empty spaces");
                wrongNickname.setVisible(true);
            } else {
                if (!username.getText().isBlank())
                    GUI.instance().setPlayerName(username.getText());
                else {
                    wrongNickname.setText("The nickname cannot be empty");
                    wrongNickname.setVisible(true);
                }
            }
        });
    }
}