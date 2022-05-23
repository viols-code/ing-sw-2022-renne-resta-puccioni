package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.HashMap;

/**
 * ServerMessage notifying all the clients that all players connected to the game
 */
public class AllPlayersConnectedMessage extends ServerMessage {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 4167105894763539403L;
    /**
     * HashMap with the nickname and wizard of the players
     */
    private final HashMap<String, Wizard> players;
    /**
     * The game mode
     */
    private final boolean gameMode;
    /**
     * The number of players in the game
     */
    private final int numPlayers;

    /**
     * Constructor
     *
     * @param players    the HashMap with the nickname and wizard of the players
     * @param gameMode   the game mode
     * @param numPlayers the number of players in the game
     */
    public AllPlayersConnectedMessage(HashMap<String, Wizard> players, boolean gameMode, int numPlayers) {
        this.players = players;
        this.gameMode = gameMode;
        this.numPlayers = numPlayers;
    }

    /**
     * Notifying all the clients that all players connected to the game
     *
     * @param view the view
     */
    public void process(View view) {
        view.handleAllPlayersConnected(players, gameMode, numPlayers);
    }
}
