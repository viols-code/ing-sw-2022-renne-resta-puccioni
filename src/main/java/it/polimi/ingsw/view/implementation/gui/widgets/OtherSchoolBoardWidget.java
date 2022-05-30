package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.beans.MockPlayer;
import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

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

        for (int i = 0; i < entranceStudents.size(); i++) {
            ImageView imageView = new ImageView();
            entrance.add(imageView, entranceBoxes.get(i).getRow(), entranceBoxes.get(i).getColumn());
            imageView.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                    "/images/students/student_" + entranceStudents.get(i).name().toLowerCase(Locale.ROOT) + ".png"))));
        }

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

                    for (int i = 0; i < students.size(); i++) {
                        ImageView imageView = new ImageView();
                        entrance.add(imageView, entranceBoxes.get(i).getRow(), entranceBoxes.get(i).getColumn());
                        imageView.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                                "/images/students/student_" + students.get(i).name().toLowerCase(Locale.ROOT) + ".png"))));
                    }
                }));
    }

    private void initDiningRoom() {
        for (Colour colour : Colour.values()) {
            for (int i = 0; i < player.getSchoolBoard().getDiningRoom().get(colour); i++) {
                ImageView imageView = new ImageView();
                diningRoom.add(imageView, i, getDiningRoomTable(colour));
                imageView.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                        "/images/students/student_" + colour + ".png"))));

            }
        }

        player.getSchoolBoard().getDiningRoomProperty().addListener((MapChangeListener<? super Colour, ? super Integer>) listener ->
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

    private void initProfessorsTable() {
        for (Colour colour : Colour.values()) {
            if (player.getSchoolBoard().getProfessorTable().get(colour)) {
                ImageView imageView = new ImageView();
                professorsTable.add(imageView, 0, getDiningRoomTable(colour));
                imageView.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                        "/images/professors/teacher_" + colour + ".png")), 40, 40, false, false));

            }
        }

        player.getSchoolBoard().getProfessorTableProperty().addListener((MapChangeListener<? super Colour, ? super Boolean>) listener ->
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
        if (player.getCurrentAssistantCardProperty().getValue() != null) {
            currentAssistantCard.setImage(new Image(Objects.requireNonNull(AssistantCardsWidget.class.getResourceAsStream(
                    "/images/assistantCards/assistant_" + (player.getCurrentAssistantCardProperty().getValue().getValue() - 1) + ".png"))));
        } else {
            currentAssistantCard.setImage(new Image(Objects.requireNonNull(AssistantCardsWidget.class.getResourceAsStream(
                    "/images/wizard/wizard" + (Wizard.getWizardCode(player.getWizard())) + ".png"))));
        }
        player.getCurrentAssistantCardProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {
            if (player.getCurrentAssistantCardProperty().getValue() != null) {
                currentAssistantCard.setImage(new Image(Objects.requireNonNull(AssistantCardsWidget.class.getResourceAsStream(
                        "/images/assistantCards/assistant_" + (player.getCurrentAssistantCardProperty().getValue().getValue() - 1) + ".png"))));
            } else {
                currentAssistantCard.setImage(new Image(Objects.requireNonNull(AssistantCardsWidget.class.getResourceAsStream(
                        "/images/wizard/wizard" + (Wizard.getWizardCode(player.getWizard())) + ".png"))));
            }
        }));
    }
}
