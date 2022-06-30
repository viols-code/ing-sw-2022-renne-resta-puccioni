package it.polimi.ingsw.view.implementation.gui;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.beans.MockPlayer;
import it.polimi.ingsw.view.implementation.gui.widgets.*;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;

/**
 * The singleton class that manages all the functionalities of the GUI.
 */
public class GUI extends View {
    private final Stage stage;
    private Scene scene;
    private static GUI instance;
    private static SchoolBoardWidget schoolBoardWidget;
    private static GroupIslandsWidget groupIslandsWidget;
    private static AssistantCardsWidget assistantCardsWidget;
    private static CharacterCardsWidget characterCardsWidget;
    private boolean guidedMode;

    /**
     * Constructor of a new GUI.
     *
     * @param client the client that is the owner of this gui
     * @param stage  the main stage of the application
     */
    public GUI(Client client, Stage stage) {
        super(client);
        this.stage = stage;

        this.setModelUpdateHandler(new GUIModelUpdateHandler(this));
        this.setRenderer(new GUIRenderer(this));
        this.setActionSender(new GUIActionSender(this));
        instance = this;
        schoolBoardWidget = null;
        groupIslandsWidget = null;
        characterCardsWidget = null;
        assistantCardsWidget = null;
        guidedMode = false;
    }

    /**
     * Gets the singleton instance.
     *
     * @return the singleton instance
     */
    public static GUI instance() {
        return instance;
    }

    /**
     * Handles the interaction with the user
     */
    @Override
    public void run() {
        Parent homePage = FXMLUtils.loadFXML("/gui/Home");
        scene = new Scene(homePage);
        stage.setScene(scene);
        stage.setTitle("Eriantys");
        stage.setResizable(false);
        stage.show();

        stage.setOnCloseRequest(windowEvent -> getClient().terminate());
    }

    /**
     * Handles the successful connection to a server lobby.
     *
     * @param isFirstConnection true if this client is the first to connect to the lobby, false otherwise
     * @param takenWizard       the list of wizard already taken
     */
    @Override
    public void addToLobby(boolean isFirstConnection, List<Wizard> takenWizard) {
        super.addToLobby(isFirstConnection, takenWizard);
    }

    /**
     * Handles the successful connection to the waiting room.
     */
    @Override
    public void correctConnection() {
        Platform.runLater(() -> {
            Parent nameSelectionPage = FXMLUtils.loadFXML("/gui/NicknameSelection");
            scene.setRoot(nameSelectionPage);
        });
    }

    /**
     * Handles the successful reconnection of the player
     */
    @Override
    public void correctReconnection(){
        showPlayerBoard();
    }

    /**
     * Handles the successful nickname setting
     *
     * @param nickname       the nickname chosen by the local player
     * @param takenNicknames the nicknames chosen by the players connected to the lobby
     */
    @Override
    public void handleCorrectNickname(String nickname, List<String> takenNicknames) {
        super.handleCorrectNickname(nickname, takenNicknames);

        if (nickname.equals(getPlayerName())) {
            Platform.runLater(() -> {
                Parent wizardSelection = FXMLUtils.loadFXML("/gui/WizardSelection");
                scene.setRoot(wizardSelection);
            });
        }
    }

    /**
     * Handles the successful wizard setting
     *
     * @param wizard the wizard chosen by the local player
     */
    @Override
    public void handleCorrectWizard(Wizard wizard) {
        super.handleCorrectWizard(wizard);

        if (wizard.equals(getPlayerWizard())) {
            if (isLobbyMaster()) {
                Platform.runLater(() -> {
                    Parent gameModeSelection = FXMLUtils.loadFXML("/gui/GameModeSelection");
                    scene.setRoot(gameModeSelection);
                });
            } else {
                Platform.runLater(() -> {
                    Parent waitingPlayers = FXMLUtils.loadFXML("/gui/WaitingPlayers");
                    scene.setRoot(waitingPlayers);
                });
            }
        }
    }

    /**
     * Handles the successful setting of the game mode (basic or expert)
     *
     * @param gameMode a boolean which is true if the game mode set is expert, false if it's basic
     */
    @Override
    public void handleGameMode(boolean gameMode) {
        super.handleGameMode(gameMode);

        if (isLobbyMaster()) {
            Platform.runLater(() -> {
                Parent playersToStartSelection = FXMLUtils.loadFXML("/gui/PlayersToStartSelection");
                scene.setRoot(playersToStartSelection);
            });
        }

    }

    /**
     * Handles the successful setting of the number of players
     *
     * @param playersToStart the number of players set to start the game
     */
    @Override
    public void handleSetPlayersToStart(int playersToStart) {
        super.handleSetPlayersToStart(playersToStart);
        Platform.runLater(() -> {
            Parent waitingPlayers = FXMLUtils.loadFXML("/gui/WaitingPlayers");
            scene.setRoot(waitingPlayers);
        });
    }

    /**
     * Handles the connection of another player to the lobby.
     *
     * @param playerName     the name of the player that connected
     * @param wizard         the wizard that the player has chosen
     * @param currentPlayers the amount of players connected to the lobby
     * @param playersToStart the number of players required to start the game
     * @param takenWizard    the list of wizard already taken
     */
    @Override
    public void handlePlayerConnect(String playerName, Wizard wizard, int currentPlayers, Integer playersToStart, List<Wizard> takenWizard) {
        super.handlePlayerConnect(playerName, wizard, currentPlayers, playersToStart, takenWizard);

        getModel().addPlayerNickname(playerName);
    }

    /**
     * Handles the successful connection of all players and prepares the mock model for the game
     *
     * @param players    a hash map that associates each nickname with the wizard chosen by the player
     * @param gameMode   a boolean which is true if the game mode set is expert, false if it's basic
     * @param numPlayers the number of players in the game
     */
    @Override
    public void handleAllPlayersConnected(HashMap<String, Wizard> players, boolean gameMode, int numPlayers) {
        super.handleAllPlayersConnected(players, gameMode, numPlayers);
    }

    /**
     * Shows the local player's PlayerBoard.
     */
    public void showPlayerBoard() {
        Platform.runLater(() -> {
            if (schoolBoardWidget == null) {
                schoolBoardWidget = new SchoolBoardWidget();
            }
            scene.setRoot(schoolBoardWidget);
        });
    }

    /**
     * Shows the other player board
     *
     * @param player the player that is the owner of the school board
     */
    public void showOtherPlayerBoard(MockPlayer player) {
        Platform.runLater(() -> {
            OtherSchoolBoardWidget otherSchoolBoard = new OtherSchoolBoardWidget(player);
            scene.setRoot(otherSchoolBoard);
        });
    }

    /**
     * Shows the exchangeEntranceDiningRoom page
     */
    public void showExchangeEntranceDiningRoom() {
        Platform.runLater(() -> {
            ExchangeEntranceDiningRoomWidget exchangeEntranceDiningRoom = new ExchangeEntranceDiningRoomWidget();
            scene.setRoot(exchangeEntranceDiningRoom);
        });
    }


    /**
     * Shows the cloud tiles
     */
    public void showCloudTile() {
        Platform.runLater(() -> {
            CloudTileWidget cloudTileWidget = new CloudTileWidget();
            scene.setRoot(cloudTileWidget);
        });
    }

    /**
     * Shows the islands
     */
    public void showIslands() {
        Platform.runLater(() -> {
            if (groupIslandsWidget == null) {
                groupIslandsWidget = new GroupIslandsWidget();
            }
            scene.setRoot(groupIslandsWidget);
        });
    }

    /**
     * Shows the assistant cards
     */
    public void showAssistantCards() {
        Platform.runLater(() -> {
            if (assistantCardsWidget == null) {
                assistantCardsWidget = new AssistantCardsWidget();
            }
            scene.setRoot(assistantCardsWidget);
        });
    }

    /**
     * Shows the character cards
     */
    public void showCharacterCards() {
        Platform.runLater(() -> {
            if (characterCardsWidget == null) {
                characterCardsWidget = new CharacterCardsWidget();
            }
            scene.setRoot(characterCardsWidget);
        });
    }

    /**
     * Shows the colour decision page
     */
    public void showColourDecision() {
        Platform.runLater(() -> {
            NoColourThreeStudentWidget noColourThreeStudentWidget = new NoColourThreeStudentWidget();
            scene.setRoot(noColourThreeStudentWidget);
        });
    }

    /**
     * Shows the group island decision page
     */
    public void showGroupIslandDecision() {
        Platform.runLater(() -> {
            SelectGroupIslandWidget selectGroupIslandWidget = new SelectGroupIslandWidget();
            scene.setRoot(selectGroupIslandWidget);
        });
    }

    /**
     * Handles the disconnection of a player
     *
     * @param playerName the name of the player that disconnected
     */
    @Override
    public void handlePlayerDisconnect(String playerName) {

    }

    /**
     * Handles the moment in which a player crashes
     *
     * @param playerName the name of the player that crashed
     */
    @Override
    public void handlePlayerCrash(String playerName) {
        getRenderer().showErrorMessage("Player " + playerName + " crashed! The game is over!");
    }

    /**
     * Handles the end of the game
     */
    @Override
    public void handleEndGame() {

    }

    /**
     * Gets the scene
     *
     * @return the scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * States if it is the turn of the local player
     *
     * @return a boolean that is true if it is the turn of the local player, false otherwise
     */
    public boolean isOwnTurn() {
        return getModel().getCurrentPlayer().getNickname().equals(getModel().getLocalPlayer().getNickname());
    }

    /**
     * States if the mode is guided
     *
     * @return a boolean that is true if the mode is guided, false otherwise
     */
    public boolean isGuidedMode() {
        return guidedMode;
    }

    /**
     * Sets the guided mode
     *
     * @param guidedMode a boolean that is true if the mode is guided, false otherwise
     */
    public void setGuidedMode(boolean guidedMode) {
        this.guidedMode = guidedMode;
    }
}
