package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.server.IServerPacket;
import it.polimi.ingsw.server.SocketClientConnection;

import java.io.Serial;
import java.io.Serializable;

public abstract class DirectReconnectionMessage implements Serializable, IServerPacket {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -6462045893905157931L;
    /**
     * The recipient
     */
    protected final transient SocketClientConnection recipient;

    /**
     * Constructs a new DirectReconnectionMessage for the given recipient
     *
     * @param recipient the client connection that this message will be sent to
     */
    public DirectReconnectionMessage(SocketClientConnection recipient) {
        this.recipient = recipient;
    }

    /**
     * Gets the recipient of this message
     *
     * @return the client connection that is the recipient of this message
     */
    public SocketClientConnection getRecipient() {
        return recipient;
    }
}
