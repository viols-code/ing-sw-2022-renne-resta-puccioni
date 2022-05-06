package it.polimi.ingsw.server;

import it.polimi.ingsw.IProcessablePacket;
import it.polimi.ingsw.client.messages.ClientMessage;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.messages.InvalidActionUpdate;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.messages.DirectServerMessage;
import it.polimi.ingsw.view.messages.PlayerEvent;

/**
 * Represents a client view on the server. It is responsible of handling incoming and outgoing messages and updates to
 * the associated client connection.
 */
public class RemoteView implements Observer<IProcessablePacket> {
    private String player;
    private final SocketClientConnection clientConnection;
    private final LobbyController lobbyController;
    private GameController gameController;

    /**
     * Constructs a new RemoteView associated with the given client connection.
     *
     * @param connection      the connection to be associated with this remote view
     * @param lobbyController the lobby controller that should handle this remote view
     */
    public RemoteView(SocketClientConnection connection, LobbyController lobbyController) {
        this.player = null;
        this.clientConnection = connection;
        this.lobbyController = lobbyController;
        this.gameController = null;
    }

    /**
     * Sets the Player associated with this RemoteView.
     *
     * @param player the player to be associated with the remote view
     */
    public synchronized void setPlayer(String player) {
        this.player = player;
    }

    /**
     * Gets the client connection associated with this RemoteView.
     *
     * @return the associated client connection
     */
    private SocketClientConnection getClientConnection() {
        return clientConnection;
    }

    /**
     * Gets the LobbyController that handles this RemoteView.
     *
     * @return the lobby controller
     */
    public LobbyController getLobbyController() {
        return lobbyController;
    }

    /**
     * Sets the GameController that handles this RemoteView.
     *
     * @param gameController the game controller to be set
     */
    synchronized void setGameController(GameController gameController) {
        this.gameController = gameController;
    }


    /**
     * Notifies the GameController of the given PlayerEvent, if the GameController is null (the game is not started)
     * prints an error and does nothing.
     *
     */
    private void notifyActionEvent(PlayerEvent event) {
        if (gameController != null) {
            event.setPlayer(player);
            try {
               gameController.update(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            System.err.println("Received PlayerActionEvent, but game is not started yet");
    }


    /**
     * Notifies the LobbyController of the given ClientMessage, if the game is already started (GameController is not null)
     * prints an error and does nothing.
     *
     * @param message the client message that will be notified to the lobby controller
     */
    private void notifyClientMessage(ClientMessage message) {
        if (gameController == null) {
            message.setClientConnection(getClientConnection());
            try {
                lobbyController.update(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            System.err.println("Received ClientMessage, but the game is already started");
    }

    /**
     * Handles an IServerPacket and sends it to the client associated with this RemoteView.
     *
     * @param packet the packet to be sent to the client
     */
    @Override
    public synchronized void update(IProcessablePacket packet) {
        if (packet instanceof DirectServerMessage) {
            DirectServerMessage dm = (DirectServerMessage) packet;
            if (dm.getRecipient() == clientConnection)
                clientConnection.send(dm);
        } else if (packet instanceof InvalidActionUpdate) {
            InvalidActionUpdate update = (InvalidActionUpdate) packet;
            if (update.getPlayerIndex() == player)
                clientConnection.send(update);
        }
         else
            clientConnection.send(packet);
    }


    /**
     * Handles an incoming packet, notifying it to the adequate controller.
     *
     * @param packet the incoming packet to be processed
     */
    void handlePacket(Object packet) {
        System.out.println("Received: " + packet);
        if (packet instanceof IProcessablePacket) {
            if (packet instanceof ClientMessage) {
                ClientMessage clientMessage = (ClientMessage) packet;
                notifyClientMessage(clientMessage);
            } else if (packet instanceof PlayerEvent) {
                PlayerEvent actionEvent = (PlayerEvent) packet;
                notifyActionEvent(actionEvent);
            } else {
                System.err.println("Received object is of unknown type");
            }
        }

    }
}
