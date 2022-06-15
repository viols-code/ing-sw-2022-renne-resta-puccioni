package it.polimi.ingsw.view.implementation.gui.controllers;

import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.implementation.gui.GUI;
import it.polimi.ingsw.view.implementation.gui.widgets.AssistantCardsWidget;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WizardSelection extends BorderPane {
    @FXML
    private HBox box;

    private final List<ImageView> images = new ArrayList<>();

    @FXML
    public void initialize() {
        for (int i = 1; i < 5; i++) {
            FlowPane flowPane = new FlowPane();
            box.getChildren().add(flowPane);
            flowPane.getStyleClass().add("wizard");
            flowPane.setMaxHeight(299.6);
            flowPane.setMaxWidth(197.6);
            ImageView imageView = new ImageView();
            flowPane.getChildren().add(imageView);
            images.add(imageView);

            imageView.setFitHeight(299.6);
            imageView.setFitWidth(197.6);

            imageView.setImage(new Image(Objects.requireNonNull(AssistantCardsWidget.class.getResourceAsStream(
                    "/images/wizard/wizard" + (i) + ".png"))));

            int a = i;
            imageView.setOnMouseClicked(event -> chooseWizard(a));
            HBox.setMargin(flowPane, new Insets(5.0, 5.0, 5.0, 5.0));

            if (GUI.instance().getTakenWizards() != null) {
                if (GUI.instance().getTakenWizards().contains(Wizard.valueOf(a))) {
                    imageView.setOpacity(0.5);
                }
            }

        }

        GUI.instance().getTakenWizardsProperty().addListener((ListChangeListener<? super Wizard>) change -> Platform.runLater(() -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Wizard wizard : change.getAddedSubList()) {
                        if (wizard == Wizard.TYPE_1) {
                            images.get(0).setOpacity(0.5);
                        }
                        if (wizard == Wizard.TYPE_2) {
                            images.get(1).setOpacity(0.5);
                        }
                        if (wizard == Wizard.TYPE_3) {
                            images.get(2).setOpacity(0.5);
                        }
                        if (wizard == Wizard.TYPE_4) {
                            images.get(3).setOpacity(0.5);
                        }
                    }
                }
            }
        }));
    }

    @FXML
    private void chooseWizard(int i) {
        GUI.instance().setWizard(Wizard.valueOf(i));
    }

}
