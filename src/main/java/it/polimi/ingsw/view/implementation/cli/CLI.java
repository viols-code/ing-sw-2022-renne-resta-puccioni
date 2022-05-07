package it.polimi.ingsw.view.implementation.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.view.View;

import java.util.Map;

public class CLI extends View{
    /**
     * Constructs a new View.
     *
     * @param client the client responsible for this view
     */
    public CLI(Client client) {
        super(client);
    }

    @Override
    public void run() {

    }

    @Override
    public void handlePlayerDisconnect(String playerName) {

    }

    @Override
    public void handlePlayerCrash(String playerName) {

    }

    @Override
    public void handleEndGame(Map<String, Integer> scores, String winnerName) {

    }
}
