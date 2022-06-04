package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;
import it.polimi.ingsw.view.beans.MockCard;
import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CharacterCardsWidget extends StackPane {
    @FXML
    private HBox box;

    @FXML
    private GridPane studentsOnCard0;

    @FXML
    private GridPane studentsOnCard1;

    @FXML
    private GridPane studentsOnCard2;

    @FXML
    private Label numberCoinsTable;

    @FXML
    private Label costCard0;

    @FXML
    private Label costCard1;

    @FXML
    private Label costCard2;

    private final List<ImageView> imageViewList = new ArrayList<>();

    public CharacterCardsWidget() {
        FXMLUtils.loadWidgetFXML(this);
    }

    @FXML
    private void initialize() {
        for (int i = 0; i < 3; i++) {
            FlowPane flowPane = new FlowPane();
            box.getChildren().add(flowPane);
            flowPane.getStyleClass().add("assistantCards");
            flowPane.setMaxHeight(300.0);
            flowPane.setMaxWidth(196.5);
            ImageView imageView = new ImageView();
            flowPane.getChildren().add(imageView);

            imageView.setFitHeight(300.0);
            imageView.setFitWidth(196.5);

            int a = i;
            imageView.setOnMouseClicked(event -> playCharacterCard(a));
            imageViewList.add(imageView);
            HBox.setMargin(flowPane, new Insets(5.0, 5.0, 5.0, 5.0));


            imageView.setImage(new Image(Objects.requireNonNull(CharacterCardsWidget.class.getResourceAsStream(
                    "/images/characterCards/" + GUI.instance().getModel().getCharacterCardByIndex(i).getType().name() + ".jpg"))));

            int c, r;
            c = 0;
            r = 0;

            if (GUI.instance().getModel().getCharacterCardByIndex(i).getNumberOfStudentsOnTheCard() > 0) {
                for (Colour colour : Colour.values()) {
                    for (int j = 0; j < GUI.instance().getModel().getCharacterCardByIndex(i).getStudents().get(colour); j++) {
                        FlowPane pane = new FlowPane();
                        pane.getStyleClass().add("student");
                        ImageView imageViewStudent = new ImageView();
                        pane.getChildren().add(imageViewStudent);
                        imageViewStudent.setImage(new Image(Objects.requireNonNull(CharacterCardsWidget.class.getResourceAsStream(
                                "/images/students/student_" + colour.name().toLowerCase(Locale.ROOT) + ".png"))));
                        imageViewStudent.setOnMouseClicked(event -> setStudent(colour));
                        if (i == 0) {
                            studentsOnCard0.add(pane, r, c);
                        } else if (i == 1) {
                            studentsOnCard1.add(pane, r, c);
                        } else {
                            studentsOnCard2.add(pane, r, c);
                        }
                        if (r == 2) {
                            c += 2;
                            r = 0;
                        } else {
                            r = 2;
                        }
                    }
                }
            }
        }

        costCard0.setText(String.valueOf(GUI.instance().getModel().getCharacterCardByIndex(0).getCost()));
        GUI.instance().getModel().getCharacterCardByIndex(0).getCostProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> costCard0.setText(String.valueOf(GUI.instance().getModel().getCharacterCardByIndex(0).getCost()))));

        costCard1.setText(String.valueOf(GUI.instance().getModel().getCharacterCardByIndex(1).getCost()));
        GUI.instance().getModel().getCharacterCardByIndex(1).getCostProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> costCard1.setText(String.valueOf(GUI.instance().getModel().getCharacterCardByIndex(1).getCost()))));

        costCard2.setText(String.valueOf(GUI.instance().getModel().getCharacterCardByIndex(2).getCost()));
        GUI.instance().getModel().getCharacterCardByIndex(2).getCostProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> costCard2.setText(String.valueOf(GUI.instance().getModel().getCharacterCardByIndex(2).getCost()))));

        numberCoinsTable.setText(String.valueOf(GUI.instance().getModel().getCoins()));
        GUI.instance().getModel().getCoinsProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> numberCoinsTable.setText(String.valueOf(GUI.instance().getModel().getCoins()))));

        GUI.instance().getModel().currentCharacterCardProperty().addListener((ChangeListener<? super MockCard>) (change, oldVal, newVal) -> Platform.runLater(() -> {
            if (newVal.getType() == CharacterCardEnumeration.BASIC_STATE) {
                for (int i = 0; i < 3; i++) {
                    if (oldVal.getType() == GUI.instance().getModel().getCharacterCardByIndex(i).getType()) {
                        int a = i;
                        imageViewList.get(i).setOnMouseClicked(event -> playCharacterCard(a));
                    }
                }
            } else {
                int a = -1;
                for (int i = 0; i < 3; i++) {
                    if (GUI.instance().getModel().getCharacterCardByIndex(i).getType() == newVal.getType()) {
                        a = i;
                        break;
                    }
                }

                switch (newVal.getType()) {
                    case NO_COLOUR -> imageViewList.get(a).setOnMouseClicked(event -> Platform.runLater(() -> GUI.instance().showColourDecision()));
                    case THREE_STUDENT -> imageViewList.get(a).setOnMouseClicked(event -> Platform.runLater(() -> GUI.instance().showColourDecision()));
                }
            }
        }));
    }

    @FXML
    private void playCharacterCard(int i) {
        GUI.instance().getActionSender().playCharacterCard(GUI.instance().getPlayerName(), i);
    }

    @FXML
    private void setStudent(Colour colour) {

    }

    @FXML
    private void showSchoolBoard() {
        GUI.instance().showPlayerBoard();
    }
}
