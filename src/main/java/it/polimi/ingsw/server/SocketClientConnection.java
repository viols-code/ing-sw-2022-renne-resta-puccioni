package it.polimi.ingsw.server;

import it.polimi.ingsw.model.player.Wizard;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Runnable class representing a client connection handler
 */
public class SocketClientConnection implements Runnable {
    private final Socket socket;
    private boolean active = true;
    private String playerName = null;
    private Wizard wizard = null;
    private UUID lobbyUUID = null;

    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final RemoteView remoteView;

    /**
     * Creates a new SocketClientConnection that manages the communication with the given Socket
     *
     * @param socket the client socket
     */
    SocketClientConnection(Socket socket, LobbyController lobbyController) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        this.remoteView = new RemoteView(this, lobbyController);
    }

    /**
     * Checks if this instance is still running
     *
     * @return true if the instance is still running, false otherwise
     */
    synchronized boolean isActive() {
        return active;
    }

    /**
     * Sets this instance as no longer running
     */
    synchronized void setInactive() {
        this.active = false;
    }

    /**
     * Gets the player name associated with this connection
     *
     * @return the player name, or null if it has not been set yet
     */
    public synchronized String getPlayerName() {
        return playerName;
    }

    /**
     * Sets the player name associated with this connection
     *
     * @param playerName the player name to associate with this connection
     */
    public synchronized void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Gets the wizard associated with this connection
     *
     * @return the player name, or null if it has not been set yet
     */
    public synchronized Wizard getWizard() {
        return wizard;
    }

    /**
     * Sets the wizard associated with this connection
     *
     * @param wizard the player name to associate with this connection
     */
    public synchronized void setWizard(Wizard wizard) {
        this.wizard = wizard;
    }

    /**
     * Sets the lobby UUID
     *
     * @param lobbyUUID the UUID of the lobby
     */
    public synchronized void setLobbyUUID(UUID lobbyUUID) {
        this.lobbyUUID = lobbyUUID;
    }

    /**
     * Gets the UUID of the Lobby that this connection is part of
     *
     * @return the uuid of the lobby
     */
    public synchronized UUID getLobbyUUID() {
        return lobbyUUID;
    }

    /**
     * Gets the remote view associated with this connection
     *
     * @return the remoteView of this connection
     */
    public RemoteView getRemoteView() {
        return remoteView;
    }

    /**
     * Closes this socket connection
     */
    public synchronized void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        setInactive();
    }

    /**
     * Stops this instance and deregister it from the LobbyController
     */
    private void close() {
        closeConnection();
        System.out.println("Unregistering client of player " + playerName);
        remoteView.getLobbyController().deregisterConnection(this);
    }

    /**
     * Runs this instance, waiting for client messages and handling them
     */
    @Override
    public void run() {
        TimeOut timeoutThread = new TimeOut();
        timeoutThread.start();

        try {
            remoteView.getLobbyController().correctConnection(this);
            Object read;
            while (isActive()) {
                read = in.readObject();

                try {
                    if (read instanceof String) {
                        if (read.equals("pong")) {
                            timeoutThread.setHasResponded();
                        } else if (read.equals("ping")) {
                            send("pong");
                        } else {
                            System.err.println("Received object of unknown type");
                        }
                    } else {
                        remoteView.handlePacket(read);
                    }
                } catch (Exception e) {
                    System.err.println("Packet from " + playerName + " handling threw an uncaught exception!");
                    e.printStackTrace();
                }
            }

        } catch (EOFException | SocketException e) {
            System.out.println("Player " + playerName + " disconnected");
        } catch (IOException | ClassNotFoundException | NoSuchElementException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Connection with player : " + playerName + " lost");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (isActive()) {
                setInactive();
                close();
            }
        }
    }

    /**
     * Sends a message to the client
     *
     * @param message the message to be sent, should be a
     */
    public synchronized void send(Object message) {
        try {
            if (this.isActive()) {
                out.reset();
                out.writeObject(message);
                out.flush();
            }
        } catch (EOFException | SocketException ignored) {
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in SocketClientConnection WriteThread");
            this.setInactive();
        }
    }

    /**
     * Thread used for the ping to the client
     */
    private class TimeOut extends Thread {
        private final AtomicBoolean hasResponded = new AtomicBoolean();

        public void run() {
            while (isActive()) {
                send("ping");
                hasResponded.set(false);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!hasResponded.get() && isActive()) {
                    close();
                    break;
                }
            }
        }

        public void setHasResponded() {
            this.hasResponded.set(true);
        }
    }
}






