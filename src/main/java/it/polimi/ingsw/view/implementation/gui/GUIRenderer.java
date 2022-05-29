package it.polimi.ingsw.view.implementation.gui;

import it.polimi.ingsw.view.Renderer;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class GUIRenderer extends Renderer {
    public GUIRenderer(GUI gui) {
        super(gui);
    }

    @Override
    public void showErrorMessage(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);

            alert.showAndWait();
        });
    }

    @Override
    public void showGameMessage(String message) {

    }

    @Override
    public void showLobbyMessage(String message) {

    }

    @Override
    public void printLocalPlayerSchoolBoard() {

    }

    @Override
    public void printLocalPlayerCoins() {

    }

    @Override
    public void printLocalPlayerCurrentAssistantCard() {

    }

    @Override
    public void printAvailableAssistantCards() {

    }

    @Override
    public void printIslands() {

    }

    @Override
    public void printCloudTiles() {

    }

    @Override
    public void printActiveCharacterCard() {

    }

    @Override
    public void printCharacterCards() {

    }

    @Override
    public void printTableCoins() {

    }

    @Override
    public void printTableProfessors() {

    }

    @Override
    public void printOthersCoins(String playerName) {

    }

    @Override
    public void printOthersSchoolBoard(String playerName) {

    }

    @Override
    public void printOthersCurrentAssistantCard(String playerName) {

    }

    @Override
    public void printResult() {

    }

    @Override
    public void help() {

    }
}
