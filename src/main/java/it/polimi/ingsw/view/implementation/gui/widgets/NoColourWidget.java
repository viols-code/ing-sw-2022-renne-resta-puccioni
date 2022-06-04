package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class NoColourWidget extends StackPane {
    @FXML
    ImageView blue;

    @FXML
    ImageView green;

    @FXML
    ImageView red;

    @FXML
    ImageView yellow;

    @FXML
    ImageView pink;

    public NoColourWidget() {
        FXMLUtils.loadWidgetFXML(this);
    }

    @FXML
    public void initialize() {
        blue.setOnMouseClicked(event -> GUI.instance().getActionSender().setColour(GUI.instance().getPlayerName(), Colour.BLUE));
        green.setOnMouseClicked(event -> GUI.instance().getActionSender().setColour(GUI.instance().getPlayerName(), Colour.GREEN));
        red.setOnMouseClicked(event -> GUI.instance().getActionSender().setColour(GUI.instance().getPlayerName(), Colour.RED));
        yellow.setOnMouseClicked(event -> GUI.instance().getActionSender().setColour(GUI.instance().getPlayerName(), Colour.YELLOW));
        pink.setOnMouseClicked(event -> GUI.instance().getActionSender().setColour(GUI.instance().getPlayerName(), Colour.PINK));
    }

    @FXML
    public void showCharacterCards() {
        GUI.instance().showCharacterCards();
    }

}
