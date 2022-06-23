package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;
import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Widget that shows the list of colours that can be chosen by the player when the character cards NoColour or ThreeStudents are played
 */
public class NoColourThreeStudentWidget extends StackPane {
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

    @FXML
    Label label;

    /**
     * Creates NoColourThreeStudentWidget
     */
    public NoColourThreeStudentWidget() {
        FXMLUtils.loadWidgetFXML(this);
    }

    /**
     * Init method called by the FXMLLoader
     */
    @FXML
    public void initialize() {

        if (GUI.instance().getModel().getCurrentCharacterCard().getType() == CharacterCardEnumeration.NO_COLOUR) {
            label.setText("Choose the colour you don't want to be considered during the influence computation:");
        } else if (GUI.instance().getModel().getCurrentCharacterCard().getType() == CharacterCardEnumeration.THREE_STUDENT) {
            label.setText("Choose the colour of the three students you want to remove from everybody's dining room:");
        }

        blue.setOnMouseClicked(event -> GUI.instance().getActionSender().setColour(GUI.instance().getPlayerName(), Colour.BLUE));
        green.setOnMouseClicked(event -> GUI.instance().getActionSender().setColour(GUI.instance().getPlayerName(), Colour.GREEN));
        red.setOnMouseClicked(event -> GUI.instance().getActionSender().setColour(GUI.instance().getPlayerName(), Colour.RED));
        yellow.setOnMouseClicked(event -> GUI.instance().getActionSender().setColour(GUI.instance().getPlayerName(), Colour.YELLOW));
        pink.setOnMouseClicked(event -> GUI.instance().getActionSender().setColour(GUI.instance().getPlayerName(), Colour.PINK));
    }

    /**
     * This method is called when the button "Go back to Character Cards" is clicked and it brings the player back to the character cards
     */
    @FXML
    public void showCharacterCards() {
        GUI.instance().showCharacterCards();
    }

}
