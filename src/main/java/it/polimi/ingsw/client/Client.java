package it.polimi.ingsw.client;

import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.implementation.cli.CLI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;

/**
 * Main client instance
 */
public class Client {
    /**
     * Default ip
     */
    private String ip = "localhost";
    /**
     * Default port
     */
    private int port = 54321;
    /**
     * Socket
     */
    private Socket socket;
    /**
     * True if the client chooses CLI, false if the client chooses GUI
     */
    private final boolean startCli;
    /**
     *
     */
    private boolean active = true;
    /**
     * Thread that sends messages to the server
     */
    private SocketClientWrite writeThread;
    /**
     * Thread that receives messages from the server
     */
    private SocketClientRead readThread;
    /**
     * The view
     */
    private View view;


    /**
     * Constructs a new Client with the given arguments
     *
     * @param startCli a boolean indicating if the client should be started in CLI mode
     */
    public Client(boolean startCli) {
        this.startCli = startCli;
    }

    /**
     * Checks if this client is still active
     *
     * @return true if the client is active, false otherwise
     */
    public synchronized boolean isActive() {
        return active;
    }

    /**
     * Set the ip of the game server to connect to
     *
     * @param ip the ip of the game server
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Set the port of the game server to connect to
     *
     * @param port the port of the game server
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Connects the client to the server
     *
     * @return true if the client connected correctly, false otherwise
     */
    public boolean connect() {
        ObjectOutputStream out;
        ObjectInputStream in;

        try {
            socket = new Socket(ip, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            // Creates two new threads, one for reading the messages from the server one for sending the messages
            // to the server
            readThread = new SocketClientRead(this, in);
            writeThread = new SocketClientWrite(this, out);
            readThread.start();
            writeThread.start();
            System.out.println("Connection established");

        } catch (UnknownHostException | ConnectException e) {
            return false;
        } catch (NoSuchElementException | IOException e) {
            System.out.println("Connection closed from the client side");
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Sends a message to the Server
     *
     * @param message the message that will be sent to the server
     */
    public void send(Object message) {
        writeThread.send(message);
    }

    /**
     * Terminates this client and the threads associated to it
     */
    public synchronized void terminate() {
        this.active = false;
        if (socket != null)
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        if (writeThread != null)
            writeThread.interrupt();

        System.out.flush();

        if (readThread != null)
            readThread.interrupt();
        System.exit(0);
    }

    /**
     * Gets the View associated with this Client
     *
     * @return the view that's associated with this client
     */
    public View getView() {
        return view;
    }

    /**
     * Starts the main client loop, reading and interpreting user commands
     */
    public void run() {
        if (startCli) {
            view = new CLI(this);
        } //else view = new GUI(this, stage);
        try {
            view.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Resets this Client instance to the initial state, closing the connection with the server
     */
    public void reset() {
        ip = "localhost";
        port = 12345;

        if (writeThread != null) writeThread.interrupt();
        if (readThread != null) readThread.interrupt();
        try {
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
