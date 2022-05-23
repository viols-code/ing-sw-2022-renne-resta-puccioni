package it.polimi.ingsw.view.implementation.gui;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.view.View;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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
        stage.setTitle("Eryantis");
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
    public void handlePlayerDisconnect(String playerName) {

    }

    @Override
    public void handlePlayerCrash(String playerName) {

    }

    @Override
    public void handleEndGame() {

    }
}
