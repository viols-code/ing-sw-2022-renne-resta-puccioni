package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.view.beans.MockPlayer;
import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class OtherSchoolBoardWidget extends StackPane {

    @FXML
    private final MockPlayer player;

    public OtherSchoolBoardWidget(MockPlayer player) {
        this.player = player;
        FXMLUtils.loadWidgetFXML(this);
    }

    @FXML
    public void showSchoolBoard(){
        GUI.instance().showPlayerBoard();
    }
}
