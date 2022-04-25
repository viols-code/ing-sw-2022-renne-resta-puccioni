package it.polimi.ingsw.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serial;
import java.net.Socket;

public class Mex2 extends ServerMessage2{
    @Serial
    private static final long serialVersionUID = -8678594150824429984L;
    private static final String message="resta";
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
