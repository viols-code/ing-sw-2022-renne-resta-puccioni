package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class CharacterCardsWidget extends StackPane {

    public CharacterCardsWidget() {
        FXMLUtils.loadWidgetFXML(this);
    }

    @FXML
    public void showSchoolBoard(){
        GUI.instance().showPlayerBoard();
    }
}
