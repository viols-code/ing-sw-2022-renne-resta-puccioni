package it.polimi.ingsw.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.net.Socket;

public abstract class ClientMessage2 implements Serializable {
    @Serial
    private static final long serialVersionUID = 5167105844763539403L;
    private static final String message="messaggio client base";

    public abstract void process(Socket connection, ObjectOutputStream out) throws IOException;
    public abstract String getMessage();
}
