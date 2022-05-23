package it.polimi.ingsw.view.implementation.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.view.View;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GUI extends View {
    private final Stage stage;
    private Scene scene;
    //private MediaPlayer mediaPlayer;
    private boolean wasPlayingMusicBefore;

    public GUI(Client client, Stage stage) {
        super(client);
        this.stage = stage;

        this.setModelUpdateHandler(new GUIModelUpdateHandler(this));
        this.setRenderer(new GUIRenderer(this));
        this.setActionSender(new GUIActionSender(this));

    }

    @Override
    public void run() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        scene = new Scene(new StackPane(l), 640, 480);
        stage.setScene(scene);
        stage.setTitle("Eryantis");
        stage.setMaximized(true);
        stage.show();
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
