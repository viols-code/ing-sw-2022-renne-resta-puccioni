package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.server.*;

import java.io.*;
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
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Object read;
            ClientMessage2 mex;

                mex = new Mex3();
                mex.process(socket,out);
                System.out.println("ho inviato il messaggio 3");

                mex = new Mex4();
                mex.process(socket,out);
                System.out.println("ho inviato il messaggio 4");


                read = in.readObject();
                if(read instanceof ServerMessage2){
                    System.out.println(((ServerMessage2)read).getMessage());
                }
                else{
                    System.out.println("message not known");
                }


        }catch(UnknownHostException | ConnectException | ClassNotFoundException e){
            return false;
        }catch(NoSuchElementException | IOException  e){
            System.out.println("Connection closed from the client side");
            e.printStackTrace();
        }
        return true;

    }
}
