package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

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
                AnchorPane anchorPane = new AnchorPane();
                ImageView imageView = new ImageView();
                islandPane.getChildren().add(imageView);
                imageView.setFitWidth(200);
                imageView.setFitHeight(200);
                imageView.setImage(new Image(Objects.requireNonNull(AssistantCardsWidget.class.getResourceAsStream(
                        "/images/islands/island2.png"))));
                anchorPane.setLayoutX(singleIslandsCoordinates.get(k).getRow());
                anchorPane.setLayoutY(singleIslandsCoordinates.get(k).getColumn());
                imageView.setLayoutX(0);
                imageView.setLayoutY(0);

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
        return new ArrayList<>(Arrays.asList(new Coordinates(0,0)));
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
}
