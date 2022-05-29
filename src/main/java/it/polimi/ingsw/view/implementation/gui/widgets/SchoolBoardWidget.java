package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.beans.MockPlayer;
import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SchoolBoardWidget extends StackPane {

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
    private GridPane gridPane;


    /*
     * LABELS
     */
    @FXML
    private Label roundNumberLabel;

    @FXML
    private Label currentPlayerLabel;

    @FXML
    private Label turnPhaseLabel;

    @FXML
    private GridPane entrance;


    public SchoolBoardWidget() {
        FXMLUtils.loadWidgetFXML(this);
    }

    @FXML
    private void initialize() {
        //Shows the current Player
        currentPlayerLabel.setText(GUI.instance().isOwnTurn() ? "Yours" :
                GUI.instance().getModel().getCurrentPlayer().getNickname());
        GUI.instance().getModel().getCurrentPlayerProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {
            currentPlayerLabel.setText(GUI.instance().isOwnTurn() ? "Yours" :
                    GUI.instance().getModel().getCurrentPlayer().getNickname());
        }));


        // Shows the round
        roundNumberLabel.setText(String.valueOf(GUI.instance().getModel().getRound()));
        GUI.instance().getModel().getRoundProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {
            roundNumberLabel.setText(String.valueOf(GUI.instance().getModel().getRound()));
        }));


        // Shows the turn phase
        turnPhaseLabel.setText(GUI.instance().getModel().getTurnPhase().name());
        GUI.instance().getModel().getTurnPhaseProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {
            turnPhaseLabel.setText(GUI.instance().getModel().getTurnPhase().name());
        }));

        List<MockPlayer> players = new ArrayList<>();

        for (MockPlayer player : GUI.instance().getModel().getPlayers().values()) {
            if (player.equals(GUI.instance().getModel().getLocalPlayer()))
                continue;
            players.add(player);
        }

        int row = 2;

        for(int i = 0; i < GUI.instance().getNumPlayers() - 1; i++){
            Button button = new Button();
            button.setText(players.get(i).getNickname() + "'s school board");
            int a = i;
            button.setOnMouseClicked(event -> Platform.runLater(() -> GUI.instance().showOtherPlayerBoard(players.get(a))));
            button.setPrefWidth(Double.MAX_VALUE);
            gridPane.add(button, 0, row);
            row++;
        }

        if(GUI.instance().getGameMode()){
            Button button = new Button();
            button.setText("Character Cards");
            button.setOnMouseClicked(event -> Platform.runLater(() -> GUI.instance().showCharacterCards()));
            gridPane.add(button, 0, row);
        }

        int c = 0;
        int r = 2;

        for(Colour colour : Colour.values()){
            for(int i = 0; i < GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getEntrance().get(colour); i++){
                ImageView imageView = new ImageView();
                imageView.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                        "/images/students/student_" + colour.name().toLowerCase(Locale.ROOT) + ".png"))));
                entrance.add(imageView, r, c);
                if(r == 2){
                    r = 0;
                    c+=2;
                }
                else if (r == 0){
                    r = 2;
                }

            }
        }

        GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getEntranceProperty().addListener((MapChangeListener<? super Colour,? super Integer> ) listener ->
                Platform.runLater(() -> {

            }));

    }


    @FXML
    private void goToIsland() {
        GUI.instance().showIslands();
    }

    @FXML
    private void goToAssistantCards() {
        GUI.instance().showAssistantCards();
    }

}
