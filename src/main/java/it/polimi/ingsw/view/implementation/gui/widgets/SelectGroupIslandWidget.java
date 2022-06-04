package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;
import it.polimi.ingsw.view.implementation.gui.GUI;
import it.polimi.ingsw.view.implementation.gui.widgets.utils.Coordinates;
import it.polimi.ingsw.view.implementation.gui.widgets.utils.GUIColours;
import it.polimi.ingsw.view.implementation.gui.widgets.utils.Positions;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SelectGroupIslandWidget extends StackPane {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label islandInfluence;

    @FXML
    private Label protectIsland;

    @FXML
    private Label studentToIsland;

    private CharacterCardEnumeration characterCard;

    private List<AnchorPane> groupIslandsPanes = new ArrayList<>();

    private List<List<AnchorPane>> singleIslandPanes = new ArrayList<>();

    public SelectGroupIslandWidget() {
        FXMLUtils.loadWidgetFXML(this);
    }

    @FXML
    public void initialize(){
        initGroupIslands();
        characterCard = GUI.instance().getModel().getCurrentCharacterCard().getType();
        if(characterCard.equals(CharacterCardEnumeration.PROTECT_ISLAND) || characterCard.equals(CharacterCardEnumeration.ISLAND_INFLUENCE)){
            addMouseClickEventOnGroupIslands();
        }
        else if(characterCard.equals(CharacterCardEnumeration.STUDENT_TO_ISLAND)){
            addMouseClickEventOnSingleIslands();
        }
        if(characterCard.equals(CharacterCardEnumeration.PROTECT_ISLAND)){
            protectIsland.setVisible(true);
            islandInfluence.setVisible(false);
            studentToIsland.setVisible(false);

        }
        else if(characterCard.equals(CharacterCardEnumeration.ISLAND_INFLUENCE)){
            protectIsland.setVisible(false);
            islandInfluence.setVisible(true);
            studentToIsland.setVisible(false);
        }
        else if(characterCard.equals(CharacterCardEnumeration.STUDENT_TO_ISLAND)){
            protectIsland.setVisible(false);
            islandInfluence.setVisible(false);
            studentToIsland.setVisible(true);
        }


    }

    private void initGroupIslands() {
        int groupIslands = GUI.instance().getModel().getTable().getGroupIslands().size();
        int singleIslands;
        List<Coordinates> singleIslandsCoordinates;
        for (int i = 0, j = 0; i < groupIslands; i++) {
            singleIslands = GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getIslands().size();
            List<AnchorPane> singleIslandBoxes = new ArrayList<>();
            singleIslandPanes.add(singleIslandBoxes);
            //creates the flow pane for the group island
            AnchorPane islandPane = new AnchorPane();
            islandPane.setPrefWidth(Positions.getIslandPaneDimension(singleIslands));
            islandPane.setPrefHeight(Positions.getIslandPaneDimension(singleIslands));
            anchorPane.getChildren().add(islandPane);
            groupIslandsPanes.add(islandPane);


            singleIslandsCoordinates = Positions.getIslandsCoordinates(singleIslands);
            for (int k = 0; k < singleIslands; k++) {
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

                //adds the no entry tiles if PROTECT_ISLAND card is present
                if(!GUI.instance().getModel().isGameExpert() && GUI.instance().getModel().isCharacterCardPresent(CharacterCardEnumeration.PROTECT_ISLAND)) {
                    Label noEntryTileLabel = new Label();
                    singleIslandPane.getChildren().add(noEntryTileLabel);
                    noEntryTileLabel.setLayoutX(60);
                    noEntryTileLabel.setLayoutY(20);
                    noEntryTileLabel.setText(" ! : " + GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getNoEntryTile());
                    noEntryTileLabel.setTextFill(GUIColours.getNoEntryTileMarkColour());
                    noEntryTileLabel.setBackground(new Background(new BackgroundFill(GUIColours.getGetNoEntryTileBackGroundColour(), CornerRadii.EMPTY, Insets.EMPTY)));
                    if (GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getNoEntryTile() > 0 && k == 0) {
                        noEntryTileLabel.setVisible(true);
                        noEntryTileLabel.toFront();
                    }
                    else{
                        noEntryTileLabel.setVisible(false);
                    }
                }
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


    private void addMouseClickEventOnGroupIslands(){
        for(AnchorPane groupIslandPane : groupIslandsPanes){
            groupIslandPane.getStyleClass().add("groupIsland");
            groupIslandPane.setOnMouseClicked(event -> Platform.runLater(() -> selectGroupIsland(groupIslandsPanes.indexOf(groupIslandPane))));
        }
    }

    private void selectGroupIsland(int groupIslandIndex){
        GUI.instance().getActionSender().setGroupIsland(GUI.instance().getPlayerName(),groupIslandIndex);
        for(AnchorPane groupIslandPane : groupIslandsPanes){
            groupIslandPane.setOnMouseClicked(event -> Platform.runLater(() -> noAction()));
            groupIslandPane.getStyleClass().remove("groupIsland");
        }
    }

    private void noAction(){

    }

    private void addMouseClickEventOnSingleIslands(){
        for(List<AnchorPane> groupIsland : singleIslandPanes){
            for(AnchorPane singleIslandPane : groupIsland){
                singleIslandPane.getStyleClass().add("singleIsland");
                singleIslandPane.setOnMouseClicked(event -> Platform.runLater(() -> selectSingleIsland(singleIslandPanes.indexOf(groupIsland),groupIsland.indexOf(singleIslandPane))));
            }
        }
    }

    private void selectSingleIsland(int groupIslandIndex,int singleIslandIndex){
        if(GUI.instance().getModel().getStudentOnCardSelected() != null){
            GUI.instance().getActionSender().setColourAndIsland(GUI.instance().getPlayerName(),GUI.instance().getModel().getStudentOnCardSelected(),groupIslandIndex,singleIslandIndex);
            for(List<AnchorPane> groupIsland : singleIslandPanes){
                for(AnchorPane singleIslandPane : groupIsland){
                    singleIslandPane.setOnMouseClicked(event -> Platform.runLater(() -> noAction()));
                    singleIslandPane.getStyleClass().remove("singleIsland");

                }
            }

        }
        else{
            GUI.instance().getRenderer().showErrorMessage("no student selected: go to character cards and select a student");
        }

    }

    @FXML
    private void showSchoolBoard() {
        GUI.instance().showPlayerBoard();
    }

}
