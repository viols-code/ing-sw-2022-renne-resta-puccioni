package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.player.TowerColour;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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

    /*
    TOWERS
     */
    @FXML
    private GridPane towers;

    private final List<Circle> towersImage = new ArrayList<>();
    private final List<ImageView> professorImage = new ArrayList<>();
    private final List<ImageView> diningRoomImage = new ArrayList<>();

    public SchoolBoardWidget() {
        FXMLUtils.loadWidgetFXML(this);
    }

    @FXML
    private void initialize() {
        entranceBoxes = new ArrayList<>(Arrays.asList(new Coordinates(2, 0), new Coordinates(0, 2), new Coordinates(2, 2), new Coordinates(0, 4), new Coordinates(2, 4), new Coordinates(0, 6), new Coordinates(2, 6), new Coordinates(0, 8), new Coordinates(2, 8)));

        //Shows the current Player
        currentPlayerLabel.setText(GUI.instance().isOwnTurn() ? "Yours" :
                GUI.instance().getModel().getCurrentPlayer().getNickname());
        GUI.instance().getModel().getCurrentPlayerProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> currentPlayerLabel.setText(GUI.instance().isOwnTurn() ? "Yours" :
                GUI.instance().getModel().getCurrentPlayer().getNickname())));


        // Shows the round
        roundNumberLabel.setText(String.valueOf(GUI.instance().getModel().getRound()));
        GUI.instance().getModel().getRoundProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> roundNumberLabel.setText(String.valueOf(GUI.instance().getModel().getRound()))));


        // Shows the turn phase
        turnPhaseLabel.setText(GUI.instance().getModel().getTurnPhase().name());
        GUI.instance().getModel().getTurnPhaseProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> turnPhaseLabel.setText(GUI.instance().getModel().getTurnPhase().name())));


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
            numberCoins.setStyle("-fx-font: 22 System;");
            anchorPane.getChildren().add(numberCoins);

            numberCoins.setText(String.valueOf(GUI.instance().getModel().getLocalPlayer().getCoins()));
            GUI.instance().getModel().getLocalPlayer().getCoinsProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> numberCoins.setText(String.valueOf(GUI.instance().getModel().getLocalPlayer().getCoins()))));
        }

        initCurrentAssistantCard();
        initEntrance();
        initDiningRoom();
        initProfessorsTable();
        initTowers();

    }


    @FXML
    private void goToIsland() {
        GUI.instance().showIslands();
    }

    @FXML
    private void goToAssistantCards() {

        GUI.instance().getModel().setPosition(-1);
        GUI.instance().getModel().setSelectedColour(null);

        GUI.instance().showAssistantCards();
    }

    @FXML
    private void selectStudentFromEntrance(int i, Colour colour) {
        GUI.instance().getModel().setPosition(i);
        GUI.instance().getModel().setSelectedColour(colour);
        diningRoom.setOnMouseClicked(event -> moveStudentToDiningRoom());
    }

    @FXML
    private void moveStudentToDiningRoom() {
        GUI.instance().getActionSender().moveStudentToDiningRoom(GUI.instance().getPlayerName(), GUI.instance().getModel().getSelectedColour());
        GUI.instance().getModel().setPosition(-1);
        GUI.instance().getModel().setSelectedColour(null);
        diningRoom.setOnMouseClicked(event -> noAction());
    }

    @FXML
    private void noAction() {
    }

    private void initEntrance() {
        //init the entrance
        initEntranceArrays();

        if (GUI.instance().getModel().getPosition().getValue() != -1) {
            entrance.getChildren().get(GUI.instance().getModel().getPosition().getValue()).getStyleClass().add("studentSelected");
        }

        GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getEntranceProperty().addListener((MapChangeListener<? super Colour, ? super Integer>) listener ->
                Platform.runLater(() -> {
                    entrance.getChildren().forEach(node -> node.setVisible(false));
                    entrance.getChildren().removeAll();
                    initEntranceArrays();
                }));

        GUI.instance().getModel().getPosition().addListener((change, oldval, newval) -> {
            if (oldval.intValue() != -1) {
                entrance.getChildren().get(oldval.intValue()).getStyleClass().removeAll("studentSelected");
            }
            if (newval.intValue() != -1) {
                entrance.getChildren().get(newval.intValue()).getStyleClass().add("studentSelected");
            }
        });
    }

    private void initEntranceArrays() {
        List<Colour> entranceStudents = new ArrayList<>();
        for (Colour colour : Colour.values()) {
            for (int i = 0; i < GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getEntrance().get(colour); i++) {
                entranceStudents.add(colour);
            }
        }

        setStudentEntrance(entranceStudents);
    }

    private void initDiningRoom() {
        initDiningRoomImage();

        diningRoom.getStyleClass().add("diningRoom");

        GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getDiningRoomProperty().addListener((MapChangeListener<? super Colour, ? super Integer>) listener ->
                Platform.runLater(() -> {
                    diningRoom.getChildren().removeAll(diningRoomImage);
                    diningRoomImage.clear();
                    initDiningRoomImage();
                }));
    }

    private void initDiningRoomImage(){
        for (Colour colour : Colour.values()) {
            for (int i = 0; i < GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getDiningRoom().get(colour); i++) {
                ImageView imageView = new ImageView();
                diningRoomImage.add(imageView);
                diningRoom.add(imageView, i, getDiningRoomTable(colour));
                imageView.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                        "/images/students/student_" + colour + ".png"))));

            }
        }
    }

    private int getDiningRoomTable(Colour colour) {
        int res = -1;
        switch (colour) {
            case GREEN -> res = 0;
            case RED -> res = 2;
            case YELLOW -> res = 4;
            case PINK -> res = 6;
            case BLUE -> res = 8;
        }
        return res;
    }

    public void setStudentEntrance(List<Colour> entranceStudents) {
        for (int i = 0; i < entranceStudents.size(); i++) {
            FlowPane flowPane = new FlowPane();
            entrance.add(flowPane, entranceBoxes.get(i).getRow(), entranceBoxes.get(i).getColumn());
            ImageView imageView = new ImageView();
            flowPane.getChildren().add(imageView);
            flowPane.getStyleClass().add("student");
            imageView.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                    "/images/students/student_" + entranceStudents.get(i).name().toLowerCase(Locale.ROOT) + ".png"))));
            Colour colour = entranceStudents.get(i);
            int a = i;
            imageView.setOnMouseClicked(event -> {
                flowPane.getStyleClass().add("studentSelected");
                selectStudentFromEntrance(a, colour);
            });
        }
    }

    private void initProfessorsTable() {
        initProfessorTableImage();

        GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getProfessorTableProperty().addListener((MapChangeListener<? super Colour, ? super Boolean>) listener ->
                Platform.runLater(() -> {
                    professorsTable.getChildren().removeAll(professorImage);
                    professorImage.clear();
                    initProfessorTableImage();
                }));
    }


    private void initProfessorTableImage(){
        for (Colour colour : Colour.values()) {
            if (GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getProfessorTable().get(colour)) {
                ImageView imageView = new ImageView();
                professorImage.add(imageView);
                professorsTable.add(imageView, 0, getDiningRoomTable(colour));
                imageView.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                        "/images/professors/teacher_" + colour + ".png")), 40, 40, false, false));

            }
        }
    }



    private void initCurrentAssistantCard() {
        setCurrentAssistantCard();

        GUI.instance().getModel().getLocalPlayer().isAssistantCardValueProperty().addListener((change, oldVal, newVal) -> Platform.runLater(this::setCurrentAssistantCard));
    }


    private void setCurrentAssistantCard() {
        if (GUI.instance().getModel().getLocalPlayer().isAssistantCardValue()) {
            currentAssistantCard.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                    "/images/assistantCards/assistant_" + (GUI.instance().getModel().getLocalPlayer().getCurrentAssistantCardProperty().getValue().getValue() - 1) + ".png"))));
        } else {
            currentAssistantCard.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                    "/images/wizard/wizard" + (Wizard.getWizardCode(GUI.instance().getModel().getLocalPlayer().getWizard())) + ".png"))));
        }
    }

    private void initTowers() {
        initTowerImage();

        GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getTowersProperty().addListener((change, oldVal, newVal) ->
                Platform.runLater(() -> {
                        towers.getChildren().removeAll(towersImage);
                        towersImage.clear();
                        initTowerImage();
                }));
    }

    private void initTowerImage(){
        int j = 0;
        int k = 0;

        for (int i = 0; i < GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getTowers(); i++) {
            Circle tower = new Circle();
            towersImage.add(tower);
            tower.setRadius(24);
            if (GUI.instance().getModel().getLocalPlayer().getTowerColour() == TowerColour.WHITE) {
                tower.setFill(Color.rgb(255, 255, 255));
            } else if (GUI.instance().getModel().getLocalPlayer().getTowerColour() == TowerColour.BLACK) {
                tower.setFill(Color.rgb(0, 0, 0));
            } else if (GUI.instance().getModel().getLocalPlayer().getTowerColour() == TowerColour.GREY) {
                tower.setFill(Color.rgb(179, 179, 179));
            }
            towers.add(tower, j, k);

            if (j == 0) {
                j += 2;
            } else {
                j = 0;
                k += 2;
            }
        }
    }

    @FXML
    public void goToCloudTile() {

        GUI.instance().getModel().setPosition(-1);
        GUI.instance().getModel().setSelectedColour(null);

        GUI.instance().showCloudTile();
    }


}
