package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.beans.MockCloudTile;
import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CloudTileWidget extends StackPane {
    @FXML
    private HBox box;

    public CloudTileWidget() {
        FXMLUtils.loadWidgetFXML(this);
    }

    @FXML
    private void initialize() {
        initializeCloudTileImages();

        GUI.instance().getModel().getTable().getShownCloudTilesProperty().addListener((ListChangeListener<? super MockCloudTile>) change ->
                Platform.runLater(() -> {
                  //  box.getChildren().forEach(node -> node.setVisible(false));
                    box.getChildren().removeAll();

                    initializeCloudTileImages();
                }));
    }

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

    public void initializeCloudTileImages(){
        for (int i = 0; i < GUI.instance().getModel().getTable().getShownCloudTiles().size(); i++) {
            AnchorPane anchorPane = new AnchorPane();
            ImageView imageView = new ImageView();
            anchorPane.getChildren().add(imageView);
            box.getChildren().add(anchorPane);
            anchorPane.getStyleClass().add("cloudTile");
            HBox.setMargin(anchorPane, new Insets(10.0, 10.0, 10.0, 10.0));
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

    @FXML
    private void showSchoolBoard() {
        GUI.instance().showPlayerBoard();
    }
}
