package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.model.player.TowerColour;
import it.polimi.ingsw.view.beans.MockGroupIsland;
import it.polimi.ingsw.view.implementation.gui.GUI;
import it.polimi.ingsw.view.implementation.gui.widgets.utils.Coordinates;
import it.polimi.ingsw.view.implementation.gui.widgets.utils.GUIColours;
import it.polimi.ingsw.view.implementation.gui.widgets.utils.Positions;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.*;

public class GroupIslandsWidget extends StackPane {

    @FXML
    private AnchorPane anchorPane;

    private List<AnchorPane> groupIslandsPanes = new ArrayList<>();

    private List<List<AnchorPane>> singleIslandPanes = new ArrayList<>();

    public GroupIslandsWidget() {
        FXMLUtils.loadWidgetFXML(this);
    }

    @FXML
    public void showSchoolBoard() {
        GUI.instance().showPlayerBoard();
    }

    @FXML
    public void initialize() {
        initGroupIslands();
        addListenerOnTurnPhase();
        addListenerOnMotherNatureProperty();
        addListenerOnGroupIslandInfluentPlayer();
        addListenerOnGroupIslandList();
        //addListenerOnIslandInfluenceChange();
    }

    private void addListenerOnGroupIslandList() {
        GUI.instance().getModel().getTable().getGroupIslandsProperty().addListener((ListChangeListener<? super MockGroupIsland>) listener ->
                Platform.runLater(() -> {
                    for (int i = 0; i < groupIslandsPanes.size(); i++) {
                        groupIslandsPanes.get(i).getChildren().removeAll(singleIslandPanes.get(i));
                    }

                    anchorPane.getChildren().removeAll(groupIslandsPanes);
                    groupIslandsPanes.clear();
                    singleIslandPanes.clear();
                    initGroupIslands();
                    addListenerOnGroupIslandInfluentPlayer();
                }));
    }

    private void initGroupIslands() {
        int groupIslands = GUI.instance().getModel().getTable().getGroupIslands().size();
        int singleIslands;
        List<Coordinates> singleIslandsCoordinates;
        for (int i = 0, j = 0; i < groupIslands; i++) {
            int groupIsland = i;
            singleIslands = GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getIslands().size();
            List<AnchorPane> singleIslandBoxes = new ArrayList<>();
            singleIslandPanes.add(singleIslandBoxes);
            //creates the flow pane for the group island
            AnchorPane islandPane = new AnchorPane();
            islandPane.setPrefWidth(Positions.getIslandPaneDimension(singleIslands));
            islandPane.setPrefHeight(Positions.getIslandPaneDimension(singleIslands));
            anchorPane.getChildren().add(islandPane);
            groupIslandsPanes.add(islandPane);

            if (GUI.instance().getModel().getTurnPhase().equals(TurnPhase.MOVE_MOTHER_NATURE)) {
                islandPane.getStyleClass().add("groupIsland");
                islandPane.setOnMouseClicked(event -> moveMotherNature(groupIsland));
            }

            singleIslandsCoordinates = Positions.getIslandsCoordinates(singleIslands);
            for (int k = 0; k < singleIslands; k++) {
                int singleIsland = k;
                //creates the anchor pane for the single island and sets the position in the groupIslandPane
                AnchorPane singleIslandPane = new AnchorPane();
                singleIslandBoxes.add(singleIslandPane);
                islandPane.getChildren().add(singleIslandPane);
                singleIslandPane.setLayoutX(singleIslandsCoordinates.get(k).getRow());
                singleIslandPane.setLayoutY(singleIslandsCoordinates.get(k).getColumn());

                //create the pane and image view to for the single island
                ImageView imageView = new ImageView();
                singleIslandPane.getChildren().add(imageView);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                imageView.setImage(new Image(Objects.requireNonNull(AssistantCardsWidget.class.getResourceAsStream(
                        "/images/islands/island2.png"))));
                imageView.setLayoutX(0);
                imageView.setLayoutY(0);

                //init the on mouse click event on the single island according to the turn phase
                if (GUI.instance().getModel().getTurnPhase().equals(TurnPhase.MOVE_STUDENT)) {
                    singleIslandPane.getStyleClass().add("singleIsland");
                    islandPane.getStyleClass().removeAll("groupIsland");
                    singleIslandPane.setOnMouseClicked(event -> addStudentToSingleIsland(groupIsland, singleIsland));
                    islandPane.setOnMouseClicked(event -> noAction());
                } else {
                    singleIslandPane.getStyleClass().removeAll("singleIsland");
                    singleIslandPane.setOnMouseClicked(event -> noAction());
                }

                //adds the grid for the students
                GridPane studentsOnSingleIsland = new GridPane();
                singleIslandPane.getChildren().add(studentsOnSingleIsland);
                studentsOnSingleIsland.setPrefWidth(80);
                studentsOnSingleIsland.setPrefHeight(70);
                studentsOnSingleIsland.toFront();
                studentsOnSingleIsland.setLayoutX(17);
                studentsOnSingleIsland.setLayoutY(10);
                List<Label> studentsLabels = new ArrayList<>();
                for (Colour colour : Colour.values()) {
                    Label label = new Label();
                    label.setPrefHeight(14.0);
                    label.setStyle("-fx-font-size: 10; -fx-font-weight: bold;");
                    studentsLabels.add(label);
                    label.setBackground(new Background(new BackgroundFill(GUIColours.getStudentRGBColour(colour), CornerRadii.EMPTY, Insets.EMPTY)));
                    studentsOnSingleIsland.addRow(Colour.getColourCode(colour), label);
                    label.setText(" " + GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getSingleIslandByIndex(k).getStudents(colour) + " ");
                }

                //adds mother nature to the single island
                Circle motherNature = new Circle();
                motherNature.setRadius(5);
                singleIslandPane.getChildren().add(motherNature);
                motherNature.setLayoutX(80);
                motherNature.setLayoutY(50);
                motherNature.toFront();
                motherNature.setFill(GUIColours.getMotherNatureColour());

                //sets the visibility of mother nature according to its position on the game table
                motherNature.setVisible(GUI.instance().getModel().getTable().getGroupIslandByIndex(i).isMotherNature() && k == 0);
                addListenerOnSingleIslandStudents(i, k, studentsLabels);

                //adds the tower
                Circle tower = new Circle();
                tower.setRadius(5);
                singleIslandPane.getChildren().add(tower);
                tower.setLayoutX(60);
                tower.setLayoutY(50);
                tower.toFront();
                if (GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getInfluentPlayer() != null) {
                    tower.setVisible(true);
                    tower.setFill(GUIColours.getTowerRGBColour(GUI.instance().getModel().getPlayerByNickname(GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getInfluentPlayer()).getTowerColour()));
                } else {
                    tower.setVisible(false);
                    tower.setFill(Color.rgb(0, 0, 0, 0.0));
                }
                addListenerOnSingleIslandStudents(i, k, studentsLabels);


            }

            //init the on mouse click event on the group island if the turn phase is MOVE_MOTHER_NATURE
            if (GUI.instance().getModel().getTurnPhase().equals(TurnPhase.MOVE_MOTHER_NATURE)) {
                islandPane.setOnMouseClicked(event -> moveMotherNature(groupIsland));
            }
            if (j <= 6 && j + singleIslands > 6) {
                islandPane.setLayoutX(540 - Positions.getIslandPaneDimension(singleIslands) / 2.0 + 220 * Math.cos((-2 * Math.PI + Positions.getAngle(j % 12) + Positions.getAngle((j + singleIslands) % 12)) / 2));
                islandPane.setLayoutY(360 - Positions.getIslandPaneDimension(singleIslands) / 2.0 - 220 * Math.sin((-2 * Math.PI + Positions.getAngle(j % 12) + Positions.getAngle((j + singleIslands) % 12)) / 2));
            } else {
                islandPane.setLayoutX(540 - Positions.getIslandPaneDimension(singleIslands) / 2.0 + 220 * Math.cos((Positions.getAngle(j % 12) + Positions.getAngle((j + singleIslands) % 12)) / 2));
                islandPane.setLayoutY(360 - Positions.getIslandPaneDimension(singleIslands) / 2.0 - 220 * Math.sin((Positions.getAngle(j % 12) + Positions.getAngle((j + singleIslands) % 12)) / 2));
            }
            j += singleIslands;
        }
    }

    private void addListenerOnGroupIslandInfluentPlayer(){
        //adds the listener on mother nature property on a single island
        for(int i = 0; i < GUI.instance().getModel().getTable().getGroupIslands().size(); i++){
            int groupIsland = i;
            GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getInfluentPlayerProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {
                if(groupIsland < GUI.instance().getModel().getTable().getGroupIslands().size()){
                    for(int j = 0; j < GUI.instance().getModel().getTable().getGroupIslandByIndex(groupIsland).getIslands().size(); j++){
                        if(newVal != null){
                            Circle tower = (Circle) singleIslandPanes.get(groupIsland).get(j).getChildren().get(3);
                            tower.setVisible(true);
                            tower.setFill(GUIColours.getTowerRGBColour(GUI.instance().getModel().getPlayerByNickname(newVal).getTowerColour()));
                        }
                    }
                }
            }));
        }
    }

    private void addListenerOnSingleIslandStudents(int groupIslandIndex, int singleIslandIndex, List<Label> studentsLabels) {
        //adds a listener to all the single islands
        GUI.instance().getModel().getTable().getGroupIslandByIndex(groupIslandIndex).getSingleIslandByIndex(singleIslandIndex).getStudentsProperty().addListener((MapChangeListener<? super Colour, ? super Integer>) listener ->
                Platform.runLater(() -> {
                    //addsStudents
                    for (Colour colour : Colour.values()) {
                        studentsLabels.get(Colour.getColourCode(colour)).setText(" " + listener.getMap().get(colour) + " ");
                    }
                }));
    }

    private void addListenerOnMotherNatureProperty() {
        //adds the listener on mother nature property on a single island
        GUI.instance().getModel().getTable().getMotherNaturePositionProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {
            if (oldVal.intValue() < GUI.instance().getModel().getTable().getGroupIslands().size()) {
                singleIslandPanes.get(oldVal.intValue()).get(0).getChildren().get(2).setVisible(false);
            }
            singleIslandPanes.get(newVal.intValue()).get(0).getChildren().get(2).setVisible(true);
        }));
    }


    private void addListenerOnTurnPhase() {
        //adds the listeners for the turn phase to add mouse click on group islands
        GUI.instance().getModel().getTurnPhaseProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {

            if (GUI.instance().getModel().getTurnPhase().equals(TurnPhase.MOVE_MOTHER_NATURE)) {

                for (int i = 0; i < GUI.instance().getModel().getTable().getGroupIslands().size(); i++) {
                    int groupIsland = i;
                    groupIslandsPanes.get(i).setOnMouseClicked(event -> moveMotherNature(groupIsland));
                    groupIslandsPanes.get(i).getStyleClass().add("groupIsland");
                    for (int j = 0; j < GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getIslands().size(); j++) {
                        singleIslandPanes.get(i).get(j).getStyleClass().removeAll("singleIsland");
                        singleIslandPanes.get(i).get(j).setOnMouseClicked(event -> noAction());
                    }
                }

            } else if (GUI.instance().getModel().getTurnPhase().equals(TurnPhase.MOVE_STUDENT)) {
                //GUI.instance().getRenderer().showErrorMessage("sono entrato nel run later -> move student");
                for (int i = 0; i < GUI.instance().getModel().getTable().getGroupIslands().size(); i++) {
                    int groupIsland = i;
                    groupIslandsPanes.get(i).getStyleClass().removeAll("groupIsland");
                    groupIslandsPanes.get(i).setOnMouseClicked(event -> noAction());
                    for (int j = 0; j < GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getIslands().size(); j++) {
                        int singleIsland = j;
                        singleIslandPanes.get(i).get(j).getStyleClass().add("singleIsland");
                        singleIslandPanes.get(i).get(j).setOnMouseClicked(event -> addStudentToSingleIsland(groupIsland, singleIsland));
                    }

                }
            } else {
                for (int i = 0; i < GUI.instance().getModel().getTable().getGroupIslands().size(); i++) {
                    groupIslandsPanes.get(i).getStyleClass().removeAll("groupIsland");
                    groupIslandsPanes.get(i).setOnMouseClicked(event -> noAction());
                    for (int j = 0; j < GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getIslands().size(); j++) {
                        singleIslandPanes.get(i).get(j).getStyleClass().removeAll("singleIsland");
                        singleIslandPanes.get(i).get(j).setOnMouseClicked(event -> noAction());
                    }

                }
            }
        }));
    }


    private void addStudentToSingleIsland(int groupIsland, int singleIsland) {
        if (GUI.instance().getModel().getSelectedColour() != null) {
            GUI.instance().getActionSender().moveStudentToIsland(GUI.instance().getPlayerName(), GUI.instance().getModel().getSelectedColour(), groupIsland, singleIsland);
        } else {
            if (GUI.instance().getModel().getTurnPhase() == TurnPhase.MOVE_STUDENT) {
                GUI.instance().getRenderer().showErrorMessage("Select a student!");
            }
        }
        GUI.instance().getModel().setSelectedColour(null);
    }

    private void moveMotherNature(int groupIsland) {

        int movement = groupIsland - GUI.instance().getModel().getTable().getMotherNaturePosition();

        if (movement < 0) {
            movement += GUI.instance().getModel().getTable().getGroupIslands().size();
        }


        GUI.instance().getActionSender().moveMotherNature(GUI.instance().getPlayerName(), movement);
    }

    private void noAction() {
    }


}
