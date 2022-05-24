package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.implementation.cli.CLI;
import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;

public class Client {
    private String ip = "localhost";
    private int port = 54321;

    private Socket socket;
    private final boolean startCli;
    private boolean active = true;
    private SocketClientWrite writeThread;
    private SocketClientRead readThread;

    private View view;

    private final Stage stage;


    /**
     * Constructs a new Client with the given arguments.
     *
     * @param startCli a boolean indicating if the client should be started in CLI mode
     */
    public Client(Stage stage, boolean startCli) {
        this.stage = stage;
        this.startCli = startCli;
    }

    /**
     * Checks if this client is still active.
     *
     * @return true if the client is active, false otherwise
     */
    public synchronized boolean isActive() {
        return active;
    }

    /**
     * Set the ip of the game server to connect to.
     *
     * @param ip the ip of the game server
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Set the port of the game server to connect to.
     *
     * @param port the port of the game server
     */
    public void setPort(int port) {
        this.port = port;
    }

    public boolean connect(String address) {
        String[] split = address.split(":");
        if (split.length != 2)
            return false;
        setIp(split[0]);
        try {
            setPort(Integer.parseInt(split[1]));
        } catch (NumberFormatException e) {
            return false;
        }
        return connect();
    }

    public boolean connect() {
        ObjectOutputStream out;
        ObjectInputStream in;
        try {
            socket = new Socket(ip, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

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
     * Sends a message to the Server.
     *
     * @param message the message that will be sent to the server
     */
    public void send(Object message) {
        writeThread.send(message);
    }

    /**
     * Terminates this client.
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
     * Gets the View associated with this Client.
     *
     * @return the view that's associated with this client
     */
    public View getView() {
        return view;
    }

    /**
     * Starts the main client loop, reading and interpreting user commands.
     */
    public void run() {
        if (startCli) {
            view = new CLI(this);
        } else view = new GUI(this, stage);
        try {
            view.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Resets this Client instance to the initial state, closing the connection with the server.
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
