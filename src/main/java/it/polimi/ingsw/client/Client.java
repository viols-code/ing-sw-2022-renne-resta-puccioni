package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.GameController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    private String ip = "localhost";
    private int port = 54321;

    private Socket socket;
    private GameController localGameController;
    private String localPlayerName;
    private Boolean isGameExpert = null;
    private boolean active = true;

    public Client(){

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

    public boolean connect(){
        try{
            socket = new Socket(ip,port);
            System.out.println("Connection established");
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            Scanner in = new Scanner(socket.getInputStream());
            System.out.println(in.nextLine());
            Scanner scanner1 = new Scanner(System.in);
            String message = scanner1.nextLine();
            out.println(message);
            out.flush();
        }catch(UnknownHostException | ConnectException e){
            return false;
        }catch(NoSuchElementException | IOException e){
            System.out.println("Connection closed from the client side");
            e.printStackTrace();
        }
        return true;

    }
}
