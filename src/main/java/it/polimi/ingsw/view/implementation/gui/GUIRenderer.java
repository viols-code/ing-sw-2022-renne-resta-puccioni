package it.polimi.ingsw.view.implementation.gui;

import it.polimi.ingsw.view.Renderer;
import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 * Class responsible for game components rendering
 */
public class GUIRenderer extends Renderer {

    /**
     * Constructs the renderer
     *
     * @param gui the gui related to this renderer
     */
    public GUIRenderer(GUI gui) {
        super(gui);
    }

    /**
     * Shows an error message
     *
     * @param message the message to show
     */
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

    /**
     * Shows a game message
     *
     * @param message the message to show
     */
    @Override
    public void showGameMessage(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Info");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.setHeight(500);
            alert.setWidth(500);
            alert.showAndWait();
        });
    }

    /**
     * Shows a lobby message
     *
     * @param message the message to show
     */
    @Override
    public void showLobbyMessage(String message) {

    }

    /**
     * Prints the school board of the local player
     */
    @Override
    public void printLocalPlayerSchoolBoard() {

    }

    /**
     * Prints the coins of the local player
     */
    @Override
    public void printLocalPlayerCoins() {

    }

    /**
     * Prints the current assistant card of the local layer
     */
    @Override
    public void printLocalPlayerCurrentAssistantCard() {

    }

    /**
     * Prints the available assistant cards
     */
    @Override
    public void printAvailableAssistantCards() {

    }

    /**
     * Prints the islands
     */
    @Override
    public void printIslands() {

    }

    /**
     * Prints the available cloud tiles
     */
    @Override
    public void printCloudTiles() {

    }

    /**
     * Prints the active character card
     */
    @Override
    public void printActiveCharacterCard() {

    }

    /**
     * Prints the character cards
     */
    @Override
    public void printCharacterCards() {

    }

    /**
     * Prints the available table coins
     */
    @Override
    public void printTableCoins() {

    }

    /**
     * Prints the professors available on the table
     */
    @Override
    public void printTableProfessors() {

    }

    /**
     * Prints the coins of another player
     *
     * @param playerName the nickname of the player
     */
    @Override
    public void printOthersCoins(String playerName) {

    }

    /**
     * Prints the school board of another player
     *
     * @param playerName the nickname of the player
     */
    @Override
    public void printOthersSchoolBoard(String playerName) {

    }

    /**
     * Prints the current assistant card of another player
     *
     * @param playerName the nickname of the player
     */
    @Override
    public void printOthersCurrentAssistantCard(String playerName) {

    }

    /**
     * Prints the results of the game
     */
    @Override
    public void printResult() {

    }

    /**
     * Prints the available commands (useful for the CLI)
     */
    @Override
    public void help() {

    }
}
