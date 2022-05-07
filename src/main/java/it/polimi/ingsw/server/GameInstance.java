package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.GameController;

import java.util.ArrayList;
import java.util.List;

/**
 * Thread to start a new Game instance.
 */
public class GameInstance implements Runnable {
    private final boolean isExpertGame;
    private final int numberOfPlayers;
    private final Lobby lobby;

    /**
     * Constructs a new GameInstance for the given Lobby.
     *
     * @param lobby the lobby that will have its game started
     */
    GameInstance(Lobby lobby, boolean isExpertGame, int numberOfPlayers) {
        this.isExpertGame = isExpertGame;
        this.lobby = lobby;
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Initializes a new Game, instantiating and registering all necessary controller and model objects.
     */
    @Override
    public void run() {
        System.out.println("Starting game!");

        GameController controller = new GameController(isExpertGame, numberOfPlayers);
        List<RemoteView> registeredViews = new ArrayList<>();
        for (SocketClientConnection conn : lobby.getConnections()) {
            conn.setLobbyUUID(lobby.getUuid());

            controller.addPlayer(conn.getPlayerName(), conn.getWizard());
            RemoteView remoteView = conn.getRemoteView();
            remoteView.setPlayer(conn.getPlayerName());
            remoteView.setGameController(controller);

            controller.getGame().addObserver(remoteView);
            for (int i = 0; i < controller.getGame().getTable().getNumberOfGroupIsland(); i++) {
                controller.getGame().getTable().getGroupIslandByIndex(i).addObserver(remoteView);
                for (int j = 0; j < controller.getGame().getTable().getGroupIslandByIndex(i).getNumberOfSingleIsland(); j++) {
                    controller.getGame().getTable().getGroupIslandByIndex(i).getIslandByIndex(j).addObserver(remoteView);
                }
            }

            controller.getGame().getTable().addObserver(remoteView);
            controller.getGame().getTable().getBag().addObserver(remoteView);

            if (isExpertGame) {
                for (int i = 0; i < 3; i++) {
                    controller.getGame().getCharacterCardByIndex(i).addObserver(remoteView);
                }
            }

            registeredViews.add(remoteView);

        }

        for (int i = 0; i < controller.getGame().getNumberOfPlayer(); i++) {
            for (RemoteView remoteView : registeredViews) {
                controller.getGame().getPlayerByIndex(i).addObserver(remoteView);
                controller.getGame().getPlayerByIndex(i).getSchoolBoard().addObserver(remoteView);
            }
        }

        lobby.startGame();

    }
}
