package it.polimi.ingsw.server;

import it.polimi.ingsw.client.messages.ClientMessage;
import it.polimi.ingsw.model.player.Wizard;

import java.util.*;

/**
 * Controller for the lobbies waiting to start a game
 */
public class LobbyController {
    private final List<Lobby> total;
    private final Map<UUID, Lobby> disconnectedLobbies;
    private final Map<UUID, Lobby> playingLobbies;
    private final List<Lobby> waitingLobbies;
    private final WaitingRoom waitingRoom;
    private Lobby currentLobby;

    public LobbyController() {
        disconnectedLobbies = new HashMap<>();
        playingLobbies = new HashMap<>();
        waitingLobbies = new ArrayList<>();
        currentLobby = new Lobby();
        waitingRoom = new WaitingRoom();
        total = new ArrayList<>();
        total.add(currentLobby);
    }

    /**
     * Processes the given ClientMessage
     *
     * @param message the client message to process
     */
    public synchronized void update(ClientMessage message) {
        message.process(this);
    }

    /**
     * Adds the given ClientConnection to the current lobby
     *
     * @param connection the connection that will be added to the lobby
     */
    public synchronized void correctConnection(SocketClientConnection connection) {
        waitingRoom.addObserver(connection.getRemoteView());
        waitingRoom.addConnection(connection);
    }


    /**
     * Adds the given ClientConnection to the current lobby
     *
     * @param connection the connection that will be added to the lobby
     */
    private void addToLobby(SocketClientConnection connection) {
        if ((!currentLobby.isPlayersToStartSet() && currentLobby.getConnections().size() < 3) || (currentLobby.isPlayersToStartSet() && currentLobby.getConnections().size() < currentLobby.getPlayersToStart())) {
            currentLobby.addObserver(connection.getRemoteView());
            try {
                currentLobby.addConnection(connection);
            } catch (IllegalStateException e) {
                System.err.println("More than 3 connections, closing this one");
                connection.closeConnection();
            }
            return;
        }

        waitingLobbies.add(currentLobby);
        currentLobby = new Lobby();
        total.add(currentLobby);
        currentLobby.addObserver(connection.getRemoteView());
        try {
            currentLobby.addConnection(connection);
        } catch (IllegalStateException e) {
            System.err.println("More than 3 connections, closing this one");
            connection.closeConnection();
        }
    }

    /**
     * Sets the name of the Player that is associated with the given ClientConnection
     *
     * @param connection the connection that will have its player name set
     * @param playerName the name to be set
     */
    public synchronized void setPlayerName(SocketClientConnection connection, String playerName) {
        if (connection.getPlayerName() != null) {
            return;
        }

        // Check if the nickname is unique in the all LobbyController
        try {
            for (Lobby lobby : total) {
                lobby.checkPlayerName(playerName);
            }
        } catch (IllegalArgumentException e) {
            waitingRoom.notifyError(connection, e.getMessage());
            return;
        }

        // Check if the nickname corresponds to a disconnected player, if so connect the player to the correct lobby
        for (Lobby lobby : disconnectedLobbies.values()) {
            if (lobby.getNicknames().contains(playerName)) {
                lobby.addConnection(connection);
                waitingRoom.removeConnection(connection);
                lobby.addObserver(connection.getRemoteView());
                lobby.setPlayerName(connection, playerName);
                if (connection.getPlayerName() != null) {
                    System.out.println("Player " + connection.getPlayerName() + " connected in Lobby " + lobby.getUuid());
                    connection.setReconnected();
                } else {
                    System.out.println("Duplicated username, waiting for a new one");
                }
                return;
            }
        }

        // Connect the player to a new lobby
        addToLobby(connection);
        waitingRoom.removeConnection(connection);

        if (currentLobby.getConnections().contains(connection)) {
            currentLobby.setPlayerName(connection, playerName);
            if (connection.getPlayerName() != null) {
                System.out.println("Player " + connection.getPlayerName() + " connected in Lobby " + currentLobby.getUuid());
            } else {
                System.out.println("Duplicated username, waiting for a new one");
            }

        } else {
            for (Lobby lobby : waitingLobbies) {
                if (lobby.getConnections().contains(connection)) {
                    lobby.setPlayerName(connection, playerName);
                    if (connection.getPlayerName() != null) {
                        System.out.println("Player " + connection.getPlayerName() + " connected in Lobby " + lobby.getUuid());
                    } else {
                        System.out.println("Duplicated username, waiting for a new one");
                    }

                }
            }
        }


    }

    /**
     * Sets the wizard of the Player that is associated with the given ClientConnection
     *
     * @param connection the connection that will have its player name set
     * @param wizard     the wizard to be set
     */
    public synchronized void setPlayerWizard(SocketClientConnection connection, Wizard wizard) {
        if (connection.getWizard() != null) {
            return;
        }

        if (currentLobby.getConnections().contains(connection)) {
            currentLobby.setPlayerWizard(connection, wizard);

            if (connection.getWizard() != null) {
                System.out.println("Player connected: " + connection.getPlayerName() + ", with wizard: " + connection.getWizard());
            }

            if (currentLobby.canStart())
                startGame(currentLobby);
        } else {
            for (Lobby lobby : waitingLobbies) {
                if (lobby.getConnections().contains(connection)) {
                    lobby.setPlayerWizard(connection, wizard);

                    System.out.println("Player connected: " + connection.getPlayerName() + ", with wizard: " + connection.getWizard());

                    if (lobby.canStart())
                        startGame(lobby);
                    return;
                }
            }
        }
    }

    /**
     * Sets the number of players needed to start the game of the connection
     *
     * @param connection     the connection that wants to set the players needed to start
     * @param playersToStart the number of players needed to start the game in the current lobby
     */
    public synchronized void setPlayersToStart(SocketClientConnection connection, int playersToStart) {
        if (currentLobby.getConnections().contains(connection)) {
            if (currentLobby.isPlayersToStartSet()) {
                return;
            }
            currentLobby.setPlayersToStart(connection, playersToStart);
            if (currentLobby.canStart())
                startGame(currentLobby);
        } else {
            for (Lobby lobby : waitingLobbies) {
                if (lobby.getConnections().contains(connection)) {
                    if (lobby.isPlayersToStartSet()) {
                        return;
                    }
                    lobby.setPlayersToStart(connection, playersToStart);
                    if (lobby.canStart())
                        startGame(lobby);
                }
            }
        }

    }

    /**
     * Sets the gameMode that is associated with the given ClientConnection
     *
     * @param connection the connection that will have its player name set
     * @param gameMode   the gameMode to be set
     */
    public synchronized void setGameMode(SocketClientConnection connection, boolean gameMode) {
        if (currentLobby.getConnections().contains(connection)) {
            currentLobby.setGameMode(connection, gameMode);
        } else {
            for (Lobby lobby : waitingLobbies) {
                if (lobby.getConnections().contains(connection)) {
                    lobby.setGameMode(connection, gameMode);
                }
            }
        }
    }

    /**
     * Starts the game in the given Lobby. The game is initialized in a separate Thread to allow new players to connect
     * to the next lobby without waiting
     *
     * @param lobby the Lobby to be started
     */
    private synchronized void startGame(Lobby lobby) {
        playingLobbies.put(lobby.getUuid(), lobby);
        waitingLobbies.remove(lobby);

        lobby.startGame();
        if (lobby.equals(currentLobby)) {
            currentLobby = new Lobby();
            total.add(currentLobby);
        }

        Thread t = new Thread(new GameInstance(lobby, lobby.getGameMode(), lobby.getPlayersToStart()));
        t.start();
    }

    /**
     * Deregister a connection from the lobby and terminate the game and disconnects all other
     * players in that lobby.
     *
     * @param connection the connection to deregister
     */
    public synchronized void deregisterConnection(SocketClientConnection connection) {
        if (connection.getLobbyUUID() == null) {
            if (currentLobby.getConnections().contains(connection)) {
                currentLobby.disconnectAll(connection);
                currentLobby = new Lobby();
                total.add(currentLobby);
                return;
            }

            for (Lobby waitingLobby : waitingLobbies) {
                if (waitingLobby.getConnections().contains(connection)) {
                    waitingLobby.disconnectAll(connection);
                    waitingLobbies.remove(waitingLobby);
                    total.remove(waitingLobby);
                    deleteLobby(waitingLobby);
                    return;
                }
            }
        } else {
            Lobby lobby = playingLobbies.get(connection.getLobbyUUID());
            if (lobby != null) {
                lobby.disconnect(connection);
                playingLobbies.remove(connection.getLobbyUUID());
                disconnectedLobbies.put(connection.getLobbyUUID(), lobby);
                return;
            }

            lobby = disconnectedLobbies.get(connection.getLobbyUUID());
            if (lobby != null) {
                if (lobby.getConnections().size() == 1) {
                    disconnectedLobbies.remove(lobby.getUuid());
                    total.remove(lobby);
                    deleteLobby(lobby);
                }
                lobby.disconnect(connection);
            }
        }
    }

    /**
     * Delete the given lobby from all the possible list in the LobbyController
     *
     * @param lobby the given lobby to be deleted
     */
    public void deleteLobby(Lobby lobby) {
        if (lobby.equals(currentLobby)) {
            currentLobby = new Lobby();
        }

        for (SocketClientConnection connection : lobby.getConnections()) {
            waitingRoom.removeConnection(connection);
        }

        waitingLobbies.remove(lobby);
        playingLobbies.remove(lobby.getUuid());
        disconnectedLobbies.remove(lobby.getUuid());
        total.remove(lobby);
    }

}