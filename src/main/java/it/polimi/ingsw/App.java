package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    private static boolean startCli = false;

    public static void main(String[] args) {
        if(1 <= args.length){
            handleCommand(args[0]);
        } else{
            handleCommand("");
        }
        launch();
    }

    private static void handleCommand(String command) {
        if (command.equalsIgnoreCase("cli")) {
            startCli = true;
        }
    }

    @Override
    public void start(Stage stage) {
        Client client;
        client = new Client(stage, startCli);
        client.run();
    }
}
