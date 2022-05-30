package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.util.Objects;

public class CloudTileWidget extends AnchorPane {
    @FXML
    private HBox box;

    @FXML
    private void initialize(){
        for(int i = 0; i < GUI.instance().getNumPlayers(); i++){
            FlowPane flowPane = new FlowPane();
            ImageView imageView = new ImageView();
            flowPane.getChildren().add(imageView);
            box.getChildren().add(flowPane);

            imageView.setImage(new Image(Objects.requireNonNull(CloudTileWidget.class.getResourceAsStream(
                    "/images/cloudTiles/cloud_card_" + GUI.instance().getNumPlayers() + "_" + (i + 1) + ".png")), 40, 40 , false, false));
        }
    }
}
