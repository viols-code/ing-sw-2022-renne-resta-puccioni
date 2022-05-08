package it.polimi.ingsw;

import it.polimi.ingsw.server.Server;

import java.io.IOException;
import java.util.Locale;

/**
 * Launcher class for the game server.
 */
public class ServerApp {
    /**
     * Launches the server.
     *
     * @param args an array of arguments, currently ignored
     */
    public static void main(String[] args) {
        Server server;
        String command = "view ciao bella";

        String[] split = command.split(" ");
        String cmd = split[0];
        String[] ciao = new String[0];
        if (cmd.equals("view")) {
            split[1] = split[1].substring(0, 1).toUpperCase(Locale.ROOT) + split[1].substring(1);
            for (int i = 2; i < split.length; i++) {
                split[1] += split[i].substring(0, 1).toUpperCase(Locale.ROOT) + split[i].substring(1);
            }
        }

        System.out.println(split[1]);
        try {
            server = new Server();
            server.run();
        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }
}