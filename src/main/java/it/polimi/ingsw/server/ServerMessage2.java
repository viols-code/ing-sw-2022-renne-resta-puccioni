package it.polimi.ingsw.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.net.Socket;

public abstract class ServerMessage2 implements Serializable {
    @Serial
    private static final long serialVersionUID = 4167105844763539403L;
    private static final String message = "basic server message";
    public abstract void process(Socket connection, ObjectOutputStream out) throws IOException;
    public abstract String getMessage();
}
