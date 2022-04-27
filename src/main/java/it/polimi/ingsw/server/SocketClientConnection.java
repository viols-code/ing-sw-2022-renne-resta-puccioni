package it.polimi.ingsw.server;

import it.polimi.ingsw.IProcessablePacket;
import it.polimi.ingsw.client.messages.ClientMessage;
import it.polimi.ingsw.client.messages.PlayerNameMessage;
import it.polimi.ingsw.model.player.Wizard;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Runnable class representing a client connection handler.
 */
public class SocketClientConnection implements Runnable {
    private final Socket socket;
    private boolean active = true;
    private String playerName;
    private Wizard wizard;

    private static final int BUFFER_CAPACITY = 20;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final ArrayBlockingQueue<Object> bufferOut;
    private final RemoteView remoteView;

    /**
     * Creates a new SocketClientConnection that manages the communication with the given Socket.
     *
     * @param socket          the client socket
     */
    SocketClientConnection(Socket socket, LobbyController lobbyController) throws IOException{
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        bufferOut = new ArrayBlockingQueue<>(BUFFER_CAPACITY);
        this.remoteView = new RemoteView(this, lobbyController);
    }

    /**
     * Checks if this instance is still running.
     *
     * @return true if the instance is still running, false otherwise
     */
    synchronized boolean isActive() {
        return active;
    }

    /**
     * Sets this instance as no longer running.
     */
    synchronized void setInactive() {
        this.active = false;
    }

    /**
     * Gets the player name associated with this connection.
     *
     * @return the player name, or <code>null</code> if it has not been set yet
     */
    public synchronized String getPlayerName() {
        return playerName;
    }

    /**
     * Sets the player name associated with this connection.
     *
     * @param playerName the player name to associate with this connection
     */
    public synchronized void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Gets the player name associated with this connection.
     *
     * @return the player name, or <code>null</code> if it has not been set yet
     */
    public synchronized Wizard getWizard() {
        return wizard;
    }

    /**
     * Sets the player name associated with this connection.
     *
     * @param wizard the player name to associate with this connection
     */
    public synchronized void setWizard(Wizard wizard) {
        this.wizard = wizard;
    }

    /**
     * Closes this socket connection.
     */
    public synchronized void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }

    /**
     * Stops this instance and deregister it from the LobbyController.
     */
    private void close() {
        closeConnection();
        System.out.println("Deregistering client...");
    }

    /**
     * Runs this instance, waiting for client messages and handling them.
     */
    @Override
    public void run() {
        try{
            remoteView.getLobbyController().addToLobby(this);
            Object read;
            while (isActive()) {
                read = in.readObject();

                try {
                    remoteView.handlePacket(read);
                } catch (Exception e) {
                    System.err.println("Packet from " + playerName + " handling threw an uncaught exception!");
                    e.printStackTrace();
                }
            }

        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }finally{
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error when closing socket!");
            }
            active = false;
            close();

        }
    }

    /**
     * Sends a message to the client.
     *
     * @param message the message to be sent, should be a
     */
    public synchronized void send(Object message) {
        if (bufferOut.remainingCapacity() > 0) {
            bufferOut.add(message);
            try {
                while (this.isActive()) {
                    Object object = bufferOut.take();

                    out.reset();
                    out.writeObject(object);
                    out.flush();
                }
            } catch (EOFException | SocketException ignored) {
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error in SocketClientConnection WriteThread");
                this.setInactive();
            }
        } else {
            System.err.println("WRITE_THREAD: Trying to send too many messages at once!");
        }
    }
}






