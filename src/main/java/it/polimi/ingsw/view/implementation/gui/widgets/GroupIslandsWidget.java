package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;
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
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Shows the group islands and handles students moving and mother nature moving
 */
public class GroupIslandsWidget extends StackPane {

    /**
     * The main anchor pane
     */
    @FXML
    private AnchorPane anchorPane;

    /**
     * A list representing the group island boxes
     */
    private final List<AnchorPane> groupIslandsPanes = new ArrayList<>();

    /**
     * A nested list representing the single island boxes for each group island
     */
    private final List<List<AnchorPane>> singleIslandPanes = new ArrayList<>();

    /**
     * Constructs the group island widget
     */
    public GroupIslandsWidget() {
        FXMLUtils.loadWidgetFXML(this);
    }

    /*
    Button methods
     */

    /**
     * Shows the school board
     */
    @FXML
    public void showSchoolBoard() {
        GUI.instance().showPlayerBoard();
    }

    /*
    Init methods
     */

    /**
     * Init method called by the FXMLLoader
     * It calls all the init method of this class
     */
    @FXML
    public void initialize() {
        initGroupIslands();
        addListenerOnTurnPhase();
        addListenerOnMotherNatureProperty();
        addListenerOnGroupIslandInfluentPlayer();
        addListenerOnGroupIslandList();
        if (GUI.instance().getModel().isGameExpert() && GUI.instance().getModel().isCharacterCardPresent(CharacterCardEnumeration.PROTECT_ISLAND)) {
            addListenerOnNoEntryTile();
        }

        initWinner();
    }

    /**
     * Creates and renders the group island with all their properties
     */
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

                //adds the no entry tiles if PROTECT_ISLAND card is present
                if (GUI.instance().getModel().isGameExpert() && GUI.instance().getModel().isCharacterCardPresent(CharacterCardEnumeration.PROTECT_ISLAND)) {
                    Label noEntryTileLabel = new Label();
                    singleIslandPane.getChildren().add(noEntryTileLabel);
                    noEntryTileLabel.setLayoutX(60);
                    noEntryTileLabel.setLayoutY(20);
                    noEntryTileLabel.setStyle("-fx-font-size: 10; -fx-font-weight: bold;");
                    noEntryTileLabel.setText(" ! : " + GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getNoEntryTile());
                    noEntryTileLabel.setTextFill(GUIColours.getNoEntryTileMarkColour());
                    noEntryTileLabel.setBackground(new Background(new BackgroundFill(GUIColours.getGetNoEntryTileBackGroundColour(), CornerRadii.EMPTY, Insets.EMPTY)));
                    if (GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getNoEntryTile() > 0 && k == 0) {
                        noEntryTileLabel.setVisible(true);
                        noEntryTileLabel.toFront();
                    } else {
                        noEntryTileLabel.setVisible(false);
                    }
                }
            }


            //init the on mouse click event on the group island if the turn phase is MOVE_MOTHER_NATURE
            if (GUI.instance().getModel().getTurnPhase().equals(TurnPhase.MOVE_MOTHER_NATURE)) {
                islandPane.setOnMouseClicked(event -> moveMotherNature(groupIsland));
            }

            /*
            Group island positioning: ring configuration obtained with circumference properties
            It uses also static methods form class Positions which contains all the information about island positioning
             */
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

    /*
    Adding listeners
     */

    /**
     * Adds a listener on the group island property (located in MockTable) which handles the updates due to islands unify
     */
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
                    if (GUI.instance().getModel().isCharacterCardPresent(CharacterCardEnumeration.PROTECT_ISLAND)) {
                        addListenerOnNoEntryTile();
                    }
                }));
    }

    /**
     * Adds a listener on the no entry tile property (located in MockGroupIsland) for each group island
     * it handles the updates due to add or remove no entry tile from the group island
     */
    private void addListenerOnNoEntryTile() {
        for (int i = 0; i < GUI.instance().getModel().getTable().getGroupIslands().size(); i++) {
            int groupIsland = i;
            GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getNoEntryTileProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {
                Label noEntryTileLabel = (Label) singleIslandPanes.get(groupIsland).get(0).getChildren().get(4);
                if (newVal.intValue() > 0) {
                    noEntryTileLabel.setText(" ! : " + newVal.intValue());
                    noEntryTileLabel.setVisible(true);
                    noEntryTileLabel.toFront();
                } else {
                    noEntryTileLabel.setVisible(false);
                }
            }));
        }
    }

    /**
     * Adds a listener on the influent player property (located in MockGroupIsland) for each group island,
     * it handles the updates due to the influence change on a specific group island
     */
    private void addListenerOnGroupIslandInfluentPlayer() {
        //adds the listener on mother nature property on a single island
        for (int i = 0; i < GUI.instance().getModel().getTable().getGroupIslands().size(); i++) {
            int groupIsland = i;
            GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getInfluentPlayerProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {
                if (groupIsland < GUI.instance().getModel().getTable().getGroupIslands().size()) {
                    for (int j = 0; j < GUI.instance().getModel().getTable().getGroupIslandByIndex(groupIsland).getIslands().size(); j++) {
                        if (newVal != null) {
                            Circle tower = (Circle) singleIslandPanes.get(groupIsland).get(j).getChildren().get(3);
                            tower.setVisible(true);
                            tower.setFill(GUIColours.getTowerRGBColour(GUI.instance().getModel().getPlayerByNickname(newVal).getTowerColour()));
                        }
                    }
                }
            }));
        }
    }

    /**
     * Adds a listener to the Hash Map representing the students on the single island
     *
     * @param groupIslandIndex  the index of the group island
     * @param singleIslandIndex the index of the single island
     * @param studentsLabels    the student labels to bind with the entries of the hash map
     */
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

    /**
     * Adds a listener on the mother nature position property (located in MockTable) which handles the mother nature movement
     */
    private void addListenerOnMotherNatureProperty() {
        //adds the listener on mother nature property on a single island
        GUI.instance().getModel().getTable().getMotherNaturePositionProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {
            if (oldVal.intValue() < GUI.instance().getModel().getTable().getGroupIslands().size()) {
                singleIslandPanes.get(oldVal.intValue()).get(0).getChildren().get(2).setVisible(false);
            }
            singleIslandPanes.get(newVal.intValue()).get(0).getChildren().get(2).setVisible(true);
        }));
    }

    /**
     * Adds a listener on the turn phase property (located in MockModel) which binds the on click events
     * to the view components according to the turn phase
     */
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

            initWinner();
        }));
    }

    /*
    Actions after mouse click
     */

    /**
     * Calls the action sender to move a student on a single island when the user clicks on a single island
     *
     * @param groupIsland  the index of the group island
     * @param singleIsland the index of the single island
     */
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

    /**
     * Calls the action sender to move mother nature when the user clicks on a group island
     *
     * @param groupIsland the index of the group island
     */
    private void moveMotherNature(int groupIsland) {
        int movement = groupIsland - GUI.instance().getModel().getTable().getMotherNaturePosition();
        if (movement < 0) {
            movement += GUI.instance().getModel().getTable().getGroupIslands().size();
        }
        GUI.instance().getActionSender().moveMotherNature(GUI.instance().getPlayerName(), movement);
    }

    /**
     * No action method to replace previous on click methods
     */
    private void noAction() {
    }

    /**
     * A method which checks if the game has ended (if the turn phase is ENDGAME) and, if so, calls the method that shows the winner
     */
    private void initWinner() {

        if (GUI.instance().getModel().getTurnPhase().equals(TurnPhase.ENDGAME)) {
            printWinner();
        }
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


}
