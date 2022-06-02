package it.polimi.ingsw.view.implementation.gui;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.beans.MockPlayer;
import it.polimi.ingsw.view.implementation.cli.utils.ViewString;
import it.polimi.ingsw.view.implementation.gui.widgets.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

    }

    /**
     * Gets the singleton instance.
     *
     * @return the singleton instance
     */
    public static GUI instance() {
        return instance;
    }

    @Override
    public void run() {
        Parent homePage = FXMLUtils.loadFXML("/gui/Home");
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        scene = new Scene(homePage);
        stage.setScene(scene);
        stage.setTitle("Eriantys");
        stage.show();

        stage.setOnCloseRequest(windowEvent -> getClient().terminate());
    }

    @Override
    public void addToLobby(boolean isFirstConnection, List<Wizard> takenWizard) {
        super.addToLobby(isFirstConnection, takenWizard);

        Platform.runLater(() -> {
            Parent nameSelectionPage = FXMLUtils.loadFXML("/gui/NicknameSelection");
            scene.setRoot(nameSelectionPage);
        });

    }

    @Override
    public void handleCorrectNickname(String nickname, List<String> takenNicknames) {
        super.handleCorrectNickname(nickname, takenNicknames);

        //Devono essere definiti alcuni metodi nel mockModel e degli attributi di tipo IntegerProperty
        //getModel().updatePlayerCount(currentPlayers, playersToStart);

        if (nickname.equals(getPlayerName())) {
            Platform.runLater(() -> {
                Parent wizardSelection = FXMLUtils.loadFXML("/gui/WizardSelection");
                scene.setRoot(wizardSelection);
            });
        }
    }

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

    @Override
    public void handleSetPlayersToStart(int playersToStart) {
        super.handleSetPlayersToStart(playersToStart);
        getModel().updatePlayerCount(getModel().getNicknames().size(), playersToStart);
        Platform.runLater(() -> {
            Parent waitingPlayers = FXMLUtils.loadFXML("/gui/WaitingPlayers");
            scene.setRoot(waitingPlayers);
        });
    }

    @Override
    public void handlePlayerConnect(String playerName, Wizard wizard, int currentPlayers, Integer playersToStart, List<Wizard> takenWizard) {
        super.handlePlayerConnect(playerName, wizard, currentPlayers, playersToStart, takenWizard);
        if (playersToStart != null) {
            getModel().updatePlayerCount(currentPlayers, playersToStart);
        }else{
            getModel().updatePlayerCount(currentPlayers, -1);
        }

        getModel().addPlayerNickname(playerName);
    }

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

    public void showOtherPlayerBoard(MockPlayer player) {
        Platform.runLater(() -> {
            OtherSchoolBoardWidget otherSchoolBoard = new OtherSchoolBoardWidget(player);
            scene.setRoot(otherSchoolBoard);
        });
    }

    public void showCloudTile() {
        Platform.runLater(() -> {
            CloudTileWidget cloudTileWidget = new CloudTileWidget();
            scene.setRoot(cloudTileWidget);
        });
    }

    public void showIslands() {
        Platform.runLater(() -> {
            if (groupIslandsWidget == null) {
                groupIslandsWidget = new GroupIslandsWidget();
            }
            scene.setRoot(groupIslandsWidget);
        });
    }

    public void showAssistantCards() {
        Platform.runLater(() -> {
            if (assistantCardsWidget == null) {
                assistantCardsWidget = new AssistantCardsWidget();
            }
            scene.setRoot(assistantCardsWidget);
        });
    }

    public void showCharacterCards() {
        Platform.runLater(() -> {
            if (characterCardsWidget == null) {
                characterCardsWidget = new CharacterCardsWidget();
            }
            scene.setRoot(characterCardsWidget);
        });
    }


    @Override
    public void handlePlayerDisconnect(String playerName) {

    }

    @Override
    public void handlePlayerCrash(String playerName) {

    }

    @Override
    public void handleEndGame() {

    }

    public Scene getScene() {
        return scene;
    }

    public boolean isOwnTurn() {
        return getModel().getCurrentPlayer().getNickname().equals(getModel().getLocalPlayer().getNickname());
    }

}
