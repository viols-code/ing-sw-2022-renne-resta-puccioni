package it.polimi.ingsw.view.implementation.gui.controllers;

import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.image.ImageView;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WizardSelection {
    @FXML
    public ImageView w_1;

    @FXML
    public ImageView w_2;

    @FXML
    public ImageView w_3;

    @FXML
    public ImageView w_4;

    @FXML
    private void type_1(){
        GUI.instance().setWizard(Wizard.TYPE_1);
    }

    @FXML
    private void type_2(){
        GUI.instance().setWizard(Wizard.TYPE_2);
    }

    @FXML
    private void type_3(){
        GUI.instance().setWizard(Wizard.TYPE_3);
    }

    @FXML
    private void type_4(){
        GUI.instance().setWizard(Wizard.TYPE_4);
    }

}
