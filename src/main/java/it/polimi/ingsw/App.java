package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Class used to launch the game client.
 */
public class App extends Application {
    private static boolean startCli = false;

    /**
     * Launches the game client.
     *
     * @param args the argument: "cli" launches the game in command line mode
     */
    public static void main(String[] args) {
        if (1 <= args.length) {
            handleCommand(args[0]);
        } else {
            handleCommand("");
        }
        launch();
    }

    /**
     * Parses and recognizes the given command line argument.
     *
     * @param command the command given
     */
    private static void handleCommand(String command) {
        if (command.equalsIgnoreCase("cli")) {
            startCli = true;
        }
    }

    /**
     * Entry method for the JavaFX application.
     *
     * @param stage the main stage of the application
     */
    @Override
    public void start(Stage stage) {
        Client client;
        client = new Client(stage, startCli);
        client.run();
    }
}
