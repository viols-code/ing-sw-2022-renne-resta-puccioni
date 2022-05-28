package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class CharacterCardsWidget extends StackPane {
    @FXML
    private HBox box;

    public CharacterCardsWidget() {
        FXMLUtils.loadWidgetFXML(this);
    }

    @FXML
    public void initialize() {
        for (int i = 0; i < 3; i++) {
            FlowPane flowPane = new FlowPane();
            box.getChildren().add(flowPane);
            flowPane.getStyleClass().add("assistantCards");
            flowPane.setMaxHeight(300.0);
            flowPane.setMaxWidth(196.5);
            ImageView imageView = new ImageView();
            flowPane.getChildren().add(imageView);

            imageView.setFitHeight(300.0);
            imageView.setFitWidth(196.5);

            int a = i;
            imageView.setOnMouseClicked(event -> playCharacterCard(a));
            HBox.setMargin(flowPane, new Insets(5.0, 5.0, 5.0, 5.0));


            imageView.setImage(new Image(Objects.requireNonNull(CharacterCardsWidget.class.getResourceAsStream(
                    "/images/characterCards/" + GUI.instance().getModel().getCharacterCardByIndex(i).getType().name() + ".jpg"))));
        }
    }

    @FXML
    public void playCharacterCard(int i) {
        GUI.instance().getActionSender().playCharacterCard(GUI.instance().getPlayerName(), i);
    }

    @FXML
    public void showSchoolBoard() {
        GUI.instance().showPlayerBoard();
    }
}
