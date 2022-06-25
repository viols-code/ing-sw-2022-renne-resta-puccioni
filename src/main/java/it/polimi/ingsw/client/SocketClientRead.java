package it.polimi.ingsw.client;

import it.polimi.ingsw.IProcessablePacket;
import it.polimi.ingsw.server.IServerPacket;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Thread responsible to read messages coming from the server and handling them
 */
public class SocketClientRead extends Thread {
    private final Client client;
    private final ObjectInputStream socketIn;

    /**
     * Constructs a new SocketClientRead for the given Client that will read messages from the given ObjectInputStream
     *
     * @param client   the client that is associated with this thread
     * @param socketIn the input stream from where messages will be read
     */
    public SocketClientRead(Client client, ObjectInputStream socketIn) {
        super();

        this.client = client;
        this.socketIn = socketIn;
    }

    /**
     * Starts the read thread loop, waiting for objects to be read from the input stream and handling them
     */
    @Override
    public void run() {
        SocketClientRead.TimeOut timeoutThread = new SocketClientRead.TimeOut();
        timeoutThread.start();

        try {
            while (client.isActive()) {
                // Wait a message from the server
                Object packet = socketIn.readObject();

                // Select the type of the message and choose how to handle it
                if (packet instanceof String) {
                    // Ping message
                    if (packet.equals("ping")) {
                        // Answer the ping from the server
                        client.send("pong");
                    } else if (packet.equals("pong")) {
                        timeoutThread.setHasResponded();
                    }
                } else {
                    if (packet instanceof IProcessablePacket) {
                        if (packet instanceof IServerPacket serverPacket) {
                            //System.out.println("Received: " + packet);

                            try {
                                // Call the method process in the message
                                serverPacket.process(client.getView());
                            } catch (Exception e) {
                                System.err.println("Uncaught exception while processing server packet");
                                e.printStackTrace();
                            }
                        } else {
                            System.err.println("Received a packet of the wrong type");
                        }
                    } else {
                        System.err.println("Received object of unknown type");
                    }
                }
            }
        } catch (SocketException | EOFException ignored) {

        } catch (Exception e) {
            e.printStackTrace();
            client.terminate();
        }
    }


    /**
     * Thread used for the ping to the server
     */
    private class TimeOut extends Thread {
        private final AtomicBoolean hasResponded = new AtomicBoolean();

        public void run() {
            while (client.isActive()) {
                client.send("ping");
                hasResponded.set(false);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!hasResponded.get()) {
                    //client.terminate();
                    break;
                }
            }
        }

        public void setHasResponded() {
            this.hasResponded.set(true);
        }
    }
}
