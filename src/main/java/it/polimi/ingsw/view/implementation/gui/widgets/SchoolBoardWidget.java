package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.model.player.TowerColour;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;
import it.polimi.ingsw.view.beans.MockCard;
import it.polimi.ingsw.view.beans.MockPlayer;
import it.polimi.ingsw.view.implementation.gui.GUI;
import it.polimi.ingsw.view.implementation.gui.widgets.utils.Coordinates;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
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

/**
 * Widgets that shows the school board of the local player
 */
public class SchoolBoardWidget extends StackPane {

    /**
     * The main anchor pane
     */
    @FXML
    private AnchorPane anchorPane;

    /*
     *BUTTONS
     */

    /**
     * Button that makes the mode of the game guided
     */
    @FXML
    private Button guidedModeButton;

    @FXML
    private GridPane gridPane;

    /*
     * LABELS
     */

    /**
     * The number of the current round
     */
    @FXML
    private Label roundNumberLabel;

    /**
     * The nickname of the current player
     */
    @FXML
    private Label currentPlayerLabel;

    /**
     * The current turn phase
     */
    @FXML
    private Label turnPhaseLabel;

    /*
    Entrance
     */
    /**
     * The grid containing the students of the entrance
     */
    @FXML
    private GridPane entrance;

    /*
     * Dining room
     */

    /**
     * The grid containing the students of the dining room
     */
    @FXML
    private GridPane diningRoom;

    /**
     * The image of the assistant card that has been played by the player for this round
     */
    @FXML
    private ImageView currentAssistantCard;

    /**
     * The list of coordinates of the students positions in the entrance grid
     */
    private List<Coordinates> entranceBoxes;

    /*
    PROFESSORS TABLE
     */

    /**
     * The grid containing the professors
     */
    @FXML
    private GridPane professorsTable;

    /*
    TOWERS
     */
    /**
     * The grid containing the images of the towers still present on the school board
     */
    @FXML
    private GridPane towers;

    /**
     * The list containing the towers on the school board
     */
    private final List<Circle> towersImage = new ArrayList<>();

    /**
     * The list containing the images of the professors owned by the player
     */
    private final List<ImageView> professorImage = new ArrayList<>();

    /**
     * The list containing the students of the dining room
     */
    private final List<ImageView> diningRoomImage = new ArrayList<>();

    /**
     * Constructs the SchoolBoardWidget
     */
    public SchoolBoardWidget() {
        FXMLUtils.loadWidgetFXML(this);
    }

    /**
     * Init method called by the FXMLLoader
     */
    @FXML
    private void initialize() {
        //The list containing the coordinates of the entrance grid in which the students must be placed
        entranceBoxes = new ArrayList<>(Arrays.asList(new Coordinates(2, 0), new Coordinates(0, 2), new Coordinates(2, 2), new Coordinates(0, 4), new Coordinates(2, 4), new Coordinates(0, 6), new Coordinates(2, 6), new Coordinates(0, 8), new Coordinates(2, 8)));

        //sets the text on the button for the guide
        if (GUI.instance().isGuidedMode()) {
            guidedModeButton.setText("Disable Guide");
        } else {
            guidedModeButton.setText("Enable Guide");
        }
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

        //adds all the players but the local one to the list of players
        for (MockPlayer player : GUI.instance().getModel().getPlayers().values()) {
            if (player.equals(GUI.instance().getModel().getLocalPlayer()))
                continue;
            players.add(player);
        }

        int row = 3;

        //creates the buttons used to view the other players' school boards
        for (int i = 0; i < GUI.instance().getNumPlayers() - 1; i++) {
            Button button = new Button();
            button.setText(players.get(i).getNickname() + "'s school board");
            int a = i;
            button.setOnMouseClicked(event -> Platform.runLater(() -> GUI.instance().showOtherPlayerBoard(players.get(a))));
            gridPane.add(button, 0, row);
            button.getStyleClass().add("game-button");
            row++;
        }

        //Only if the game mode is expert the buttons "Character Cards" and coin image are created
        if (GUI.instance().getGameMode()) {
            Button button = new Button();
            button.setText("Character Cards");
            button.getStyleClass().add("game-button");
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

            //Adds a listener to the number of coins of the player in order to update it
            GUI.instance().getModel().getLocalPlayer().getCoinsProperty().addListener((change, oldVal, newVal) ->
                    Platform.runLater(() -> numberCoins.setText(String.valueOf(GUI.instance().getModel().getLocalPlayer().getCoins())))
            );
        }

        initCurrentAssistantCard();
        initEntrance();
        initDiningRoom();
        initProfessorsTable();
        initTowers();
        initWinner();
    }

    /**
     * Shows the group islands
     */
    @FXML
    private void goToIsland() {
        GUI.instance().showIslands();
    }

    /**
     * Shows the assistant cards of the deck
     */
    @FXML
    private void goToAssistantCards() {
        GUI.instance().getModel().setPosition(-1);
        GUI.instance().getModel().setSelectedColour(null);

        GUI.instance().showAssistantCards();
    }

    /**
     * Saves in the mockModel the position and the colour of the student selected in the entrance
     *
     * @param i      the position of the student selected in the list
     * @param colour the colour of the student selected
     */
    @FXML
    private void selectStudentFromEntrance(int i, Colour colour) {
        GUI.instance().getModel().setPosition(i);
        GUI.instance().getModel().setSelectedColour(colour);
        diningRoom.setOnMouseClicked(event -> moveStudentToDiningRoom());
    }

    /**
     * Saves in the mockModel the position and the colour of the student of the entrance that the player wants to exchange when the student_to_entrance card is played
     *
     * @param i      the position of the student selected in the entrance
     * @param colour the colour of the student selected in the entrance
     */
    @FXML
    private void selectStudentToExchange(int i, Colour colour) {
        GUI.instance().getModel().setPosition(i);
        GUI.instance().getModel().setSelectedColour(colour);
        GUI.instance().getActionSender().setColourCardEntrance(GUI.instance().getPlayerName(), GUI.instance().getModel().getStudentOnCardSelected(), GUI.instance().getModel().getSelectedColour());
        GUI.instance().getModel().setStudentOnCardSelected(null);
    }

    /**
     * Moves the student selected from the entrance to the dining room. Then resets the position and the colour selected
     */
    @FXML
    private void moveStudentToDiningRoom() {
        if (GUI.instance().getModel().getSelectedColour() != null) {
            GUI.instance().getActionSender().moveStudentToDiningRoom(GUI.instance().getPlayerName(), GUI.instance().getModel().getSelectedColour());
            GUI.instance().getModel().setPosition(-1);
            GUI.instance().getModel().setSelectedColour(null);
            diningRoom.setOnMouseClicked(event -> noAction());
        } else {
            GUI.instance().getRenderer().showErrorMessage("Select a student");
        }
    }

    @FXML
    private void noAction() {
    }

    /**
     * Initializes and updates the entrance grid that contains the images of the students
     */
    private void initEntrance() {
        //init the entrance
        initEntranceArrays();

        //If a student has been selected then it is highlighted
        if (GUI.instance().getModel().getPosition().getValue() != -1) {
            entrance.getChildren().get(GUI.instance().getModel().getPosition().getValue()).getStyleClass().add("studentSelected");
        }

        //Adds a listener to the entrance in order to update it
        GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getEntranceProperty().addListener((MapChangeListener<? super Colour, ? super Integer>) listener ->
                Platform.runLater(() -> {
                    entrance.getChildren().forEach(node -> node.setVisible(false));
                    entrance.getChildren().clear();
                    initEntranceArrays();
                }));

        //Adds a listener to the position in order to know if the player has selected a student
        GUI.instance().getModel().getPosition().addListener((change, oldVal, newVal) -> {
            //If a student was selected by mistake (so the player has selected another one) the old one is not highlighted anymore
            if (oldVal.intValue() != -1 && oldVal.intValue() < entrance.getChildren().size()) {
                entrance.getChildren().get(oldVal.intValue()).getStyleClass().removeAll("studentSelected");
            }

            //The new selected student is highlighted
            if (newVal.intValue() != -1 && newVal.intValue() < entrance.getChildren().size()) {
                entrance.getChildren().get(newVal.intValue()).getStyleClass().add("studentSelected");
            }
        });

    }

    /**
     * Creates an array containing all the students of the entrance ordered by colour
     */
    private void initEntranceArrays() {
        List<Colour> entranceStudents = new ArrayList<>();
        for (Colour colour : Colour.values()) {
            for (int i = 0; i < GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getEntrance().get(colour); i++) {
                entranceStudents.add(colour);
            }
        }

        setStudentEntrance(entranceStudents);
    }

    /**
     * Initializes and updates the dining room
     */
    private void initDiningRoom() {
        initDiningRoomImage();

        diningRoom.getStyleClass().add("diningRoom");

        //Adds a listener to the dining room
        GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getDiningRoomProperty().addListener((MapChangeListener<? super Colour, ? super Integer>) listener ->
                Platform.runLater(() -> {
                    diningRoom.getChildren().removeAll(diningRoomImage);
                    diningRoomImage.clear();
                    initDiningRoomImage();
                }));
    }

    /**
     * Shows the students in the right places on the dining room
     */
    private void initDiningRoomImage() {
        for (Colour colour : Colour.values()) {
            for (int i = 0; i < GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getDiningRoom().get(colour); i++) {
                ImageView imageView = new ImageView();
                diningRoomImage.add(imageView);
                diningRoom.add(imageView, i, getDiningRoomTable(colour));
                imageView.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                        "/images/students/student_" + colour.name().toLowerCase(Locale.ROOT) + ".png"))));

            }
        }

        //Adds a listener to the current character card so that if the card is student_to_dining_room the dining room is clickable even if no student has been selected in the entrance
        GUI.instance().getModel().currentCharacterCardProperty().addListener((ChangeListener<? super MockCard>) (change, oldVal, newVal) ->
                Platform.runLater(() -> {
                    if (newVal.getType() == CharacterCardEnumeration.STUDENT_TO_DINING_ROOM) {
                        diningRoom.setOnMouseClicked(event -> GUI.instance().getActionSender().setColour(GUI.instance().getPlayerName(), GUI.instance().getModel().getStudentOnCardSelected()));
                    }
                }));
    }


    /**
     * Gets the position of the given colour on the dining room grid
     *
     * @param colour the colour of which the position is requested
     * @return the row of the dining room grid in which the colour given has to be placed
     */
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

    /**
     * Places the student images in the entrance grid
     *
     * @param entranceStudents the list of students contained in the entrance
     */
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

            //Add a listener to the studentOnCardSelected
            GUI.instance().getModel().getStudentOnCardSelectedProperty().addListener((ChangeListener<? super Colour>) (change, oldVal, newVal) ->
                    Platform.runLater(() -> {
                                //If the card is Student_To_Entrance and the current player has selected a student on the card, the student will be exchanged with the one selected in the entrance
                                if (newVal != null && GUI.instance().getModel().getCurrentCharacterCard().getType() == CharacterCardEnumeration.STUDENT_TO_ENTRANCE) {
                                    imageView.setOnMouseClicked(event -> {
                                        flowPane.getStyleClass().add("studentSelected");
                                        selectStudentToExchange(a, colour);
                                    });
                                } else {
                                    imageView.setOnMouseClicked(event -> {
                                        flowPane.getStyleClass().add("studentSelected");
                                        selectStudentFromEntrance(a, colour);
                                    });
                                }
                            }
                    ));

        }
    }

    /**
     * Initializes and updates the professor table
     */
    private void initProfessorsTable() {
        initProfessorTableImage();

        //Adds a listener to the professor table
        GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getProfessorTableProperty().addListener((MapChangeListener<? super Colour, ? super Boolean>) listener ->
                Platform.runLater(() -> {
                    professorsTable.getChildren().removeAll(professorImage);
                    professorImage.clear();
                    initProfessorTableImage();
                }));
    }

    /**
     * Places the images of the professors in the right places
     */
    private void initProfessorTableImage() {
        for (Colour colour : Colour.values()) {
            if (GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getProfessorTable().get(colour)) {
                ImageView imageView = new ImageView();
                professorImage.add(imageView);
                professorsTable.add(imageView, 0, getDiningRoomTable(colour));
                imageView.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                        "/images/professors/teacher_" + colour.name().toLowerCase(Locale.ROOT) + ".png")), 40, 40, false, false));

            }
        }
    }

    /**
     * Initializes and updates the assistant card that has been played by the player for this round
     */
    private void initCurrentAssistantCard() {
        setCurrentAssistantCard();

        GUI.instance().getModel().getLocalPlayer().isAssistantCardValueProperty().addListener((change, oldVal, newVal) -> Platform.runLater(this::setCurrentAssistantCard));
    }

    /**
     * Sets the image of the assistant card that has been played by the player for this round
     */
    private void setCurrentAssistantCard() {
        if (GUI.instance().getModel().getLocalPlayer().isAssistantCardValue()) {
            currentAssistantCard.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                    "/images/assistantCards/assistant_" + (GUI.instance().getModel().getLocalPlayer().getCurrentAssistantCardProperty().getValue().getValue() - 1) + ".png"))));
        } else {
            currentAssistantCard.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                    "/images/wizard/wizard" + (Wizard.getWizardCode(GUI.instance().getModel().getLocalPlayer().getWizard())) + ".png"))));
        }
    }

    /**
     * Initializes and updates the towers that are still on the player's school board
     */
    private void initTowers() {
        initTowerImage();

        GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getTowersProperty().addListener((change, oldVal, newVal) ->
                Platform.runLater(() -> {
                    towers.getChildren().removeAll(towersImage);
                    towersImage.clear();
                    initTowerImage();
                }));
    }

    /**
     * Places the tower images on the player's school board
     */
    private void initTowerImage() {
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

    /**
     * A method which checks if the game has ended (if the turn phase is ENDGAME) and, if so, calls the method that shows the winner
     */
    private void initWinner() {

        if (GUI.instance().getModel().getTurnPhase().equals(TurnPhase.ENDGAME)) {
            printWinner();
        }

        //Adds a listener to the turn phase in order to know if the game has ended
        GUI.instance().getModel().getTurnPhaseProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {

            if (GUI.instance().getModel().getTurnPhase().equals(TurnPhase.ENDGAME)) {
                printWinner();
            }
        }));
    }

    /**
     * The layout of the images that appear once the game has ended to show who is the winner
     */
    private void printWinner() {
        if (GUI.instance().getModel().getWinner().getNickname().equals(GUI.instance().getPlayerName())) {
            ImageView winner = new ImageView();
            anchorPane.getChildren().add(winner);
            winner.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                    "/images/winner.png"))));
            winner.setLayoutY(153);
            winner.setLayoutX(263);
            winner.setFitWidth(547);
            winner.setFitHeight(395);
        } else {
            ImageView loser = new ImageView();
            anchorPane.getChildren().add(loser);
            loser.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                    "/images/loser2.png"))));
            loser.setLayoutY(171);
            loser.setLayoutX(74);
            loser.setFitWidth(934);
            loser.setFitHeight(290);
            Label winnerName = new Label();
            winnerName.setText(GUI.instance().getModel().getWinner().getNickname());
            winnerName.setStyle("-fx-font: 96 Stsyem;");
            winnerName.setLayoutX(671);
            winnerName.setLayoutY(257);
            anchorPane.getChildren().add(winnerName);
        }
    }

    /**
     * Shows the cloud tiles page
     */
    @FXML
    public void goToCloudTile() {

        GUI.instance().getModel().setPosition(-1);
        GUI.instance().getModel().setSelectedColour(null);

        GUI.instance().showCloudTile();
    }

    /**
     * Button used to change the mode from guided to normal and vice versa
     */
    @FXML
    public void changeGuideMode() {
        if (GUI.instance().isGuidedMode()) {
            guidedModeButton.setText("Enable Guide");
            GUI.instance().setGuidedMode(false);
        } else {
            guidedModeButton.setText("Disable Guide");
            GUI.instance().setGuidedMode(true);
        }
    }


}
