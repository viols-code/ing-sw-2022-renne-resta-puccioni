package it.polimi.ingsw.server;

import java.io.*;
import java.net.Socket;

public class Mex3 extends ClientMessage2{
    @Serial
    private static final long serialVersionUID = -8678590154824429984L;
    private static final String message="ciao come ti chiami";
    public void process(Socket connection, ObjectOutputStream out) throws IOException {
        out.reset();
        out.writeObject(this);
        out.flush();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
