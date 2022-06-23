package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Widget that represents the deck of assistant cards
 */
public class AssistantCardsWidget extends StackPane {
    @FXML
    private VBox vbox;

    /**
     * List containing the imageViews of the assistant cards
     */
    private final List<ImageView> images = new ArrayList<>();

    /**
     * Creates the AssistantCardWidget
     */
    public AssistantCardsWidget() {
        FXMLUtils.loadWidgetFXML(this);
    }

    /**
     * Init method called by the FXMLLoader
     * It calls all the init method of this class
     */
    @FXML
    private void initialize() {
        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();

        for (int i = 1; i < 11; i++) {
            FlowPane flowPane = new FlowPane();
            flowPane.getStyleClass().add("assistantCards");
            flowPane.setMaxHeight(159.9);
            flowPane.setMaxWidth(109.2);
            ImageView imageView = new ImageView();
            flowPane.getChildren().add(imageView);
            images.add(imageView);

            imageView.setFitHeight(159.9);
            imageView.setFitWidth(109.2);
            imageView.setImage(new Image(Objects.requireNonNull(AssistantCardsWidget.class.getResourceAsStream(
                    "/images/assistantCards/assistant_" + (i - 1) + ".png"))));
            int a = i - 1;
            imageView.setOnMouseClicked(event -> playAssistantCard(a));
            if (i <= 5) {
                hBox1.getChildren().add(flowPane);
            } else {
                hBox2.getChildren().add(flowPane);
            }

            HBox.setMargin(flowPane, new Insets(5.0, 5.0, 5.0, 5.0));

            //If the card has already been played by the player the image of that card becomes blurred
            if (!GUI.instance().getModel().getLocalPlayer().getCardsProperty().containsKey(a)) {
                imageView.setOpacity(0.5);
            }
        }

        vbox.getChildren().add(hBox1);
        vbox.getChildren().add(hBox2);
        hBox1.alignmentProperty().setValue(Pos.CENTER);
        hBox1.prefWidth(179.9);
        hBox1.prefHeight(546.0);
        hBox2.alignmentProperty().setValue(Pos.CENTER);
        hBox2.prefWidth(179.9);
        hBox2.prefHeight(546.0);

        //If the card has already been played by the player the image of that card becomes blurred
        GUI.instance().getModel().getLocalPlayer().getCardsProperty().addListener(((MapChangeListener<? super Integer, ? super AssistantCard>) change -> Platform.runLater(() -> {
            if (change.wasRemoved()) {
                images.get(change.getKey()).setOpacity(0.5);
            }
        })));
    }

    /**
     * The method called when the player clicks on the button "BACK TO SCHOOL BOARD"
     */
    @FXML
    public void showSchoolBoard() {
        GUI.instance().showPlayerBoard();
    }

    @FXML
    public void playAssistantCard(int i) {
        GUI.instance().getActionSender().playAssistantCard(GUI.instance().getPlayerName(), i);
    }

}
