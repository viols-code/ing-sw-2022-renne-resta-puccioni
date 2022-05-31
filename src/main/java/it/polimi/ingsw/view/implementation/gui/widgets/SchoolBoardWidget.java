package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.beans.MockPlayer;
import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.*;

public class SchoolBoardWidget extends StackPane {

    @FXML
    private AnchorPane anchorPane;

    /*
     *BUTTONS
     */

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

    /*
    Entrance
     */
    @FXML
    private GridPane entrance;


    /*
     * Dining room
     */
    @FXML
    private GridPane diningRoom;

    @FXML
    private ImageView currentAssistantCard;

    private List<Coordinates> entranceBoxes;

    /*
    PROFESSORS TABLE
     */
    @FXML
    private GridPane professorsTable;

    public SchoolBoardWidget() {
        FXMLUtils.loadWidgetFXML(this);
    }

    @FXML
    private void initialize() {
        entranceBoxes = new ArrayList<>(Arrays.asList(new Coordinates(2, 0), new Coordinates(0, 2), new Coordinates(2, 2), new Coordinates(0, 4), new Coordinates(2, 4), new Coordinates(0, 6), new Coordinates(2, 6), new Coordinates(0, 8), new Coordinates(2, 8)));

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

        int row = 3;

        for (int i = 0; i < GUI.instance().getNumPlayers() - 1; i++) {
            Button button = new Button();
            button.setText(players.get(i).getNickname() + "'s school board");
            int a = i;
            button.setOnMouseClicked(event -> Platform.runLater(() -> GUI.instance().showOtherPlayerBoard(players.get(a))));
            //button.setPrefWidth(Double.MAX_VALUE);
            gridPane.add(button, 0, row);
            row++;
        }

        if (GUI.instance().getGameMode()) {
            Button button = new Button();
            button.setText("Character Cards");
            button.setOnMouseClicked(event -> Platform.runLater(() -> GUI.instance().showCharacterCards()));
            gridPane.add(button, 0, row);
            ImageView coins = new ImageView();
            anchorPane.getChildren().add(coins);
            coins.setFitWidth(87);
            coins.setFitHeight(100);
            coins.setLayoutX(815);
            coins.setLayoutY(163);
            coins.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                    "/images/Moneta_base.png"))));
            Label numberCoins = new Label();
            numberCoins.setLayoutX(914);
            numberCoins.setLayoutY(197);
            numberCoins.setPrefSize(12, 32);
            anchorPane.getChildren().add(numberCoins);

            numberCoins.setText(String.valueOf(GUI.instance().getModel().getLocalPlayer().getCoins()));
            GUI.instance().getModel().getLocalPlayer().getCoinsProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {
                numberCoins.setText(String.valueOf(GUI.instance().getModel().getLocalPlayer().getCoins()));
            }));
        }

        initCurrentAssistantCard();
        initEntrance();
        initDiningRoom();
        initProfessorsTable();

    }


    @FXML
    private void goToIsland() {
        GUI.instance().showIslands();
    }

    @FXML
    private void goToAssistantCards() {
        GUI.instance().showAssistantCards();
    }

    @FXML
    private void selectStudentFromEntrance(Colour colour) {
        GUI.instance().getModel().setSelectedColour(colour);
        diningRoom.setOnMouseClicked(event -> moveStudentToDiningRoom());
    }

    @FXML
    private void moveStudentToDiningRoom() {
        GUI.instance().getActionSender().moveStudentToDiningRoom(GUI.instance().getPlayerName(), GUI.instance().getModel().getSelectedColour());
        diningRoom.setOnMouseClicked(event -> noAction());
    }

    @FXML
    private void noAction() {

    }

    private void initEntrance() {
        //init the entrance
        List<Colour> entranceStudents = new ArrayList<>();
        for (Colour colour : Colour.values()) {
            for (int i = 0; i < GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getEntrance().get(colour); i++) {
                entranceStudents.add(colour);
            }
        }

        setStudentEntrance(entranceStudents);

        GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getEntranceProperty().addListener((MapChangeListener<? super Colour, ? super Integer>) listener ->
                Platform.runLater(() -> {
                    entrance.getChildren().forEach(node -> node.setVisible(false));
                    entrance.getChildren().removeAll();
                    List<Colour> students = new ArrayList<>();
                    for (Colour colour : Colour.values()) {
                        for (int i = 0; i < GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getEntrance().get(colour); i++) {
                            students.add(colour);
                        }
                    }

                    setStudentEntrance(students);
                }));
    }

    private void initDiningRoom() {
        for (Colour colour : Colour.values()) {
            for (int i = 0; i < GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getDiningRoom().get(colour); i++) {
                ImageView imageView = new ImageView();
                diningRoom.add(imageView, i, getDiningRoomTable(colour));
                imageView.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                        "/images/students/student_" + colour + ".png"))));

            }
        }

        diningRoom.getStyleClass().add("diningRoom");

        GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getDiningRoomProperty().addListener((MapChangeListener<? super Colour, ? super Integer>) listener ->
                Platform.runLater(() -> {
                    for (Colour colour : Colour.values()) {
                        for (int i = 0; i < GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getDiningRoom().get(colour); i++) {
                            ImageView imageView = new ImageView();
                            diningRoom.add(imageView, i, getDiningRoomTable(colour));
                            imageView.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                                    "/images/students/student_" + colour + ".png"))));

                        }
                    }
                }));
    }

    private int getDiningRoomTable(Colour colour) {
        int res = 0;
        switch (colour) {
            case GREEN -> {
                res = 0;
            }
            case RED -> {
                res = 2;
            }
            case YELLOW -> {
                res = 4;
            }
            case PINK -> {
                res = 6;
            }
            case BLUE -> {
                res = 8;
            }
        }
        return res;
    }

    public void setStudentEntrance(List<Colour> entranceStudents){
        for (int i = 0; i < entranceStudents.size(); i++) {
            FlowPane flowPane = new FlowPane();
            entrance.add(flowPane, entranceBoxes.get(i).getRow(), entranceBoxes.get(i).getColumn());
            ImageView imageView = new ImageView();
            flowPane.getChildren().add(imageView);
            flowPane.getStyleClass().add("student");
            imageView.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                    "/images/students/student_" + entranceStudents.get(i).name().toLowerCase(Locale.ROOT) + ".png"))));
            Colour colour = entranceStudents.get(i);
            imageView.setOnMouseClicked(event -> selectStudentFromEntrance(colour));
        }
    }

    private void initProfessorsTable() {
        for (Colour colour : Colour.values()) {
            if (GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getProfessorTable().get(colour)) {
                ImageView imageView = new ImageView();
                professorsTable.add(imageView, 0, getDiningRoomTable(colour));
                imageView.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                        "/images/professors/teacher_" + colour + ".png")), 40, 40, false, false));

            }
        }

        GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getProfessorTableProperty().addListener((MapChangeListener<? super Colour, ? super Boolean>) listener ->
                Platform.runLater(() -> {
                    for (Colour colour : Colour.values()) {
                        if (GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getProfessorTable().get(colour)) {
                            ImageView imageView = new ImageView();
                            professorsTable.add(imageView, 0, getDiningRoomTable(colour));
                            imageView.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                                    "/images/professors/teacher_" + colour + ".png")), 40, 40, false, false));

                        }
                    }
                }));
    }

    private void initCurrentAssistantCard() {
        if (GUI.instance().getModel().getLocalPlayer().getCurrentAssistantCardProperty().getValue() != null) {
            currentAssistantCard.setImage(new Image(Objects.requireNonNull(AssistantCardsWidget.class.getResourceAsStream(
                    "/images/assistantCards/assistant_" + (GUI.instance().getModel().getLocalPlayer().getCurrentAssistantCardProperty().getValue().getValue() - 1) + ".png"))));
        } else {
            currentAssistantCard.setImage(new Image(Objects.requireNonNull(AssistantCardsWidget.class.getResourceAsStream(
                    "/images/wizard/wizard" + (Wizard.getWizardCode(GUI.instance().getModel().getLocalPlayer().getWizard())) + ".png"))));
        }
        GUI.instance().getModel().getLocalPlayer().getCurrentAssistantCardProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {
            if (GUI.instance().getModel().getLocalPlayer().getCurrentAssistantCardProperty().getValue() != null) {
                currentAssistantCard.setImage(new Image(Objects.requireNonNull(AssistantCardsWidget.class.getResourceAsStream(
                        "/images/assistantCards/assistant_" + (GUI.instance().getModel().getLocalPlayer().getCurrentAssistantCardProperty().getValue().getValue() - 1) + ".png"))));
            } else {
                currentAssistantCard.setImage(new Image(Objects.requireNonNull(AssistantCardsWidget.class.getResourceAsStream(
                        "/images/wizard/wizard" + (Wizard.getWizardCode(GUI.instance().getModel().getLocalPlayer().getWizard())) + ".png"))));
            }
        }));
    }

    @FXML
    public void goToCloudTile() {
        GUI.instance().showCloudTile();
    }


}
