package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.server.IServerPacket;
import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.server.messages.DirectServerMessage;

import java.io.Serial;
import java.io.Serializable;

public abstract class DirectReconnectionMessage extends DirectServerMessage {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -6462045893905157931L;


    /**
     * Constructs a new DirectReconnectionMessage for the given recipient
     *
     * @param recipient the client connection that this message will be sent to
     */
    public DirectReconnectionMessage(SocketClientConnection recipient) {
        super(recipient);
    }

    /**
     * Gets the recipient of this message
     *
     * @return the client connection that is the recipient of this message
     */
    public SocketClientConnection getRecipient() {
        return super.getRecipient();
    }
}
