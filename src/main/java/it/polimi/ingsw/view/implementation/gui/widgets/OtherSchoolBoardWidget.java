package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.player.TowerColour;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.beans.MockPlayer;
import it.polimi.ingsw.view.implementation.gui.GUI;
import it.polimi.ingsw.view.implementation.gui.widgets.utils.Coordinates;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.*;

/**
 * Widget that shows the school board of the other players
 */
public class OtherSchoolBoardWidget extends StackPane {

    /**
     * The player of whom the school board belongs
     */
    @FXML
    private final MockPlayer player;

    /**
     * The nickname of the player who owns the school board
     */
    @FXML
    private Label nicknameLabel;

    /**
     * The grid containing the entrance of the school board
     */
    @FXML
    private GridPane entrance;

    /**
     * The grid containing the dining room of the school board
     */
    @FXML
    private GridPane diningRoom;

    /**
     * The grid containing the professor table of the school board
     */
    @FXML
    private GridPane professorsTable;

    /**
     * The current assistant card played by the player that owns the school board
     */
    @FXML
    private ImageView currentAssistantCard;

    /**
     * The grid containing the towers of the player
     */
    @FXML
    private GridPane towersOtherPlayer;

    /**
     * The main anchor pane
     */
    @FXML
    private AnchorPane anchorPane;

    /**
     * The list containing the towers of the player who owns the school board
     */
    private final List<Circle> towersImage = new ArrayList<>();

    /**
     * The list containing the professors images of the player who owns the school board
     */
    private final List<ImageView> professorsImage = new ArrayList<>();

    /**
     * The list containing the images of the students on the dining room of the player who owns the school board
     */
    private final List<ImageView> diningRoomImages = new ArrayList<>();

    /**
     * The list of coordinates of the students in the entrance
     */
    private List<Coordinates> entranceBoxes;

    /**
     * Contructs OtherSchoolBoardWidget
     *
     * @param player the player that owns the school board
     */
    public OtherSchoolBoardWidget(MockPlayer player) {
        this.player = player;
        FXMLUtils.loadWidgetFXML(this);
    }

    /**
     * Init method called by the FXMLLoader
     */
    @FXML
    private void initialize() {
        nicknameLabel.setText(player.getNickname());
        //The list containing the coordinates of the entrance grid in which the students must be placed
        entranceBoxes = new ArrayList<>(Arrays.asList(new Coordinates(2, 0), new Coordinates(0, 2), new Coordinates(2, 2), new Coordinates(0, 4), new Coordinates(2, 4), new Coordinates(0, 6), new Coordinates(2, 6), new Coordinates(0, 8), new Coordinates(2, 8)));
        initCurrentAssistantCard();
        initEntrance();
        initDiningRoom();
        initProfessorsTable();

        if (GUI.instance().getGameMode()) {
            ImageView coins = new ImageView();
            anchorPane.getChildren().add(coins);
            coins.setFitWidth(87);
            coins.setFitHeight(100);
            coins.setLayoutX(901);
            coins.setLayoutY(66);
            coins.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                    "/images/Moneta_base.png"))));
            Label numberCoins = new Label();
            numberCoins.setLayoutX(1000);
            numberCoins.setLayoutY(100);
            numberCoins.setStyle("-fx-font: 22 System;");
            anchorPane.getChildren().add(numberCoins);

            numberCoins.setText(String.valueOf(player.getCoins()));
            player.getCoinsProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> numberCoins.setText(String.valueOf(player.getCoins()))));
        }
        initTowers();
    }

    /**
     * This method is called when the button "GO BACK TO YOUR SCHOOL BOARD" is clicked and it brings the player back to his/her own school board
     */
    @FXML
    public void showSchoolBoard() {
        GUI.instance().showPlayerBoard();
    }

    /**
     * Initializes and updates the entrance of the other player's school board
     */
    private void initEntrance() {
        //init the entrance
        List<Colour> entranceStudents = new ArrayList<>();
        for (Colour colour : Colour.values()) {
            for (int i = 0; i < player.getSchoolBoard().getEntrance().get(colour); i++) {
                entranceStudents.add(colour);
            }
        }

        entranceUpdate(entranceStudents);

        //adds a listener to the other players' entrance
        player.getSchoolBoard().getEntranceProperty().addListener((MapChangeListener<? super Colour, ? super Integer>) listener ->
                Platform.runLater(() -> {
                    entrance.getChildren().forEach(node -> node.setVisible(false));
                    entrance.getChildren().removeAll();
                    List<Colour> students = new ArrayList<>();
                    for (Colour colour : Colour.values()) {
                        for (int i = 0; i < player.getSchoolBoard().getEntrance().get(colour); i++) {
                            students.add(colour);
                        }
                    }

                    entranceUpdate(students);
                }));
    }

    /**
     * Places the images of the students in the entrance grid
     *
     * @param entranceStudents the list containing the colours of all the students that are in the entrance of the other player
     */
    private void entranceUpdate(List<Colour> entranceStudents) {
        for (int i = 0; i < entranceStudents.size(); i++) {
            ImageView imageView = new ImageView();
            entrance.add(imageView, entranceBoxes.get(i).getRow(), entranceBoxes.get(i).getColumn());
            imageView.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                    "/images/students/student_" + entranceStudents.get(i).name().toLowerCase(Locale.ROOT) + ".png"))));
        }
    }

    /**
     * Initializes and updates the dining room of the other player's school board
     */
    private void initDiningRoom() {

        diningRoomUpdate();

        player.getSchoolBoard().getDiningRoomProperty().addListener((MapChangeListener<? super Colour, ? super Integer>) listener ->
                Platform.runLater(() -> {
                    diningRoom.getChildren().removeAll(diningRoomImages);
                    diningRoomImages.clear();
                    diningRoomUpdate();
                }));
    }

    /**
     * Places the images of the students in the dining room grid
     */
    private void diningRoomUpdate() {
        for (Colour colour : Colour.values()) {
            for (int i = 0; i < player.getSchoolBoard().getDiningRoom().get(colour); i++) {
                ImageView imageView = new ImageView();
                diningRoomImages.add(imageView);
                diningRoom.add(imageView, i, getDiningRoomTable(colour));
                imageView.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                        "/images/students/student_" + colour.name().toLowerCase(Locale.ROOT) + ".png"))));

            }
        }
    }

    /**
     * Gets the position of the professor of the given colour on the professor table grid
     *
     * @param colour the colour of the professor of which the position in requested
     * @return the index of the row of the professor table grid where the professor of the colour given must be placed
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
     * Initializes and updates the professor table of the other player's school board
     */
    private void initProfessorsTable() {
        professorUpdate();

        //adds a listener to the professor table of the other player
        player.getSchoolBoard().getProfessorTableProperty().addListener((MapChangeListener<? super Colour, ? super Boolean>) listener ->
                Platform.runLater(() -> {
                    professorsTable.getChildren().removeAll(professorsImage);
                    professorsImage.clear();
                    professorUpdate();
                }));
    }

    /**
     * Places the images of professors in the professor table grid
     */
    private void professorUpdate() {
        for (Colour colour : Colour.values()) {
            if (player.getSchoolBoard().getProfessorTable().get(colour)) {
                ImageView imageView = new ImageView();
                professorsImage.add(imageView);
                professorsTable.add(imageView, 0, getDiningRoomTable(colour));
                imageView.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                        "/images/professors/teacher_" + colour.name().toLowerCase(Locale.ROOT) + ".png")), 40, 40, false, false));

            }
        }
    }

    /**
     * Initializes and updates the assistant card that has been played by the other player for this round
     */
    private void initCurrentAssistantCard() {
        setCurrentAssistantCard();

        player.getCurrentAssistantCardProperty().addListener((change, oldVal, newVal) -> Platform.runLater(this::setCurrentAssistantCard));
    }

    /**
     * Sets the image of the assistant card that has been played by the other player for this round
     */
    private void setCurrentAssistantCard() {
        if (player.isAssistantCardValue()) {
            currentAssistantCard.setImage(new Image(Objects.requireNonNull(AssistantCardsWidget.class.getResourceAsStream(
                    "/images/assistantCards/assistant_" + (player.getCurrentAssistantCardProperty().getValue().getValue() - 1) + ".png"))));
        } else {
            currentAssistantCard.setImage(new Image(Objects.requireNonNull(AssistantCardsWidget.class.getResourceAsStream(
                    "/images/wizard/wizard" + (Wizard.getWizardCode(player.getWizard())) + ".png"))));
        }
    }

    /**
     * Initializes and updates the towers that are still on the other player's school board
     */
    private void initTowers() {
        initTowerImage();

        player.getSchoolBoard().getTowersProperty().addListener((change, oldVal, newVal) ->
                Platform.runLater(() -> {
                    towersOtherPlayer.getChildren().removeAll(towersImage);
                    towersImage.clear();
                    initTowerImage();
                }));
    }

    /**
     * Places the tower images on the other player's school board
     */
    private void initTowerImage() {
        int j = 0;
        int k = 0;

        for (int i = 0; i < player.getSchoolBoard().getTowers(); i++) {
            Circle tower = new Circle();
            towersImage.add(tower);
            tower.setRadius(24);
            if (player.getTowerColour() == TowerColour.WHITE) {
                tower.setFill(Color.rgb(255, 255, 255));
            } else if (player.getTowerColour() == TowerColour.BLACK) {
                tower.setFill(Color.rgb(0, 0, 0));
            } else if (player.getTowerColour() == TowerColour.GREY) {
                tower.setFill(Color.rgb(179, 179, 179));
            }

            towersOtherPlayer.add(tower, j, k);

            if (j == 0) {
                j += 2;
            } else {
                j = 0;
                k += 2;
            }
        }
    }
}