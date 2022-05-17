package it.polimi.ingsw.server;

import it.polimi.ingsw.client.messages.ClientMessage;
import it.polimi.ingsw.model.player.Wizard;

import java.util.*;

/**
 * Controller for the lobbies waiting to start a game.
 */
public class LobbyController {
    private final Map<UUID, Lobby> playingLobbies;
    private final List<Lobby> waitingLobbies;
    private Lobby currentLobby;

    public LobbyController(){
        playingLobbies = new HashMap<>();
        waitingLobbies = new ArrayList<>();
        currentLobby = new Lobby();
        waitingLobbies.add(currentLobby);
    }

    /**
     * Processes the given ClientMessage.
     *
     * @param message the client message to process
     */
    public synchronized void update(ClientMessage message) {
        message.process(this);
    }

    /**
     * Adds the given ClientConnection to the current lobby.
     *
     * @param connection the connection that will be added to the lobby
     */
    public synchronized void addToLobby(SocketClientConnection connection) {
        for (Lobby waitingLobby : waitingLobbies) {
            if ((!waitingLobby.isPlayersToStartSet() && waitingLobby.getConnections().size() < 3) || (waitingLobby.isPlayersToStartSet() && waitingLobby.getConnections().size() < waitingLobby.getPlayersToStart())) {
                waitingLobby.addObserver(connection.getRemoteView());
                try {
                    waitingLobby.addConnection(connection);
                } catch (IllegalStateException e) {
                    System.err.println("More than 3 connections, closing this one");
                    connection.closeConnection();
                }
                return;
            }
        }


        currentLobby = new Lobby();
        waitingLobbies.add(currentLobby);
        currentLobby.addObserver(connection.getRemoteView());
        try {
            currentLobby.addConnection(connection);
        } catch (IllegalStateException e) {
            System.err.println("More than 3 connections, closing this one");
            connection.closeConnection();
        }
    }

    /**
     * Sets the name of the Player that is associated with the given ClientConnection.
     *
     * @param connection the connection that will have its player name set
     * @param playerName the name to be set
     */
    public synchronized void setPlayerName(SocketClientConnection connection, String playerName) {
        if (connection.getPlayerName() != null) {
            return;
        }

        if(waitingLobbies.size() > 1){
            for (Lobby waitingLobby : waitingLobbies) {
                if (waitingLobby.getConnections().contains(connection)) {
                    waitingLobby.setPlayerName(connection, playerName);
                    break;
                }
            }
        } else{
            currentLobby.setPlayerName(connection, playerName);
            System.out.println("Player " + connection.getPlayerName() + " connected in Lobby " + currentLobby.getUuid());
        }
    }

    /**
     * Sets the wizard of the Player that is associated with the given ClientConnection.
     *
     * @param connection the connection that will have its player name set
     * @param wizard     the wizard to be set
     */
    public synchronized void setPlayerWizard(SocketClientConnection connection, Wizard wizard) {
        if (connection.getWizard() != null) {
            return;
        }

        if(waitingLobbies.size() > 1){
            for (Lobby waitingLobby : waitingLobbies) {
                if (waitingLobby.getConnections().contains(connection)) {
                    waitingLobby.setPlayerWizard(connection, wizard);
                    if (waitingLobby.canStart()){
                        startGame(waitingLobby);
                    }
                    break;
                }
            }
        } else{
            currentLobby.setPlayerWizard(connection, wizard);

            System.out.println("Player connected: " + connection.getPlayerName() + ", with wizard: " + connection.getWizard());

            if (currentLobby.canStart())
                startGame(currentLobby);
        }
    }

    /**
     * Sets the number of players needed to start the game in the current lobby.
     *
     * @param connection     the connection that wants to set the players needed to start
     * @param playersToStart the number of players needed to start the game in the current lobby
     */
    public synchronized void setPlayersToStart(SocketClientConnection connection, int playersToStart) {
        if(waitingLobbies.size() > 1){
            for (Lobby waitingLobby : waitingLobbies) {
                if (waitingLobby.getConnections().contains(connection)) {
                    if (waitingLobby.isPlayersToStartSet()) {
                        return;
                    }
                    waitingLobby.setPlayersToStart(connection, playersToStart);
                    if (waitingLobby.canStart()) {
                        startGame(waitingLobby);
                    }
                    break;
                }
            }
        } else{
            if (currentLobby.isPlayersToStartSet()) {
                return;
            }
            currentLobby.setPlayersToStart(connection, playersToStart);
            if (currentLobby.canStart())
                startGame(currentLobby);
        }


    }

    /**
     * Sets the gameMode that is associated with the given ClientConnection.
     *
     * @param connection the connection that will have its player name set
     * @param gameMode   the gameMode to be set
     */
    public synchronized void setGameMode(SocketClientConnection connection, boolean gameMode) {
        if(waitingLobbies.size() > 1){
            for (Lobby waitingLobby : waitingLobbies) {
                if (waitingLobby.getConnections().contains(connection)) {
                    waitingLobby.setGameMode(connection, gameMode);
                    break;
                }
            }
        } else{
            currentLobby.setGameMode(connection, gameMode);
        }
    }

    /**
     * Starts the game in the current lobby. The game is initialized in a separate Thread to allow new players to connect
     * to the next lobby without waiting.
     */
    private synchronized void startGame(Lobby lobby) {
        playingLobbies.put(lobby.getUuid(), lobby);
        waitingLobbies.remove(lobby);

        lobby.startGame();

        Thread t = new Thread(new GameInstance(lobby, lobby.getGameMode(), lobby.getPlayersToStart()));
        t.start();

        if(waitingLobbies.size() > 0){
            currentLobby = waitingLobbies.get(0);
        } else{
            currentLobby = new Lobby();
            waitingLobbies.add(currentLobby);
        }
    }

    /**
     * Deregister a connection from the lobby, if it is in a playing lobby terminates the game and disconnects all other
     * players in that lobby.
     *
     * @param connection the connection to deregister
     */
    public synchronized void deregisterConnection(SocketClientConnection connection) {
        if (connection.getLobbyUUID() == null) {
            for (Lobby waitingLobby : waitingLobbies) {
                if (waitingLobby.getConnections().contains(connection)) {
                    waitingLobby.disconnect(connection);
                    break;
                }
            }
        } else {
            Lobby lobby = playingLobbies.get(connection.getLobbyUUID());
            if (lobby != null) {
                lobby.disconnectAll(connection);

                playingLobbies.remove(connection.getLobbyUUID());
            }
        }
    }
}