package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.view.beans.MockPlayer;
import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class SchoolBoardWidget extends StackPane{

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

    private final MockPlayer player;


    public SchoolBoardWidget(MockPlayer player) {
       this.player = player;
        FXMLUtils.loadWidgetFXML(this);
    }

    @FXML
    private void initialize(){
       //Shows the current Player
        currentPlayerLabel.setText(GUI.instance().isOwnTurn() ? "Yours" :
                GUI.instance().getModel().getCurrentPlayer().getNickname());
        GUI.instance().getModel().currentPlayerNameProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {
            if (GUI.instance().getPlayerName().equals(newVal)) {
                currentPlayerLabel.setText("Yours");
            } else {
                currentPlayerLabel.setText(newVal);
            }
        }));


        GUI.instance().getModel().getRoundProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {
            roundNumberLabel.setText(GUI.instance().getModel().getRoundProperty().toString());
        }));


       //Shows the turn phase
        roundNumberLabel.setText("play assistant card");
        GUI.instance().getModel().getRoundProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {
            roundNumberLabel.setText(GUI.instance().getModel().getTurnPhase().name());
        }));

    }

    @FXML
    private void goToPlayer1SchoolBoard(MockPlayer player) {
        Platform.runLater(() -> {
            SchoolBoardWidget schoolBoardWidget = new SchoolBoardWidget(player);
            //GUI.instance().setRoot(schoolBoardWidget);
        });
    }

    @FXML
    private void goToIsland() {
       GUI.instance().showIslands();
    }

    @FXML
    private void goToAssistantCards(){
        GUI.instance().showAssistantCards();
    }

    @FXML
    private void goToCharacterCards(){
        GUI.instance().showCharacterCards();
    }

    @FXML
    private void goToPlayer1() {
        List<MockPlayer> players = new ArrayList<>();

        for (MockPlayer player : GUI.instance().getModel().getPlayers().values()) {
            if (player.equals(GUI.instance().getModel().getLocalPlayer()))
                continue;
            players.add(player);
        }

        Platform.runLater(() -> getScene().setRoot(new SchoolBoardWidget(players.get(0))));
    }

}
