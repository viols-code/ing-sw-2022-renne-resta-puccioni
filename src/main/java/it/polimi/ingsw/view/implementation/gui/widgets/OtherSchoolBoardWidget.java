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

public class OtherSchoolBoardWidget extends StackPane {

    @FXML
    private final MockPlayer player;

    @FXML
    private Label nicknameLabel;

    @FXML
    private GridPane entrance;

    @FXML
    private GridPane diningRoom;

    @FXML
    private GridPane professorsTable;

    @FXML
    private ImageView currentAssistantCard;

    @FXML
    private GridPane towersOtherPlayer;

    @FXML
    private AnchorPane anchorPane;

    private final List<Circle> towersImage = new ArrayList<>();

    private final List<ImageView> professorsImage = new ArrayList<>();

    private final List<ImageView> diningRoomImages = new ArrayList<>();

    private List<Coordinates> entranceBoxes;

    public OtherSchoolBoardWidget(MockPlayer player) {
        this.player = player;
        FXMLUtils.loadWidgetFXML(this);
    }

    @FXML
    private void initialize() {
        nicknameLabel.setText(player.getNickname());
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

    @FXML
    public void showSchoolBoard() {
        GUI.instance().showPlayerBoard();
    }

    private void initEntrance() {
        //init the entrance
        List<Colour> entranceStudents = new ArrayList<>();
        for (Colour colour : Colour.values()) {
            for (int i = 0; i < player.getSchoolBoard().getEntrance().get(colour); i++) {
                entranceStudents.add(colour);
            }
        }

        entranceUpdate(entranceStudents);

        player.getSchoolBoard().getEntranceProperty().addListener((MapChangeListener<? super Colour, ? super Integer>) listener ->
                Platform.runLater(() -> {
                    entrance.getChildren().forEach(node -> node.setVisible(false));
                    entrance.getChildren().removeAll();
                    List<Colour> students = new ArrayList<>();
                    for (Colour colour : Colour.values()) {
                        for (int i = 0; i < GUI.instance().getModel().getLocalPlayer().getSchoolBoard().getEntrance().get(colour); i++) {
                            students.add(colour);
                        }
                    }

                    entranceUpdate(students);
                }));
    }

    private void entranceUpdate(List<Colour> entranceStudents) {
        for (int i = 0; i < entranceStudents.size(); i++) {
            ImageView imageView = new ImageView();
            entrance.add(imageView, entranceBoxes.get(i).getRow(), entranceBoxes.get(i).getColumn());
            imageView.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                    "/images/students/student_" + entranceStudents.get(i).name().toLowerCase(Locale.ROOT) + ".png"))));
        }
    }

    private void initDiningRoom() {

        diningRoomUpdate();

        player.getSchoolBoard().getDiningRoomProperty().addListener((MapChangeListener<? super Colour, ? super Integer>) listener ->
                Platform.runLater(() -> {
                    diningRoom.getChildren().removeAll(diningRoomImages);
                    diningRoomImages.clear();
                    diningRoomUpdate();
                }));
    }

    private void diningRoomUpdate() {
        for (Colour colour : Colour.values()) {
            for (int i = 0; i < player.getSchoolBoard().getDiningRoom().get(colour); i++) {
                ImageView imageView = new ImageView();
                diningRoomImages.add(imageView);
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

    private void initProfessorsTable() {
        professorUpdate();

        player.getSchoolBoard().getProfessorTableProperty().addListener((MapChangeListener<? super Colour, ? super Boolean>) listener ->
                Platform.runLater(() -> {
                    professorsTable.getChildren().removeAll(professorsImage);
                    professorsImage.clear();
                    professorUpdate();
                }));
    }

    private void professorUpdate() {
        for (Colour colour : Colour.values()) {
            if (player.getSchoolBoard().getProfessorTable().get(colour)) {
                ImageView imageView = new ImageView();
                professorsImage.add(imageView);
                professorsTable.add(imageView, 0, getDiningRoomTable(colour));
                imageView.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                        "/images/professors/teacher_" + colour + ".png")), 40, 40, false, false));

            }
        }
    }

    private void initCurrentAssistantCard() {
        setCurrentAssistantCard();

        player.getCurrentAssistantCardProperty().addListener((change, oldVal, newVal) -> Platform.runLater(this::setCurrentAssistantCard));
    }

    private void setCurrentAssistantCard() {
        if (player.isAssistantCardValue()) {
            currentAssistantCard.setImage(new Image(Objects.requireNonNull(AssistantCardsWidget.class.getResourceAsStream(
                    "/images/assistantCards/assistant_" + (player.getCurrentAssistantCardProperty().getValue().getValue() - 1) + ".png"))));
        } else {
            currentAssistantCard.setImage(new Image(Objects.requireNonNull(AssistantCardsWidget.class.getResourceAsStream(
                    "/images/wizard/wizard" + (Wizard.getWizardCode(player.getWizard())) + ".png"))));
        }
    }

    private void initTowers() {
        initTowerImage();

        player.getSchoolBoard().getTowersProperty().addListener((change, oldVal, newVal) ->
                Platform.runLater(() -> {
                    towersOtherPlayer.getChildren().removeAll(towersImage);
                    towersImage.clear();
                    initTowerImage();
                }));
    }

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