package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.beans.MockPlayer;
import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Locale;
import java.util.Objects;

public class OtherSchoolBoardWidget extends StackPane {

    @FXML
    private final MockPlayer player;

    public OtherSchoolBoardWidget(MockPlayer player) {
        this.player = player;
        FXMLUtils.loadWidgetFXML(this);
    }

    @FXML
    private void initialize(){

    }

    @FXML
    public void showSchoolBoard() {
        GUI.instance().showPlayerBoard();
    }
}
