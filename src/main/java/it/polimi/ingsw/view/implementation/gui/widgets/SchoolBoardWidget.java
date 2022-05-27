package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.view.beans.MockPlayer;
import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.image.Image;

public class SchoolBoardWidget {

    @FXML
    private Image studentEntrance1;

    private MockPlayer localPlayer;

    private void initialize(){
        localPlayer = GUI.instance().getModel().getLocalPlayer();


    }
}
