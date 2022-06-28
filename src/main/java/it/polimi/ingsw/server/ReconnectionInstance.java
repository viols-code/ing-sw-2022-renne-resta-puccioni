package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.GameController;

import java.util.ArrayList;
import java.util.List;

public class ReconnectionInstance implements Runnable{

    private final GameController controller;
    private final Lobby lobby;
    private final SocketClientConnection conn;

    /**
     * Creates an instance of the game with the updated information when a player reconnects
     *
     * @param lobby           the lobby where the reconnected player belongs
     * @param controller the gameController
     */
    ReconnectionInstance(Lobby lobby, GameController controller, SocketClientConnection connection) {
        this.lobby = lobby;
        this.controller = controller;
        this.conn = connection;
    }

    /**
     * Re-initializes the game for the reconnected player, instantiating and registering all necessary controller and model objects
     */
    @Override
    public void run() {

        conn.setLobbyUUID(lobby.getUuid());

        RemoteView remoteView = conn.getRemoteView();
        remoteView.setPlayer(conn.getPlayerName());
        remoteView.setGameController(controller);
        controller.getGame().addObserver(remoteView);
        controller.getGame().getTable().addObserver(remoteView);
        controller.getGame().getTable().getBag().addObserver(remoteView);

        for (int i = 0; i < controller.getGame().getTable().getNumberOfGroupIsland(); i++) {
            controller.getGame().getTable().getGroupIslandByIndex(i).addObserver(remoteView);
            for (int j = 0; j < controller.getGame().getTable().getGroupIslandByIndex(i).getNumberOfSingleIsland(); j++) {
                controller.getGame().getTable().getGroupIslandByIndex(i).getIslandByIndex(j).addObserver(remoteView);
            }
        }

        controller.getGame().getBasicState().addObserver(remoteView);

        if (controller.isGameExpert()) {
            for (int i = 0; i < 3; i++) {
                controller.getGame().getCharacterCardByIndex(i).addObserver(remoteView);
            }
        }

        for (int i = 0; i < controller.getGame().getNumberOfPlayer(); i++) {
            controller.getGame().getPlayerByIndex(i).addObserver(remoteView);
            controller.getGame().getPlayerByIndex(i).getSchoolBoard().addObserver(remoteView);
        }
    }
}
