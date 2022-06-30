package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.view.View;

import java.io.Serial;

public class TableReconnectUpdate extends DirectReconnectionMessage{
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -5351934793905157931L;
    /**
     * The recipient
     */
    protected final transient SocketClientConnection recipient;
    /**
     * The number of groupIslands
     */
    private final int groupIsland;
    /**
     * True if the groupIslands are expert, false otherwise
     */
    private final boolean expert;

    /**
     * Constructs a new TableReconnectUpdate for the given recipient
     *
     * @param recipient the client connection that this message will be sent to
     */
    public TableReconnectUpdate(SocketClientConnection recipient, int groupIsland, boolean expert) {
        super(recipient);
        this.recipient = recipient;
        this.groupIsland = groupIsland;
        this.expert = expert;
    }

    /**
     * Gets the recipient of this message
     *
     * @return the client connection that is the recipient of this message
     */
    public SocketClientConnection getRecipient() {
        return recipient;
    }

    /**
     * Update the view with the islands information
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateIslands(groupIsland, expert);
    }
}
