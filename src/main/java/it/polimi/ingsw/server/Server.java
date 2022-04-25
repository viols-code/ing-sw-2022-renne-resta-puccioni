package it.polimi.ingsw.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Main server instance.
 */
public class Server {
    private static final int PORT = 54321;
    private final ServerSocket serverSocket;
    private final LobbyController lobbyController;

    /**
     * Constructs a new Server, initializing the ServerSocket, the FixedThreadPool and the LobbyController.
     *
     * @throws IOException if an error occurs while initializing the ServerSocket
     */
    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
        this.lobbyController = new LobbyController();
    }

    /**
     * Runs the server and makes it wait for and accept incoming connections.
     */
    public void run() {
        System.out.println("Server starting...");

        while (true) {
            try {
                Socket connection = serverSocket.accept();

                System.out.println("Accepted new connection");

                //pezzo originale
                /*SocketClientConnection socketConnection = new SocketClientConnection(connection, lobbyController);
                Thread t = new Thread(socketConnection);
                t.start();*/

                SocketClientConnection socketConnection = new SocketClientConnection(connection);
                Thread t = new Thread(socketConnection);
                t.start();

                /*PrintWriter out = new PrintWriter(connection.getOutputStream());
                out.println("ciao, come ti chiami?");
                out.flush();
                Scanner in = new Scanner(connection.getInputStream());
                System.out.println(in.nextLine());*/
            } catch (IOException e) {
                System.err.println("Connection Error!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
