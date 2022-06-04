package it.polimi.ingsw;

import java.io.IOException;


public class AppLauncher {
    private final static String operatingSystem = System.getProperty("os.name");

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
