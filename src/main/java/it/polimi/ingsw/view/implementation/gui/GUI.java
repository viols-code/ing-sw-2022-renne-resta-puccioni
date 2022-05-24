package it.polimi.ingsw.view.implementation.gui;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.View;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

public class GUI extends View {
    private final Stage stage;
    private Scene scene;
    private static GUI instance;
    //private MediaPlayer mediaPlayer;
    private boolean wasPlayingMusicBefore;

    public GUI(Client client, Stage stage) {
        super(client);
        this.stage = stage;

        this.setModelUpdateHandler(new GUIModelUpdateHandler(this));
        this.setRenderer(new GUIRenderer(this));
        this.setActionSender(new GUIActionSender(this));
        instance = this;

    }

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
        stage.setMaximized(true);
        stage.show();
    }

    @Override
    public void addToLobby(boolean isFirstConnection){
        super.addToLobby(isFirstConnection);

        Platform.runLater(() -> {
            Parent nameSelectionPage = FXMLUtils.loadFXML("/gui/NicknameSelection");
            scene.setRoot(nameSelectionPage);
        });
    }

    @Override
    public void handleCorrectNickname(String nickname, List<String> takenNicknames){
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
    public void handleCorrectWizard(Wizard wizard, List<Wizard> takenWizard){
        super.handleCorrectWizard(wizard, takenWizard);

        if(wizard.equals(getPlayerWizard())){
            if(isLobbyMaster()) {
                Platform.runLater(() -> {
                    Parent gameModeSelection = FXMLUtils.loadFXML("/gui/GameModeSelection");
                    scene.setRoot(gameModeSelection);
                });
            }
            else{
                Platform.runLater(() -> {
                    Parent waitingPlayers = FXMLUtils.loadFXML("/gui/WaitingPlayers");
                    scene.setRoot(waitingPlayers);
                });
            }
        }
    }

    @Override
    public void handleGameMode(boolean gameMode){
        super.handleGameMode(gameMode);

        Platform.runLater(() -> {
            Parent playersToStartSelection = FXMLUtils.loadFXML("/gui/PlayersToStartSelection");
            scene.setRoot(playersToStartSelection);
        });

    }

    @Override
    public void handleSetPlayersToStart(int playersToStart) {
        super.handleSetPlayersToStart(playersToStart);
        Platform.runLater(() -> {
            Parent waitingPlayers = FXMLUtils.loadFXML("/gui/WaitingPlayers");
            scene.setRoot(waitingPlayers);
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
}
