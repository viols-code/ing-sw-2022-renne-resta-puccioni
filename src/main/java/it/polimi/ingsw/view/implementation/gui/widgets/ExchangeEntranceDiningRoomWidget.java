package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.model.player.TowerColour;
import it.polimi.ingsw.view.implementation.gui.GUI;
import it.polimi.ingsw.view.implementation.gui.widgets.utils.Coordinates;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
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

public class ExchangeEntranceDiningRoomWidget extends StackPane {
    /**
     * The main anchor pane
     */
    @FXML
    private AnchorPane anchorPane;

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
     * The list containing the green students of the dining room
     */
    @FXML
    private final List<ImageView> diningRoomGreen = new ArrayList<>();
    /**
     * The list containing the red students of the dining room
     */
    @FXML
    private final List<ImageView> diningRoomRed = new ArrayList<>();
    /**
     * The list containing the pink students of the dining room
     */
    @FXML
    private final List<ImageView> diningRoomPink = new ArrayList<>();
    /**
     * The list containing the yellow students of the dining room
     */
    @FXML
    private final List<ImageView> diningRoomYellow = new ArrayList<>();
    /**
     * The list containing the blue students of the dining room
     */
    @FXML
    private final List<ImageView> diningRoomBlue = new ArrayList<>();

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
    private final List<FlowPane> diningRoomImage = new ArrayList<>();


    /**
     * Creates a ExchangeEntranceDiningRoomWidget
     */
    public ExchangeEntranceDiningRoomWidget() {
        FXMLUtils.loadWidgetFXML(this);
    }

    /**
     * Init method called by the FXMLLoader
     */
    @FXML
    private void initialize() {
        //The list containing the coordinates of the entrance grid in which the students must be placed
        entranceBoxes = new ArrayList<>(Arrays.asList(new Coordinates(2, 0), new Coordinates(0, 2), new Coordinates(2, 2), new Coordinates(0, 4), new Coordinates(2, 4), new Coordinates(0, 6), new Coordinates(2, 6), new Coordinates(0, 8), new Coordinates(2, 8)));

        initEntrance();
        initDiningRoom();
        initProfessorsTable();
        initTowers();
        initWinner();
    }

    /**
     * Saves in the mockModel the position and the colour of the student selected in the entrance for the exchange when the exchange_entrance_diningRoom card is played
     *
     * @param i      the position of the student selected in the list
     * @param colour the colour of the student selected
     */
    @FXML
    private void selectStudentFromEntrance(int i, Colour colour) {
        GUI.instance().getModel().setPositionExchange(i);
        GUI.instance().getModel().setSelectedColourExchange(colour);

        setClickableDiningRoom(diningRoomBlue, Colour.BLUE);
        setClickableDiningRoom(diningRoomGreen, Colour.GREEN);
        setClickableDiningRoom(diningRoomYellow, Colour.YELLOW);
        setClickableDiningRoom(diningRoomRed, Colour.RED);
        setClickableDiningRoom(diningRoomPink, Colour.PINK);
    }

    /**
     * Set the method called when a dining room's student is clicked to chooseColour
     *
     * @param diningRoomRow the list of ImageView
     * @param colour        the given colour
     */
    private void setClickableDiningRoom(List<ImageView> diningRoomRow, Colour colour) {
        for (ImageView image : diningRoomRow) {
            image.setOnMouseClicked(event -> chooseColour(colour));
        }
    }

    /**
     * Set the method called when a dining room's student is clicked to no action
     *
     * @param diningRoomRow the list of ImageView
     */
    private void setNonClickableDiningRoom(List<ImageView> diningRoomRow) {
        for (ImageView image : diningRoomRow) {
            image.setOnMouseClicked(event -> {
            });
        }
    }

    /**
     * Choose the colour form the dining room
     *
     * @param colour the colour chosen from the dining room
     */
    private void chooseColour(Colour colour) {
        if (GUI.instance().getModel().getPositionExchange().getValue() != -1 && GUI.instance().getModel().getSelectedColourExchange() != null) {
            GUI.instance().getActionSender().setColourDiningRoomEntrance(GUI.instance().getPlayerName(), colour, GUI.instance().getModel().getSelectedColourExchange());
            GUI.instance().getModel().setPositionExchange(-1);
            GUI.instance().getModel().setSelectedColourExchange(null);

            setNonClickableDiningRoom(diningRoomBlue);
            setNonClickableDiningRoom(diningRoomGreen);
            setNonClickableDiningRoom(diningRoomYellow);
            setNonClickableDiningRoom(diningRoomRed);
            setNonClickableDiningRoom(diningRoomPink);
        }
    }

    /**
     * Initializes and updates the entrance grid that contains the images of the students
     */
    private void initEntrance() {
        //init the entrance
        initEntranceArrays();

        //If a student has been selected then it is highlighted
        if (GUI.instance().getModel().getPositionExchange().getValue() != -1) {
            entrance.getChildren().get(GUI.instance().getModel().getPositionExchange().getValue()).getStyleClass().add("studentSelected");
        }

        //Adds a listener to the entrance in order to update it
        GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getEntranceProperty().addListener((MapChangeListener<? super Colour, ? super Integer>) listener ->
                Platform.runLater(() -> {
                    entrance.getChildren().forEach(node -> node.setVisible(false));
                    entrance.getChildren().clear();
                    initEntranceArrays();
                }));

        //Adds a listener to the position in order to know if the player has selected a student
        GUI.instance().getModel().getPositionExchange().addListener((change, oldVal, newVal) -> {
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
        diningRoomYellow.clear();
        diningRoomBlue.clear();
        diningRoomGreen.clear();
        diningRoomPink.clear();
        diningRoomRed.clear();

        for (Colour colour : Colour.values()) {
            for (int i = 0; i < GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getDiningRoom().get(colour); i++) {
                ImageView imageView = new ImageView();
                FlowPane flowPane = new FlowPane();
                flowPane.getChildren().add(imageView);
                flowPane.getStyleClass().add("student");
                diningRoomImage.add(flowPane);
                diningRoom.add(flowPane, i, getDiningRoomTable(colour));
                imageView.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                        "/images/students/student_" + colour.name().toLowerCase(Locale.ROOT) + ".png"))));

                switch (colour) {
                    case YELLOW -> diningRoomYellow.add(imageView);
                    case BLUE -> diningRoomBlue.add(imageView);
                    case GREEN -> diningRoomGreen.add(imageView);
                    case RED -> diningRoomRed.add(imageView);
                    case PINK -> diningRoomPink.add(imageView);
                }

            }
        }

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
     * Returns to the CharacterCard page.
     */
    public void goToCharacterCard() {
        GUI.instance().getModel().setPositionExchange(-1);
        GUI.instance().getModel().setSelectedColourExchange(null);
        GUI.instance().showCharacterCards();
    }

}
