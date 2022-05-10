package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.HashMap;

public class AllPlayersConnectedMessage extends ServerMessage{
    @Serial
    private static final long serialVersionUID = 4167105894763539403L;
    private final HashMap<String, Wizard> players;
    private final boolean gameMode;
    private final int numPlayers;

    public AllPlayersConnectedMessage(HashMap<String, Wizard> players, boolean gameMode, int numPlayers){
        this.players = players;
        this.gameMode = gameMode;
        this.numPlayers = numPlayers;
    }

    public void process(View view){
        view.handleAllPlayersConnected(players,gameMode,numPlayers);
    }
}
