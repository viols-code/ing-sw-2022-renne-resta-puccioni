package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.view.beans.MockCloudTile;
import it.polimi.ingsw.view.implementation.gui.GUI;
import it.polimi.ingsw.view.implementation.gui.widgets.utils.Coordinates;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.*;

/**
 * Widget that models the cloud tiles
 */
public class CloudTileWidget extends StackPane {
    @FXML
    private AnchorPane pane;

    /**
     * Creates a CloudTileWidget
     */
    public CloudTileWidget() {
        FXMLUtils.loadWidgetFXML(this);
    }

    /**
     * A List containing the AnchorPane in which the cloud tiles are placed
     */
    private final List<AnchorPane> cloudTiles = new ArrayList<>();

    /**
     * Init method called by the FXMLLoader
     * It calls all the init method of this class
     */
    @FXML
    private void initialize() {
        initializeCloudTileImages();
        initWinner();

        //adds a listener to the shown cloud tiles
        GUI.instance().getModel().getTable().getShownCloudTilesProperty().addListener((ListChangeListener<? super MockCloudTile>) change ->
                Platform.runLater(() -> {
                    for (AnchorPane anchorPane : cloudTiles) {
                        anchorPane.getChildren().clear();
                    }
                    pane.getChildren().removeAll(cloudTiles);
                    cloudTiles.clear();
                    initializeCloudTileImages();
                }));
    }

    /**
     * Places the students images on the cloud tile given
     *
     * @param anchorPane the anchorPane of the cloud tile
     * @param i          the index of the cloud tile
     * @param x          list of x coordinates
     * @param y          list of y coordinates
     */
    public void initializeCloudTileStudents(AnchorPane anchorPane, int i, List<Integer> x, List<Integer> y) {
        int c = 0;
        for (Colour colour : Colour.values()) {
            for (int j = 0; j < GUI.instance().getModel().getTable().getShownCloudTiles().get(i).getMockCloudTile().get(colour); j++) {
                ImageView student = new ImageView();
                student.setImage(new Image(Objects.requireNonNull(CloudTileWidget.class.getResourceAsStream(
                        "/images/students/student_" + colour.name().toLowerCase(Locale.ROOT) + ".png")), 80, 80, false, false));
                anchorPane.getChildren().add(student);
                student.setLayoutX(x.get(c));
                student.setLayoutY(y.get(c));
                c++;
            }
        }
    }

    /**
     * Places the cloud tiles
     */
    public void initializeCloudTileImages() {
        List<Coordinates> cloud1 = new ArrayList<>(List.of(new Coordinates(440, 260)));
        List<Coordinates> cloud2 = new ArrayList<>(Arrays.asList(new Coordinates(320, 260), new Coordinates(620, 260)));
        List<Coordinates> cloud3 = new ArrayList<>(Arrays.asList(new Coordinates(140, 260), new Coordinates(440, 260), new Coordinates(740, 260)));

        for (int i = 0; i < GUI.instance().getModel().getTable().getShownCloudTiles().size(); i++) {
            AnchorPane anchorPane = new AnchorPane();
            pane.getChildren().add(anchorPane);
            cloudTiles.add(anchorPane);
            ImageView imageView = new ImageView();
            anchorPane.getChildren().add(imageView);
            anchorPane.getStyleClass().add("cloudTile");

            switch (GUI.instance().getModel().getTable().getShownCloudTiles().size()) {
                case (1) -> {
                    anchorPane.setLayoutX(cloud1.get(i).getRow());
                    anchorPane.setLayoutY(cloud1.get(i).getColumn());
                }
                case (2) -> {
                    anchorPane.setLayoutX(cloud2.get(i).getRow());
                    anchorPane.setLayoutY(cloud2.get(i).getColumn());
                }
                case (3) -> {
                    anchorPane.setLayoutX(cloud3.get(i).getRow());
                    anchorPane.setLayoutY(cloud3.get(i).getColumn());
                }
            }

            int a = i;
            anchorPane.setOnMouseClicked(event -> chooseCloudTile(a));

            List<Integer> x = new ArrayList<>();
            List<Integer> y = new ArrayList<>();
            if (GUI.instance().getNumPlayers() == 2) {
                x.add(1);
                x.add(79);
                x.add(103);
                y.add(49);
                y.add(120);
                y.add(19);
                anchorPane.setMaxHeight(200);
                anchorPane.setMaxWidth(200);
                imageView.setImage(new Image(Objects.requireNonNull(CloudTileWidget.class.getResourceAsStream(
                        "/images/cloudTiles/cloud_card_" + GUI.instance().getNumPlayers()
                                + "_" + (i + 1) + ".png")), 200, 200, false, false));

            } else {
                x.add(20);
                x.add(150);
                x.add(20);
                x.add(150);
                y.add(49);
                y.add(49);
                y.add(140);
                y.add(140);
                anchorPane.setMaxHeight(250);
                anchorPane.setMaxWidth(250);
                imageView.setImage(new Image(Objects.requireNonNull(CloudTileWidget.class.getResourceAsStream(
                        "/images/cloudTiles/cloud_card_" + GUI.instance().getNumPlayers()
                                + "_" + (i + 1) + ".png")), 250, 250, false, false));

            }
            initializeCloudTileStudents(anchorPane, i, x, y);
        }
    }


    @FXML
    private void chooseCloudTile(int i) {
        GUI.instance().getActionSender().chooseCloudTile(GUI.instance().getPlayerName(), i);
    }

    /**
     * This method is called when the player clicks on the button "GO BACK TO SCHOOL BOARD". It brings the player back to the school board view
     */
    @FXML
    private void showSchoolBoard() {
        GUI.instance().showPlayerBoard();
    }

    /**
     * A method which checks if the game has ended (if the turn phase is ENDGAME) and, if so, calls the method whic shows the winner
     */
    private void initWinner() {

        if (GUI.instance().getModel().getTurnPhase().equals(TurnPhase.ENDGAME)) {
            printWinner();
        }

        //Adds a listener to the turn phase in order to know if the game has ended
        GUI.instance().getModel().getTurnPhaseProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {

            if (GUI.instance().getModel().getTurnPhase().equals(TurnPhase.ENDGAME)) {
                printWinner();
            }
        }));
    }

    /**
     * The layout of the images that appear once the game has ended to show who is the winner
     */
    private void printWinner() {
        if (GUI.instance().getModel().getWinner().getNickname().equals(GUI.instance().getPlayerName())) {
            ImageView winner = new ImageView();
            pane.getChildren().add(winner);
            winner.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                    "/images/winner.png"))));
            winner.setLayoutY(153);
            winner.setLayoutX(263);
            winner.setFitWidth(547);
            winner.setFitHeight(395);
        } else {
            ImageView loser = new ImageView();
            pane.getChildren().add(loser);
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
            pane.getChildren().add(winnerName);
        }
    }
}
