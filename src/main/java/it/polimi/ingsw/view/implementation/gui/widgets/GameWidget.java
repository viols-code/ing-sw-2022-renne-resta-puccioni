package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.view.beans.MockPlayer;
import javafx.scene.layout.StackPane;

public class GameWidget extends StackPane {

    private final MockPlayer player;

    public GameWidget(MockPlayer player) {
        this.player = player;

        FXMLUtils.loadWidgetFXML(this);
    }

    public void initializeWidgets(){

    }
}
