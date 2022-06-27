package it.polimi.ingsw.server;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.messages.CorrectConnectionMessage;
import it.polimi.ingsw.server.messages.ErrorMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Models a waiting room, holding the list of connected clients without a name setted
 */
public class WaitingRoom extends Observable<IServerPacket> {
    /**
     * List of the connection without a nickname
     */
    private final List<SocketClientConnection> connectionList;

    /**
     * Constructor: create a new WaitingRoom
     */
    public WaitingRoom(){
        connectionList = new ArrayList<>();
    }

    /**
     * Adds the given connection to this waiting room
     *
     * @param connection the connection to be added
     */
    public void addConnection(SocketClientConnection connection) {
        connectionList.add(connection);

        notify(new CorrectConnectionMessage(connection));
    }

    /**
     * Removes the given connection to this waiting room
     *
     * @param connection the connection to be removed
     */
    public void removeConnection(SocketClientConnection connection) {
        connectionList.remove(connection);
    }

    /**
     * Notify an error to the given connection
     *
     * @param connection the given connection
     * @param error a String containing the error message
     */
    public void notifyError(SocketClientConnection connection, String error){
        notify(new ErrorMessage(connection, error));
    }



}
