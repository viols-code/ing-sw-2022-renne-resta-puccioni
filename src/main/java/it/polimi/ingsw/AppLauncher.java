package it.polimi.ingsw;

import java.io.IOException;

public class AppLauncher {
    public static void main(String[] args) throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "chcp 65001").inheritIO().start().waitFor();
        App.main(args);
        System.out.println("ciao");
    }
}
