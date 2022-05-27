package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.view.beans.MockPlayer;
import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

public class SchoolBoardWidget {

    @FXML
    private Image studentEntrance1;

    /*
     *BUTTONS
     */
    @FXML
    private Button islandsButton;

    @FXML
    private Button deckButton;

    @FXML
    private Button player1Button;

    @FXML
    private Button player2Button;

    @FXML
    private Button charactersButton;

    /*
     * LABELS
     */
    @FXML
    private Label roundNumberLabel;

    @FXML
    private Label currentPlayerLabel;

    @FXML
    private Label turnPhaseLabel;

    private MockPlayer localPlayer;

    private void initialize(){

       localPlayer = GUI.instance().getModel().getLocalPlayer();
       //Shows the round
        roundNumberLabel.setText("ciao");
        GUI.instance().getModel().getRoundProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {
            roundNumberLabel.setText(GUI.instance().getModel().getRoundProperty().toString());
        }));

       //Shows the turn
       currentPlayerLabel.setText("ciao ciao");
       GUI.instance().getModel().getCurrentPlayerProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {
           if (GUI.instance().getPlayerName().equals(newVal)) {
               currentPlayerLabel.setText("You");
           } else {
               currentPlayerLabel.setText(GUI.instance().getModel().getCurrentPlayer().getNickname());
           }
       }));

       //Shows the turn phase
        roundNumberLabel.setText("play assistant card");
        GUI.instance().getModel().getRoundProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {
            roundNumberLabel.setText(GUI.instance().getModel().getTurnPhase().name());
        }));

    }

    @FXML
    private void goToPlayer1SchoolBoard() {
        Platform.runLater(() -> {
            SchoolBoardWidget schoolBoardWidget = new SchoolBoardWidget();
            //GUI.instance().setRoot(schoolBoardWidget);
        });
    }


}
