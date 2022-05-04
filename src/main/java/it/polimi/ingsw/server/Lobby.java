package it.polimi.ingsw.server;

import it.polimi.ingsw.IProcessablePacket;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.messages.*;

import java.util.*;

/**
 * Models a lobby, holding the list of connected clients, the first connected client and the number of players needed to
 * start the game in the current lobby.
 */
public class Lobby extends Observable<IProcessablePacket> {
    private final UUID uuid;

    private SocketClientConnection firstConnection;
    private final List<SocketClientConnection> connections;
    private int playersToStart;
    private Boolean gameMode;
    private boolean isGameModeSet;

    /**
     * Constructs a new Lobby with a random UUID, an empty connection list and the players needed to start set to -1.
     */
    public Lobby() {
        this.uuid = UUID.randomUUID();
        this.firstConnection = null;
        this.connections = new ArrayList<>();
        this.playersToStart = -1;
        this.gameMode = null;
        this.isGameModeSet = false;
    }

    /**
     * Gets the UUID of this Lobby.
     *
     * @return the uuid of the lobby
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Gets the list of clients connected to this lobby
     *
     * @return the list of client connections
     */
    List<SocketClientConnection> getConnections() {
        return connections;
    }


    /**
     * Adds the given connection to this lobby.
     *
     * @param connection the connection to be added
     * @throws IllegalStateException if 4 clients are already connected to this lobby
     */
    public void addConnection(SocketClientConnection connection) throws IllegalStateException {
        if (connections.size() > 3)
            throw new IllegalStateException();

        connections.add(connection);

        if (firstConnection == null)
            firstConnection = connection;

        notify(new AddToLobbyMessage(connection, firstConnection == connection));
    }

    /**
     * Sets the player name for the given connection. If there is another player with this name already connected sends
     * an error message to the client.
     *
     * @param connection the connection that will have its player name set
     * @param playerName the player name to be set, if it's null or empty sends an error message to the client
     */
    public void setPlayerNameAndWizard(SocketClientConnection connection, String playerName, Wizard wizard) {
        if (playerName == null || playerName.trim().equals("")) {
            notify(new ErrorMessage(connection, "Your username can't be empty"));
            return;
        }
        if (wizard == null) {
            notify(new ErrorMessage(connection, "Your wizard can't be empty"));
            return;
        }

        for (SocketClientConnection clientConnection : connections) {
            if (playerName.equalsIgnoreCase(clientConnection.getPlayerName())) {
                notify(new ErrorMessage(connection, "This username is already taken"));
                return;
            }
        }

        for (SocketClientConnection clientConnection : connections) {
            if (wizard.equals(clientConnection.getWizard())) {
                notify(new ErrorMessage(connection, "This wizard is already taken"));
                return;
            }
        }

        connection.setPlayerName(playerName);
        List<String> otherNames = new ArrayList<>();
        connections.forEach(con -> {
            if (con.getPlayerName() != null)
                otherNames.add(con.getPlayerName());
        });

        connection.setWizard(wizard);
        List<Wizard> otherWizard = new ArrayList<>();
        connections.forEach(con -> {
            if (con.getWizard() != null)
                otherWizard.add(con.getWizard());
        });

        notify(new PlayerConnectMessage(playerName, wizard, connections.size(), playersToStart, otherNames, otherWizard));
    }

    /**
     * Sets the players needed to start the game, if the given connection is not the first connection to the lobby
     * sends an error message to the client. If the number given is less than the number of clients already connected
     * sends an error message to the client.
     *
     * @param connection     the connection that wants to set the number of players needed to start
     * @param playersToStart the number of players needed to start the game, should be between 1 and 4, otherwise an
     *                       error message will be sent to the client
     */
    public void setPlayersToStart(SocketClientConnection connection, int playersToStart) {
        if (connections.indexOf(connection) != 0) {
            notify(new ErrorMessage(connection, "Only the first player that connected to the lobby can set the number of players needed to start the game"));
            return;
        }
        if (playersToStart < 2 || playersToStart > 3) {
            notify(new ErrorMessage(connection, "This is not a number between 2 and 3"));
            return;
        }
        if (playersToStart < connections.size()) {
            notify(new ErrorMessage(connection, connections.size() + " players are already connected"));
            return;
        }

        this.playersToStart = playersToStart;
        notify(new PlayersToStartSetMessage(connection, playersToStart));
    }

    public void setGameMode(SocketClientConnection connection, Boolean gameMode) {
        if (connections.indexOf(connection) != 0) {
            notify(new ErrorMessage(connection, "Only the first player that connected to the lobby can set game mode"));
            return;
        }
        if (gameMode == null) {
            notify(new ErrorMessage(connection, "The game mode that you have chosen is invalid!"));
            return;
        }

        this.gameMode = gameMode;
        setGameMode(connection);
    }

    /**
     * Sets to true if the master player has already chosen the desired gameMode
     */
    public void setGameMode(SocketClientConnection connection) {
        isGameModeSet = true;
        notify(new GameModeSetMessage(connection));
    }

    /**
     * Notifies all connected clients that the game is starting.
     */
    public synchronized void startGame() {
        notify(new GameStartMessage(gameMode));
    }

    /**
     * Remove the given connection from the Lobby, notifying all other clients.
     *
     * @param connection the connection to be removed from the lobby
     */
    public void disconnect(SocketClientConnection connection) {
        if (connection == firstConnection) {
            firstConnection = null;
            playersToStart = -1;
        }

        notify(new PlayerLeaveMessage(connection.getPlayerName()));
        connections.remove(connection);
    }

    /**
     * Remove the given crashed connection from the Lobby, disconnecting and notifying all other clients.
     *
     * @param crashedConnection the connection that crashed
     */
    public void disconnectAll(SocketClientConnection crashedConnection) {
        connections.remove(crashedConnection);

        notify(new PlayerCrashMessage(crashedConnection.getPlayerName()));

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (SocketClientConnection conn : connections) {
                    if (conn != null) {
                        conn.closeConnection();
                    }
                    connections.remove(conn);
                }
            }
        }, 5000);
    }

    /**
     * Checks if the number of players needed to start the game for this Lobby is set.
     *
     * @return true if the playersToStart set are 2 or 3, false otherwise
     */
    public boolean isPlayersToStartSet() {
        return playersToStart > 1 && playersToStart < 4;
    }

    /**
     * Checks if the Lobby can start a game.
     *
     * @return true if all connected clients have their name set and the number of connected clients is equal to
     * playersToStart, false otherwise
     */
    public synchronized boolean canStart() {
        for (SocketClientConnection conn : connections)
            if (conn.getPlayerName() == null || conn.getWizard() == null)
                return false;
        if (!isGameModeSet) return false;
        return playersToStart == connections.size();
    }

    /**
     * Terminate the Lobby, disconnecting all clients.
     */
    public synchronized void terminate() {
        for (SocketClientConnection conn : connections) {
            if (conn != null) {
                conn.closeConnection();
            }
            connections.remove(conn);
        }
    }
}