package it.polimi.ingsw.view.implementation.gui.controllers;

import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class WizardSelection {

    @FXML
    private ListView<Wizard> wizards;

    Wizard[] types = {Wizard.TYPE_1, Wizard.TYPE_2, Wizard.TYPE_3, Wizard.TYPE_4};

    @FXML
    private void initialize() {
       wizards.getItems().addAll(types);

       wizards.getSelectionModel().selectedItemProperty().addListener((observableValue, wizard, t1) ->
               GUI.instance().setWizard(wizards.getSelectionModel().getSelectedItem()));
    }

}
