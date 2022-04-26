package it.polimi.ingsw.server;

import it.polimi.ingsw.IProcessablePacket;
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

    /**
     * Creates a new SocketClientConnection that manages the communication with the given Socket.
     *
     * @param socket          the client socket
     */
    SocketClientConnection(Socket socket) {
        this.socket = socket;
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
        ObjectInputStream in;
        ObjectOutputStream out;
        try{
            in = new ObjectInputStream(socket.getInputStream());
            Object read;
            out = new ObjectOutputStream(socket.getOutputStream());
            while (isActive()) {
                read = in.readObject();
                if(read instanceof ClientMessage2){
                        System.out.println(((ClientMessage2)read).getMessage());
                    if(read instanceof Mex3){
                        ServerMessage2 mex = new Mex1();
                        mex.process(socket,out);
                        System.out.println("ho inviato il messaggio 1");
                    }
                    else if(read instanceof Mex4){
                        ServerMessage2 mex = new Mex2();
                        mex.process(socket,out);
                        System.out.println("ho inviato il messaggio 2");
                    }
                }
                else{
                    System.out.println("message not known");
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
            System.out.println("Deregistering client...");

        }
}}






