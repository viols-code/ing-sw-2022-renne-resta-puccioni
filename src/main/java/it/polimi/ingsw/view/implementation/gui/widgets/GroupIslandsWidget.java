package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.model.player.TowerColour;
import it.polimi.ingsw.view.beans.MockGroupIsland;
import it.polimi.ingsw.view.implementation.gui.GUI;
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

    private List<Coordinates> groupIslandBoxes;

    private List<AnchorPane> groupIslandsPanes = new ArrayList<>();

    private List<List<AnchorPane>> singleIslandPanes = new ArrayList<>();

    private HashMap<Colour,Paint> studentRGBColours = new HashMap<>();

    private HashMap<TowerColour,Paint> towerRGBColours = new HashMap<>();

    public GroupIslandsWidget() {
        FXMLUtils.loadWidgetFXML(this);
    }

    @FXML
    public void showSchoolBoard() {
        GUI.instance().showPlayerBoard();
    }

    @FXML
    public void initialize(){
        initStudentRGBColour();
        initTowerRGBColour();
        groupIslandBoxes = new ArrayList<>(Arrays.asList(new Coordinates(11,268), new Coordinates(110,131), new Coordinates(245,40),new Coordinates(425,-6), new Coordinates(601,40),new Coordinates(753,131),new Coordinates(853,268),new Coordinates(753,410),new Coordinates(601,490),new Coordinates(429,524),new Coordinates(245,490),new Coordinates(99,410)));
        initGroupIslands();
        addListenerOnTurnPhase();
        addListenerOnMotherNatureProperty();
        //addListenerOnGroupIslandInfluentPlayer();
        addListenerOnGroupIslandList();
        addListenerOnIslandInfluenceChange();
    }

    private void addListenerOnGroupIslandList(){
        GUI.instance().getModel().getTable().getGroupIslandsProperty().addListener((ListChangeListener<? super MockGroupIsland>) listener ->
                Platform.runLater(() -> {
                    for(int i = 0; i < groupIslandsPanes.size(); i++){
                        groupIslandsPanes.get(i).getChildren().removeAll(singleIslandPanes.get(i));
                    }

                    anchorPane.getChildren().removeAll(groupIslandsPanes);
                    groupIslandsPanes.clear();
                    singleIslandPanes.clear();
                    System.out.println(groupIslandsPanes.size() + "-" + singleIslandPanes.size());
                    initGroupIslands();
                    //addListenerOnGroupIslandInfluentPlayer();
                }));
    }

    private void addListenerOnIslandInfluenceChange(){
        GUI.instance().getModel().getTable().islandInfluenceChangedProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {
            if(GUI.instance().getModel().getTable().islandInfluenceChangedProperty().getValue() < GUI.instance().getModel().getTable().getGroupIslands().size()) {
                for (int j = 0; j < GUI.instance().getModel().getTable().getGroupIslandByIndex(GUI.instance().getModel().getTable().islandInfluenceChangedProperty().getValue()).getIslands().size(); j++) {
                    if (newVal != null) {
                        Circle tower = (Circle) singleIslandPanes.get(GUI.instance().getModel().getTable().islandInfluenceChangedProperty().getValue()).get(j).getChildren().get(3);
                        tower.setVisible(true);
                        tower.setFill(towerRGBColours.get(GUI.instance().getModel().getPlayerByNickname(GUI.instance().getModel().getTable().getGroupIslandByIndex(GUI.instance().getModel().getTable().islandInfluenceChangedProperty().getValue()).getInfluentPlayer()).getTowerColour()));
                    }
                }}
            }));
    }

    private void initGroupIslands(){
        int groupIslands = GUI.instance().getModel().getTable().getGroupIslands().size();
        int singleIslands;
        List<Coordinates> singleIslandsCoordinates;
        for(int i = 0,j=0; i < groupIslands; i++){
            int groupIsland = i;
            singleIslands = GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getIslands().size();
            List <AnchorPane> singleIslandBoxes = new ArrayList<>();
            singleIslandPanes.add(singleIslandBoxes);
            //creates the flow pane for the group island
            AnchorPane islandPane = new AnchorPane();
            islandPane.setPrefWidth(getIslandPaneDimension(singleIslands));
            islandPane.setPrefHeight(getIslandPaneDimension(singleIslands));
            anchorPane.getChildren().add(islandPane);
            groupIslandsPanes.add(islandPane);

            if(GUI.instance().getModel().getTurnPhase().equals(TurnPhase.MOVE_MOTHER_NATURE)){
                islandPane.getStyleClass().add("groupIsland");
                islandPane.setOnMouseClicked(event -> moveMotherNature(groupIsland));
            }

            singleIslandsCoordinates = getIslandsCoordinates(singleIslands);
            for(int k = 0; k < singleIslands; k++){
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
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);
                imageView.setImage(new Image(Objects.requireNonNull(AssistantCardsWidget.class.getResourceAsStream(
                        "/images/islands/island2.png"))));
                imageView.setLayoutX(0);
                imageView.setLayoutY(0);

                //init the on mouse click event on the single island according to the turn phase
                if(GUI.instance().getModel().getTurnPhase().equals(TurnPhase.MOVE_STUDENT)){
                    singleIslandPane.getStyleClass().add("singleIsland");
                    islandPane.getStyleClass().removeAll("groupIsland");
                    singleIslandPane.setOnMouseClicked(event -> addStudentToSingleIsland(groupIsland,singleIsland));
                    islandPane.setOnMouseClicked(event -> noAction());
                }
                else{
                    singleIslandPane.getStyleClass().removeAll("singleIsland");
                    singleIslandPane.setOnMouseClicked(event -> noAction());
                }

                //adds the grid for the students
                GridPane studentsOnSingleIsland = new GridPane();
                singleIslandPane.getChildren().add(studentsOnSingleIsland);
                studentsOnSingleIsland.setPrefWidth(100);
                studentsOnSingleIsland.setPrefHeight(100);
                studentsOnSingleIsland.toFront();
                studentsOnSingleIsland.setLayoutX(25);
                studentsOnSingleIsland.setLayoutY(25);
                List<Label> studentsLabels = new ArrayList<>();
                for(Colour colour: Colour.values()){
                    Label label = new Label();
                    studentsLabels.add(label);
                    label.setBackground(new Background(new BackgroundFill(studentRGBColours.get(colour),CornerRadii.EMPTY, Insets.EMPTY)));
                    studentsOnSingleIsland.addRow(Colour.getColourCode(colour),label);
                    label.setText("" + GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getSingleIslandByIndex(k).getStudents(colour));
                }

                //adds mother nature to the single island
                Circle motherNature = new Circle();
                motherNature.setRadius(10);
                singleIslandPane.getChildren().add(motherNature);
                motherNature.setLayoutX(130);
                motherNature.setLayoutY(75);
                motherNature.toFront();
                motherNature.setFill(Color.rgb(255, 102, 0));

                //sets the visibility of mother nature according to its position on the game table
                motherNature.setVisible(GUI.instance().getModel().getTable().getGroupIslandByIndex(i).isMotherNature() && k == 0);
                addListenerOnSingleIslandStudents(i,k,studentsLabels);

                //adds the tower
                Circle tower = new Circle();
                tower.setRadius(10);
                singleIslandPane.getChildren().add(tower);
                tower.setLayoutX(90);
                tower.setLayoutY(75);
                tower.toFront();
                if(GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getInfluentPlayer() != null){
                    tower.setVisible(true);
                    tower.setFill(towerRGBColours.get(GUI.instance().getModel().getPlayerByNickname(GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getInfluentPlayer()).getTowerColour()));
                }
                else{
                    tower.setVisible(false);
                    tower.setFill(Color.rgb(0,0,0,0.0));
                }
                addListenerOnSingleIslandStudents(i,k,studentsLabels);
            }

            //init the on mouse click event on the group island if the turn phase is MOVE_MOTHER_NATURE
            if(GUI.instance().getModel().getTurnPhase().equals(TurnPhase.MOVE_MOTHER_NATURE)) {
                islandPane.setOnMouseClicked(event -> moveMotherNature(groupIsland));
            }

            //sets the layout
            islandPane.setLayoutX(groupIslandBoxes.get(j).getRow());
            islandPane.setLayoutY(groupIslandBoxes.get(j).getColumn());
            j += singleIslands;
        }
    }

    private void initTowerRGBColour(){
        towerRGBColours.put(TowerColour.WHITE,Color.rgb(255,255,255));
        towerRGBColours.put(TowerColour.BLACK,Color.rgb(0, 0, 0));
        towerRGBColours.put(TowerColour.GREY,Color.rgb(179, 179, 179));
    }

    private void initStudentRGBColour(){
        studentRGBColours.put(Colour.GREEN,Color.rgb(0,255,0));
        studentRGBColours.put(Colour.RED,Color.rgb(255, 0, 0));
        studentRGBColours.put(Colour.YELLOW,Color.rgb(255, 204, 0));
        studentRGBColours.put(Colour.PINK,Color.rgb(255, 102, 204));
        studentRGBColours.put(Colour.BLUE,Color.rgb(0, 204, 255));
    }

    /*private void addListenerOnGroupIslandInfluentPlayer(){
        //adds the listener on mother nature property on a single island
        for(int i = 0; i < GUI.instance().getModel().getTable().getGroupIslands().size(); i++){
            int groupIsland = i;
            GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getInfluentPlayerProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {
                if(groupIsland < GUI.instance().getModel().getTable().getGroupIslands().size()){
                    for(int j = 0; j < GUI.instance().getModel().getTable().getGroupIslandByIndex(groupIsland).getIslands().size(); j++){
                        if(newVal != null){
                            Circle tower = (Circle) singleIslandPanes.get(groupIsland).get(j).getChildren().get(3);
                            tower.setVisible(true);
                            tower.setFill(towerRGBColours.get(GUI.instance().getModel().getPlayerByNickname(newVal).getTowerColour()));
                        }
                    }
                }
            }));
        }
    }*/

    private void addListenerOnSingleIslandStudents(int groupIslandIndex, int singleIslandIndex, List<Label> studentsLabels){
        //adds a listener to all the single islands
        GUI.instance().getModel().getTable().getGroupIslandByIndex(groupIslandIndex).getSingleIslandByIndex(singleIslandIndex).getStudentsProperty().addListener((MapChangeListener<? super Colour, ? super Integer>) listener ->
                Platform.runLater(() -> {
                    //addsStudents
                    for(Colour colour: Colour.values()){
                        studentsLabels.get(Colour.getColourCode(colour)).setText("" + listener.getMap().get(colour));
                    }
                }));
    }

    private void addListenerOnMotherNatureProperty(){
        //adds the listener on mother nature property on a single island
        GUI.instance().getModel().getTable().getMotherNaturePositionProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {
            singleIslandPanes.get(oldVal.intValue()).get(0).getChildren().get(2).setVisible(false);
            singleIslandPanes.get(newVal.intValue()).get(0).getChildren().get(2).setVisible(true);
        }));
    }


    private void addListenerOnTurnPhase(){
        //adds the listeners for the turn phase to add mouse click on group islands
        GUI.instance().getModel().getTurnPhaseProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {

            if(GUI.instance().getModel().getTurnPhase().equals(TurnPhase.MOVE_MOTHER_NATURE)){

                for(int i = 0; i < GUI.instance().getModel().getTable().getGroupIslands().size(); i++){
                    int groupIsland = i;
                    groupIslandsPanes.get(i).setOnMouseClicked(event -> moveMotherNature(groupIsland));
                    groupIslandsPanes.get(i).getStyleClass().add("groupIsland");
                    for(int j = 0; j < GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getIslands().size(); j++){
                        int singleIsland = j;
                        singleIslandPanes.get(i).get(j).getStyleClass().removeAll("singleIsland");
                        singleIslandPanes.get(i).get(j).setOnMouseClicked(event -> noAction());
                    }
                }

            }
            else if(GUI.instance().getModel().getTurnPhase().equals(TurnPhase.MOVE_STUDENT)){
                //GUI.instance().getRenderer().showErrorMessage("sono entrato nel run later -> move student");
                for(int i = 0; i < GUI.instance().getModel().getTable().getGroupIslands().size(); i++){
                    int groupIsland = i;
                    groupIslandsPanes.get(i).getStyleClass().removeAll("groupIsland");
                    groupIslandsPanes.get(i).setOnMouseClicked(event -> noAction());
                    for(int j = 0; j < GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getIslands().size(); j++){
                        int singleIsland = j;
                        singleIslandPanes.get(i).get(j).getStyleClass().add("singleIsland");
                        singleIslandPanes.get(i).get(j).setOnMouseClicked(event -> addStudentToSingleIsland(groupIsland,singleIsland));
                    }

                }
            }
            else{
                for(int i = 0; i < GUI.instance().getModel().getTable().getGroupIslands().size(); i++){
                    int groupIsland = i;
                    groupIslandsPanes.get(i).getStyleClass().removeAll("groupIsland");
                    groupIslandsPanes.get(i).setOnMouseClicked(event -> noAction());
                    for(int j = 0; j < GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getIslands().size(); j++){
                        int singleIsland = j;
                        singleIslandPanes.get(i).get(j).getStyleClass().removeAll("singleIsland");
                        singleIslandPanes.get(i).get(j).setOnMouseClicked(event -> noAction());
                    }

                }
            }
        }));
    }

    private List<Coordinates> getIslandsCoordinates(int numberOfSingleIsland){
        List<Coordinates> coordinates = null;
        switch(numberOfSingleIsland){
            case 1 -> coordinates = getCoordinatesForOneSingleIsland();
            case 2 -> coordinates = getCoordinatesForTwoSingleIslands();
            case 3 -> coordinates = getCoordinatesForThreeSingleIslands();
            case 4 -> coordinates = getCoordinatesForFourGroupIslands();
            case 5 -> coordinates = getCoordinatesForFiveGroupIslands();
            case 6 -> coordinates = getCoordinatesForSixGroupIslands();
            case 7 -> coordinates = getCoordinatesForSevenGroupIslands();
        }
        return coordinates;
    }

    private List<Coordinates> getCoordinatesForOneSingleIsland(){
        return new ArrayList<>(List.of(new Coordinates(0, 0)));
    }

    private List<Coordinates> getCoordinatesForTwoSingleIslands(){
        return new ArrayList<>(Arrays.asList(new Coordinates(7,18),new Coordinates(114,111)));
    }

    private List<Coordinates> getCoordinatesForThreeSingleIslands(){
        return new ArrayList<>(Arrays.asList(new Coordinates(24,66),new Coordinates(136,14),new Coordinates(136,141)));
    }

    private List<Coordinates> getCoordinatesForFourGroupIslands(){
        return new ArrayList<>(Arrays.asList(new Coordinates(14,8),new Coordinates(150,0),new Coordinates(14,136), new Coordinates(150,136)));
    }

    private List<Coordinates> getCoordinatesForFiveGroupIslands(){
        return new ArrayList<>(Arrays.asList(new Coordinates(14,14),new Coordinates(134,66),new Coordinates(286,150), new Coordinates(50,141),new Coordinates(166,214)));
    }

    private List<Coordinates> getCoordinatesForSixGroupIslands(){
        return new ArrayList<>(Arrays.asList(new Coordinates(14,14),new Coordinates(250,6),new Coordinates(127,81), new Coordinates(14,149),new Coordinates(127,224),new Coordinates(239,149)));
    }

    private List<Coordinates> getCoordinatesForSevenGroupIslands(){
        return new ArrayList<>(Arrays.asList(new Coordinates(0,50),new Coordinates(125,0),new Coordinates(250,61), new Coordinates(125,125),new Coordinates(0,192),new Coordinates(125,250),new Coordinates(250,200)));
    }

    private int getIslandPaneDimension(int numberOfSingleIsland){
        int res = 0;
        switch(numberOfSingleIsland){
            case 1 -> res = 150;
            case 2 -> res = 262;
            case 3,4 -> res = 300;
            case  5,6 -> res = 400;
            case 7 -> res = 500;
        }
        return res;
    }

    private void addStudentToSingleIsland(int groupIsland, int singleIsland){
        if(GUI.instance().getModel().getSelectedColour() != null){
            GUI.instance().getActionSender().moveStudentToIsland(GUI.instance().getPlayerName(),GUI.instance().getModel().getSelectedColour(),groupIsland,singleIsland);
        }
        else{
            if(GUI.instance().getModel().getTurnPhase() == TurnPhase.MOVE_STUDENT){
                GUI.instance().getRenderer().showErrorMessage("Select a student!");
            }
        }
        GUI.instance().getModel().setSelectedColour(null);
    }

    private void moveMotherNature(int groupIsland){

        int movement = groupIsland - GUI.instance().getModel().getTable().getMotherNaturePosition();

        if(movement < 0){
            movement += GUI.instance().getModel().getTable().getGroupIslands().size();
        }


        GUI.instance().getActionSender().moveMotherNature(GUI.instance().getPlayerName(), movement);
    }
    private void noAction(){}

}
