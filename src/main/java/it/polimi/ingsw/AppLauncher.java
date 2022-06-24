package it.polimi.ingsw;

import java.io.IOException;

/**
 * This class is needed for jar packaging with maven shade because the launcher class for a JavaFX project should not extend Application
 */
public class AppLauncher {
    private final static String operatingSystem = System.getProperty("os.name");

    /**
     * Launches the game client.
     *
     * @param args the argument: "cli" launches the game in command line mode
     */
    public static void main(String[] args) {
        if (operatingSystem.contains("Windows")) {
            try {
                new ProcessBuilder("cmd", "/c", "chcp 65001").inheritIO().start().waitFor();
            } catch (InterruptedException | IOException e) {
                System.err.println(e.getMessage());
            }
        }
        App.main(args);
    }
}
