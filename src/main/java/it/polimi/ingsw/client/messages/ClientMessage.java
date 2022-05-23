package it.polimi.ingsw.client.messages;

import it.polimi.ingsw.IProcessablePacket;
import it.polimi.ingsw.server.LobbyController;
import it.polimi.ingsw.server.SocketClientConnection;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a message that will be sent from the Client to the Server
 */
public abstract class ClientMessage implements Serializable, IProcessablePacket<LobbyController> {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 463009486016884875L;
    /**
     * The socketClientConnection
     */
    private transient SocketClientConnection clientConnection;

    /**
     * Gets the ClientConnection of this message.
     *
     * @return the client connection associated with this message
     */
    public SocketClientConnection getClientConnection() {
        return clientConnection;
    }

    /**
     * Constructor
     *
     * @param clientConnection the socketClientConnection
     */
    public void setClientConnection(SocketClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }
}