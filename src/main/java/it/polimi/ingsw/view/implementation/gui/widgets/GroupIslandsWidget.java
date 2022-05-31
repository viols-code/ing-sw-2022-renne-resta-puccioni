package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.application.Platform;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GroupIslandsWidget extends StackPane {

    @FXML
    private AnchorPane anchorPane;

    private List<Coordinates> groupIslandBoxes;

    public GroupIslandsWidget() {
        FXMLUtils.loadWidgetFXML(this);
    }

    @FXML
    public void showSchoolBoard() {
        GUI.instance().showPlayerBoard();
    }

    public void initialize(){
        groupIslandBoxes = new ArrayList<>(Arrays.asList(new Coordinates(11,268), new Coordinates(110,131), new Coordinates(245,40),new Coordinates(425,-6), new Coordinates(601,40),new Coordinates(753,131),new Coordinates(853,268),new Coordinates(753,410),new Coordinates(601,490),new Coordinates(429,524),new Coordinates(245,490),new Coordinates(99,410)));
        int groupIslands = GUI.instance().getModel().getTable().getGroupIslands().size();
        int singleIslands;
        List<Coordinates> singleIslandsCoordinates;
        for(int i = 0,j=0; i < groupIslands; i++){
            singleIslands = GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getIslands().size();

            //creates the flow pane for the group island
            AnchorPane islandPane = new AnchorPane();
            islandPane.setPrefWidth(getIslandPaneDimension(singleIslands));
            islandPane.setPrefHeight(getIslandPaneDimension(singleIslands));
            anchorPane.getChildren().add(islandPane);

            singleIslandsCoordinates = getIslandsCoordinates(singleIslands);
            for(int k = 0; k < singleIslands; k++){

                //creates the anchor pane for the single island
                AnchorPane singleIslandPane = new AnchorPane();
                islandPane.getChildren().add(singleIslandPane);
                singleIslandPane.setLayoutX(singleIslandsCoordinates.get(k).getRow());
                singleIslandPane.setLayoutY(singleIslandsCoordinates.get(k).getColumn());
                int groupIslandIndex = i;
                int singleIslandIndex = k;


                //create the image view to for the single island
                ImageView imageView = new ImageView();
                singleIslandPane.getChildren().add(imageView);
                singleIslandPane.getStyleClass().add("singleIsland");
                imageView.setFitWidth(200);
                imageView.setFitHeight(200);
                imageView.setImage(new Image(Objects.requireNonNull(AssistantCardsWidget.class.getResourceAsStream(
                        "/images/islands/island2.png"))));
                imageView.setLayoutX(0);
                imageView.setLayoutY(0);
                //imageView.toBack();
                singleIslandPane.setOnMouseClicked(event -> addStudentToSingleIsland(groupIslandIndex,singleIslandIndex));

                //adds the students
                Label greenStudents = new Label();
                Label redStudents = new Label();
                Label yellowStudents = new Label();
                Label pinkStudents = new Label();
                Label blueStudents = new Label();
                GridPane studentsOnSingleIsland= new GridPane();
                singleIslandPane.getChildren().add(studentsOnSingleIsland);
                studentsOnSingleIsland.setPrefWidth(100);
                studentsOnSingleIsland.setPrefHeight(100);
                studentsOnSingleIsland.toFront();
                studentsOnSingleIsland.setLayoutX(50);
                studentsOnSingleIsland.setLayoutY(50);
                greenStudents.setBackground(new Background(new BackgroundFill(Color.rgb(0,255,0),CornerRadii.EMPTY, Insets.EMPTY)));
                yellowStudents.setBackground(new Background(new BackgroundFill(Color.rgb(255, 204, 0),CornerRadii.EMPTY, Insets.EMPTY)));
                redStudents.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0),CornerRadii.EMPTY, Insets.EMPTY)));
                pinkStudents.setBackground(new Background(new BackgroundFill(Color.rgb(255, 102, 204),CornerRadii.EMPTY, Insets.EMPTY)));
                blueStudents.setBackground(new Background(new BackgroundFill(Color.rgb(0, 204, 255),CornerRadii.EMPTY, Insets.EMPTY)));
                studentsOnSingleIsland.setVisible(true);
                studentsOnSingleIsland.addRow(0,greenStudents);
                studentsOnSingleIsland.addRow(1,redStudents);
                studentsOnSingleIsland.addRow(2,yellowStudents);
                studentsOnSingleIsland.addRow(3,pinkStudents);
                studentsOnSingleIsland.addRow(4,blueStudents);

                //addsStudents
                greenStudents.setText("" + GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getSingleIslandByIndex(k).getStudents(Colour.GREEN));
                redStudents.setText("" + GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getSingleIslandByIndex(k).getStudents(Colour.RED));
                yellowStudents.setText("" + GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getSingleIslandByIndex(k).getStudents(Colour.YELLOW));
                pinkStudents.setText("" + GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getSingleIslandByIndex(k).getStudents(Colour.PINK));
                blueStudents.setText("" + GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getSingleIslandByIndex(k).getStudents(Colour.BLUE));
                //adds mother nature
                Circle motherNature = new Circle();
                motherNature.setRadius(10);
                singleIslandPane.getChildren().add(motherNature);
                motherNature.setLayoutX(150);
                motherNature.setLayoutY(100);
                motherNature.toFront();
                motherNature.setFill(Color.rgb(255, 102, 0));
                motherNature.setVisible(GUI.instance().getModel().getTable().getGroupIslandByIndex(i).isMotherNature() && k == 0);

                //adds a listener to all the single islands
                GUI.instance().getModel().getTable().getGroupIslandByIndex(i).getSingleIslandByIndex(k).getStudentsProperty().addListener((MapChangeListener<? super Colour, ? super Integer>) listener ->
                        Platform.runLater(() -> {
                            //addsStudents
                            greenStudents.setText("" + listener.getMap().get(Colour.GREEN));
                            redStudents.setText("" + listener.getMap().get(Colour.RED));
                            yellowStudents.setText("" + listener.getMap().get(Colour.YELLOW));
                            pinkStudents.setText("" + listener.getMap().get(Colour.PINK));
                            blueStudents.setText("" + listener.getMap().get(Colour.BLUE));
                        }));

                int groupIsland = i;

                GUI.instance().getModel().getTurnPhaseProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {

                        if(GUI.instance().getModel().getTurnPhase().equals(TurnPhase.MOVE_MOTHER_NATURE)){
                            islandPane.setOnMouseClicked(event -> moveMotherNature(groupIsland));
                        }
                        }));
            }


            //sets the layout
            islandPane.setLayoutX(groupIslandBoxes.get(j).getRow());
            islandPane.setLayoutY(groupIslandBoxes.get(j).getColumn());
            j += singleIslands;
        }
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
        return new ArrayList<>(Arrays.asList(new Coordinates(0,0),new Coordinates(110,80)));
    }

    private List<Coordinates> getCoordinatesForThreeSingleIslands(){
        return new ArrayList<>(Arrays.asList(new Coordinates(-12,23),new Coordinates(113,-9),new Coordinates(88,110)));
    }

    private List<Coordinates> getCoordinatesForFourGroupIslands(){
        return new ArrayList<>(Arrays.asList(new Coordinates(0,100),new Coordinates(96,0),new Coordinates(206,79), new Coordinates(113,179)));
    }

    private List<Coordinates> getCoordinatesForFiveGroupIslands(){
        return new ArrayList<>(Arrays.asList(new Coordinates(50,14),new Coordinates(166,77),new Coordinates(286,150), new Coordinates(50,141),new Coordinates(166,214)));
    }

    private List<Coordinates> getCoordinatesForSixGroupIslands(){
        return new ArrayList<>(Arrays.asList(new Coordinates(50,14),new Coordinates(166,77),new Coordinates(286,150), new Coordinates(50,141),new Coordinates(166,214),new Coordinates(56,266)));
    }

    private List<Coordinates> getCoordinatesForSevenGroupIslands(){
        return new ArrayList<>(Arrays.asList(new Coordinates(50,14),new Coordinates(166,77),new Coordinates(286,150), new Coordinates(50,141),new Coordinates(166,214),new Coordinates(56,266),new Coordinates(277,286)));
    }

    private int getIslandPaneDimension(int numberOfSingleIsland){
        int res = 0;
        switch(numberOfSingleIsland){
            case 1 -> res = 200;
            case 2,3 -> res = 300;
            case 4 -> res = 400;
            case 5,6,7 -> res = 500;
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
}
