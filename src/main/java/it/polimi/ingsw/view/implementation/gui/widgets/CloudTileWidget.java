package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
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
        for (int i = 0; i < GUI.instance().getModel().getTable().getShownCloudTiles().size(); i++) {
            FlowPane flowPane = new FlowPane();
            ImageView imageView = new ImageView();
            flowPane.getChildren().add(imageView);
            box.getChildren().add(flowPane);
            flowPane.setMaxHeight(200);
            flowPane.setMaxWidth(200);
            flowPane.getStyleClass().add("cloudTile");
            HBox.setMargin(flowPane, new Insets(10.0, 10.0, 10.0, 10.0));
            imageView.setImage(new Image(Objects.requireNonNull(CloudTileWidget.class.getResourceAsStream(
                    "/images/cloudTiles/cloud_card_" + GUI.instance().getNumPlayers()
                            + "_" + (i + 1) + ".png")), 200, 200, false, false));
            int a = i;
            imageView.setOnMouseClicked(event -> chooseCloudTile(a));

            if (GUI.instance().getNumPlayers() == 2) {
                int c = 0;
                List<Integer> x = new ArrayList<>();
                List<Integer> y = new ArrayList<>();
                x.add(10);
                x.add(20);
                x.add(30);
                y.add(20);
                y.add(10);
                y.add(30);

                for (Colour colour : Colour.values()) {
                    for (int j = 0; j < GUI.instance().getModel().getTable().getShownCloudTiles().get(i).getMockCloudTile().get(colour); j++) {
                        ImageView student = new ImageView();
                        student.setImage(new Image(Objects.requireNonNull(CloudTileWidget.class.getResourceAsStream(
                                "/images/students/student_" + colour.name().toLowerCase(Locale.ROOT) + ".png")), 80, 80, false, false));
                        flowPane.getChildren().add(student);
                        student.setLayoutX(x.get(c));
                        student.setLayoutY(y.get(c));
                        c++;
                    }
                }
            }
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
